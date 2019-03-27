package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Instruction.ISmInstruction;

import java.awt.*;
import java.util.List;

public interface ISmProgramNode {
  ISmInstruction getInstruction();
  void setInstruction(ISmInstruction instruction);

  /**
   * Check if current node depends on another node.
   * @param node Check if node depends on another node.
   * @return true if it does depends given node; could be direct or indirect.
   */
  boolean dependsOn(ISmProgramNode node);

  /**
   * Get a list of dependency nodes.
   * @return List of single level dependency node.
   */
  List<ISmProgramNode> getDependencies();

  Image getImage();
}
