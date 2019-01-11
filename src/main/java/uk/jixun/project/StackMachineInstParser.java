package uk.jixun.project;

import uk.jixun.project.Instruction.CommentInstruction;
import uk.jixun.project.Instruction.ISmInstruction;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Parsing a stream of asm code text.
 */
public class StackMachineInstParser implements Iterator<ISmInstruction> {
  private Scanner source;
  public StackMachineInstParser(Scanner scanner) {
    source = scanner;
  }

  @Override
  public boolean hasNext() {
    return source.hasNext();
  }

  @Override
  public ISmInstruction next() {
    String line = source.nextLine();
    String trimmed = line.trim();

    // Instruction is actually a comment
    if (trimmed.charAt(0) == '#') {
      return new CommentInstruction(trimmed.substring(1).trim());
    }

    return null;
  }
}
