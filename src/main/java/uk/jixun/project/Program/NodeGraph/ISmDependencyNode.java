package uk.jixun.project.Program.NodeGraph;

import java.util.Stack;

public interface ISmDependencyNode extends ISmProgramNode {
  /**
   * Instructions required for data.
   * Begin of the stack is earliest instruction that provides the required stack element.
   * @return Instructions required for this instruction to work.
   */
  Stack<ISmProgramNode> getConsumedInstructions();

  void pushStack(ISmProgramNode instruction);
}
