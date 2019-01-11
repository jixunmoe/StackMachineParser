package uk.jixun.project;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import uk.jixun.project.Instruction.CommentInstruction;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Instruction.NoInstruction;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Parsing a stream of asm code text.
 */
public class StackMachineInstParser implements Iterator<ISmInstruction> {
  private Scanner source;
  public StackMachineInstParser(Scanner scanner) {
    source = scanner;
  }

  private ISmInstruction parseInstruction(String line) {
    // Instruction is actually a comment
    if (line.charAt(0) == '#') {
      return new CommentInstruction(line.substring(1).trim());
    }

    // Split by whitespaces
    String[] parts = line.split("\\s+");

    // Empty line
    if (parts.length == 0) {
      return new NoInstruction();
    }

    // Convert to opcode
    // TODO: Implement instruction parsing
    throw new NotImplementedException();
  }

  @Override
  public boolean hasNext() {
    return source.hasNext();
  }

  @Override
  public ISmInstruction next() {
    String line = source.nextLine();

    return parseInstruction(line.trim());
  }
}
