package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Instruction.ISmInstruction;

import java.awt.image.RenderedImage;

public interface ISmProgramNode {
  ISmInstruction getInstruction();
  void setInstruction(ISmInstruction instruction);

  RenderedImage getImage();
}
