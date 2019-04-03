package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SmDependencyGraph implements ISmDependencyGraph {
  private Stack<ISmProgramNode> globalStack = new Stack<>();
  private List<ISmProgramNode> nodes = new ArrayList<>();
  private ISmProgram program = null;

  @Override
  public Stack<ISmProgramNode> getInstructionStack() {
    return globalStack;
  }

  @Override
  public List<ISmProgramNode> getDisconnectedNodes() {
    return nodes;
  }

  private ISmProgramNode getAdjacentNode(ISmProgramNode ref, int delta) {
    int index = nodes.indexOf(ref) + delta;
    if (index >= 0 && index < nodes.size()) {
      return nodes.get(index);
    }
    return null;
  }

  @Override
  public ISmProgramNode getNextNode(ISmProgramNode ref) {
    return getAdjacentNode(ref, 1);
  }

  @Override
  public ISmProgramNode getPrevNode(ISmProgramNode ref) {
    return getAdjacentNode(ref, -1);
  }

  @Override
  public void setProgram(ISmProgram program) {
    this.program = program;
    reloadInstructions();
  }

  @Override
  public boolean containsNode(ISmProgramNode node) {
    // null does not contain anything.
    if (program == null) {
      return false;
    }

    ISmInstruction inst = node.getInstruction();
    return program.getInstructions().contains(inst);
  }

  @Override
  public Image getImage(ISimulatorConfig config) {
    return null;
  }

  private void reloadInstructions() {
    globalStack.clear();
    nodes.clear();

    for (ISmInstruction inst : program.getInstructions()) {
      addInstruction(inst);
    }
  }

  private void addInstruction(ISmInstruction instruction) {
    ISmDependencyNode node = new SmDependencyNode();
    node.setInstruction(instruction);

    // number of items to consume from or produce to stack
    int consume = instruction.getOpCode().getConsume();
    int produce = instruction.getOpCode().getProduce();

    // Get instruction dependency.
    for (int i = 0; i < consume; i++) {
      node.pushStack(getInstructionStack().pop());
    }

    // This is a disconnected node.
    if (produce == 0) {
      getDisconnectedNodes().add(node);
    } else {
      // TODO: Better way to implement this.
      // now just work with instructions produces 1 values.
      assert produce == 1;

      for (int i = 0; i < produce; i++) {
        getInstructionStack().push(node);
      }
    }
  }
}
