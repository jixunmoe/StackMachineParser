package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Instruction.ISmInstruction;

import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SmBasicProgramNodeGraph implements ISmProgramNodeGraph {
  private Stack<ISmProgramNode> globalStack = new Stack<>();
  private List<ISmProgramNode> nodes = new ArrayList<>();

  @Override
  public Stack<ISmProgramNode> getInstructionStack() {
    return globalStack;
  }

  @Override
  public List<ISmProgramNode> getDisconnectedNodes() {
    return nodes;
  }

  @Override
  public void addInstruction(ISmInstruction instruction) {
    ISmProgramNode node = new SmBasicProgramNode();
    node.setInstruction(instruction);

    int consume = instruction.getOpCode().getConsume();
    int produce = instruction.getOpCode().getProduce();

    for(int i = 0; i < consume; i++) {
      node.pushStack(getInstructionStack().pop());
    }

    // This is a disconnected node.
    if (produce == 0) {
      getDisconnectedNodes().add(node);
    } else {
      // TODO: Better way to implement this.
      // now just work with instructions produces 1 values.
      assert produce == 1;

      for(int i = 0; i < produce; i++) {
        getInstructionStack().push(node);
      }
    }
  }

  @Override
  public RenderedImage getImage() {
    return null;
  }
}
