package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Instruction.ISmInstruction;

import java.awt.image.RenderedImage;
import java.util.Stack;

public interface ISmProgramNode {
  ISmInstruction getInstruction();
  void setInstruction(ISmInstruction instruction);

  /**
   * Instructions required for data.
   * Begin of the stack is earliest instruction that provides the required stack element.
   * @return Instructions required for this instruction to work.
   */
  Stack<ISmProgramNode> getConsumedInstructions();

  void pushStack(ISmProgramNode instruction);

  RenderedImage getImage();
}
