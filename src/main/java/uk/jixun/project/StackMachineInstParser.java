package uk.jixun.project;

import uk.jixun.project.Exceptions.LabelDuplicationException;
import uk.jixun.project.Exceptions.ParseException;
import uk.jixun.project.Helper.ParseHelper;
import uk.jixun.project.Instruction.BasicInstruction;
import uk.jixun.project.Instruction.CommentInstruction;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Instruction.NoInstruction;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.OpCode.SmOpcodeParser;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Operand.SmOperandParser;
import uk.jixun.project.Program.SmProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Parsing a stream of asm code text.
 */
public class StackMachineInstParser {
  long virtualAddress = 0;
  long lineNumber = 0;
  private SmProgram program = new SmProgram();
  private Scanner source;
  private ArrayList<ISmInstruction> results = new ArrayList<>();

  public StackMachineInstParser(Scanner scanner) {
    source = scanner;
  }

  private boolean parseInstruction(String line) throws LabelDuplicationException {
    boolean skipWhiteSpace = true;
    StringBuilder buffer = new StringBuilder();
    String opcodeStr = "";
    List<String> operandsStr = new ArrayList<>();

    ParseInstructionState state = ParseInstructionState.Opcode;
    for (int i = 0; i < line.length(); i++) {
      if (skipWhiteSpace) {
        while (ParseHelper.isWhiteSpace(line.charAt(i))) {
          i++;
        }
        skipWhiteSpace = false;
      }

      char c = line.charAt(i);
      switch (state) {
        case Opcode:
          if (c == ':') {
            String label = buffer.toString();
            program.registerLabel(label, virtualAddress);

            buffer = new StringBuilder();
            skipWhiteSpace = true;
          } else if (c == '#') {
            // Is a comment
            ISmInstruction instruction = new CommentInstruction(line.substring(i + 1));
            instruction.setLine(lineNumber);
            instruction.setVirtualAddress(virtualAddress);
            results.add(instruction);
            return true;
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
    }

    if (state == ParseInstructionState.Opcode) {
      opcodeStr = buffer.toString();

      // Check for empty line
      if (opcodeStr.length() == 0) {
        // results.add(new NoInstruction());
        return true;
      }
    } else {
      // state == ParseInstructionState.Operand
      // If there's anything in the buffer that can be an operand
      if (buffer.length() > 0) {
        operandsStr.add(buffer.toString());
      }
    }

    // Initialise instruction object
    BasicInstruction instruction = new BasicInstruction();
    instruction.setLine(lineNumber);
    instruction.setVirtualAddress(virtualAddress);

    // Set opcode parsed
    ISmOpCode opcode = SmOpcodeParser.parse(opcodeStr);
    instruction.setOpcode(opcode);

    // Convert operands to nodes
    List<ISmOperand> operands = operandsStr
      .stream()
      .map((String operandStr) -> SmOperandParser.parse(operandStr, instruction))
      .collect(Collectors.toList());
    instruction.setOperands(operands);

    // FIXME: Let instruction to calculate sizeof current instruction.
    virtualAddress++;

    results.add(instruction);
    return true;
  }

  public boolean hasNext() {
    return source.hasNext();
  }

  public ISmInstruction next() throws LabelDuplicationException, ParseException {
    while (results.size() <= 0) {
      if (!hasNext()) {
        // FIXME: Throw an error if no more entries left?
        return new NoInstruction();
      }
      String line = source.nextLine();
      lineNumber++;
      if (!parseInstruction(line)) {
        throw ParseException.forInputString(line);
      }
    }

    ISmInstruction instruction = results.get(0);
    instruction.setProgram(program);
    program.addInstruction(instruction);
    results.remove(0);
    return instruction;
  }

  private enum ParseInstructionState {
    Opcode, Operand
  }
}
