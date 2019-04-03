package uk.jixun.project.Program.NodeGraph;

import java.util.List;
import java.util.Stack;

public interface ISmDependencyGraph extends ISmProgramGraph {
  Stack<ISmProgramNode> getInstructionStack();

  List<ISmProgramNode> getDisconnectedNodes();

  ISmProgramNode getNextNode(ISmProgramNode ref);

  ISmProgramNode getPrevNode(ISmProgramNode ref);
}
