package uk.jixun.project;

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
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.Program.SmProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Parsing a stream of asm code text.
 */
public class StackMachineInstParser {
  private static Logger logger = Logger.getLogger(StackMachineInstParser.class.getName());

  private int virtualAddress = 0;
  private int lineNumber = 0;
  private SmProgram program = new SmProgram();
  private Scanner source;
  private ArrayList<ISmInstruction> results = new ArrayList<>();

  public StackMachineInstParser(Scanner scanner) {
    source = scanner;
  }

  private boolean parseInstruction(String line) {
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
          } else if (c == '#' || c == ';') {
            opcodeStr = buffer.toString();
            buffer = new StringBuilder();

            // Is a comment
            state = ParseInstructionState.Comment;
            continue;
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
          } else if (c == '#' || c == ';') {
            operandsStr.add(buffer.toString());
            state = ParseInstructionState.Comment;
            continue;
          } else {
            buffer.append(c);
          }
          break;
        case Comment:
          ISmInstruction instruction = new CommentInstruction(line.substring(i));
          instruction.setLine(lineNumber);
          instruction.setVirtualAddress(virtualAddress);
          // FIXME: Add comment to the program not as an instruction.
          // results.add(instruction);
          i += line.length();
          break;
        default:
          break;
      }
    }

    if (state == ParseInstructionState.Opcode) {
      opcodeStr = buffer.toString().strip();
    } else if (state == ParseInstructionState.Operand) {
      operandsStr.add(buffer.toString());
    }

    // Check for empty line
    if (opcodeStr.length() == 0) {
      // results.add(new NoInstruction());
      return true;
    }

    // Initialise instruction object
    BasicInstruction instruction = new BasicInstruction();
    instruction.setLine(lineNumber);
    instruction.setVirtualAddress(virtualAddress);

    // Set opcode parsed
    ISmOpCode opcode = SmOpcodeParser.parse(opcodeStr);
    instruction.setOpcode(opcode);
    opcode.setInstruction(instruction);

    // Convert operands to nodes
    List<ISmOperand> operands = operandsStr
      .stream()
      // Remove any empty operands
      .map(String::stripTrailing)
      .filter(s -> !s.isEmpty())
      .map((String operandStr) -> SmOperandParser.parse(operandStr, instruction))
      .collect(Collectors.toList());
    instruction.setOperands(operands);

    // FIXME: Let instruction to calculate sizeof current instruction.
    virtualAddress++;

    results.add(instruction);
    return true;
  }

  public boolean hasNext() {
    return source.hasNext() || results.size() > 0;
  }

  public ISmInstruction next() throws ParseException {
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

  public ISmProgram toProgram() {
    SmProgram prog = new SmProgram();
    while(hasNext()) {
      prog.addInstruction(next());
    }
    return prog;
  }

  private enum ParseInstructionState {
    Opcode, Operand, Comment
  }
}
