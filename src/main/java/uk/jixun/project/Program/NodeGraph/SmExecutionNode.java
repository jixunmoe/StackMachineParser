package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Instruction.ISmInstruction;

import java.awt.*;
import java.util.List;

// TODO: Implement this class.

public class SmExecutionNode implements ISmProgramNode {
  @Override
  public ISmInstruction getInstruction() {
    return null;
  }

  @Override
  public void setInstruction(ISmInstruction instruction) {
  }

  @Override
  public boolean dependsOn(ISmProgramNode node) {
    return false;
  }

  @Override
  public List<ISmProgramNode> getDependencies() {
    return null;
  }

  @Override
  public Image getImage() {
    return null;
  }
}
