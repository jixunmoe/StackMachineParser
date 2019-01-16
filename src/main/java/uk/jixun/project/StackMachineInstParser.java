package uk.jixun.project;

import org.apache.commons.lang3.NotImplementedException;
import uk.jixun.project.Exceptions.LabelDuplicationException;
import uk.jixun.project.Helper.ParseHelper;
import uk.jixun.project.Instruction.BasicInstruction;
import uk.jixun.project.Instruction.CommentInstruction;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Instruction.NoInstruction;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.OpCode.SmOpcodeParser;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Operand.SmOperandParser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Parsing a stream of asm code text.
 */
public class StackMachineInstParser {
  private Scanner source;
  public StackMachineInstParser(Scanner scanner) {
    source = scanner;
  }

  private enum ParseInstructionState {
    Opcode, Operand
  }

  long virtualAddress = 0;
  long lineNumber = 0;
  HashMap<String, Long> labelMapping = new HashMap<>();

  private ISmInstruction parseInstruction(String line) throws LabelDuplicationException {
    // Instruction is actually a comment
    if (line.charAt(0) == '#') {
      return new CommentInstruction(line.substring(1).trim());
    }

    int i = 0;
    boolean skipWhiteSpace = false;
    StringBuilder buffer = new StringBuilder();
    String opcodeStr = "";
    List<String> operandsStr = new ArrayList<>();

    ParseInstructionState state = ParseInstructionState.Opcode;
    while (true) {
      if (i >= line.length()) {
        break;
      }

      char c = line.charAt(i);
      switch(state) {
        case Opcode:
          if (c == ':') {
            String label = buffer.toString();
            if (labelMapping.containsKey(label)) {
              throw new LabelDuplicationException(label);
            }
            labelMapping.put(label, virtualAddress);

            buffer = new StringBuilder();
          } else if (ParseHelper.isWhiteSpace(c)) {
            skipWhiteSpace = true;

            opcodeStr = buffer.toString();
            buffer = new StringBuilder();
            state = ParseInstructionState.Operand;
          } else {
            buffer.append(c);
          }
          break;
        case Operand:
          if (ParseHelper.isWhiteSpace(c)) {
            operandsStr.add(buffer.toString());
            buffer = new StringBuilder();
          } else {
            buffer.append(c);
          }
          break;
        default:
          break;
      }

      if (skipWhiteSpace) {
        while(ParseHelper.isWhiteSpace(line.charAt(c))) {
          i++;
        }
        skipWhiteSpace = false;
      } else {
        i++;
      }
    }

    if (state == ParseInstructionState.Opcode) {
      opcodeStr = buffer.toString();

      // Check for empty line
      if (opcodeStr.length() == 0) {
        return new NoInstruction();
      }
    }

    // Initialise instruction object
    BasicInstruction instruction = new BasicInstruction();

    // Set opcode parsed
    ISmOpCode opcode = SmOpcodeParser.parse(opcodeStr);
    instruction.setOpcode(opcode);

    // Convert operands to nodes
    List<ISmOperand> operands = operandsStr
      .stream()
      .map((String operandStr) -> SmOperandParser.parse(operandStr, instruction))
      .collect(Collectors.toList());
    instruction.setOperands(operands);

    return instruction;
  }

  public boolean hasNext() {
    return source.hasNext();
  }

  public ISmInstruction next() throws LabelDuplicationException {
    String line = source.nextLine();
    lineNumber++;

    return parseInstruction(line.trim());
  }
}
