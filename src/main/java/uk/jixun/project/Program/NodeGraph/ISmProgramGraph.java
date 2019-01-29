package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Instruction.ISmInstruction;

import java.awt.image.RenderedImage;

public interface ISmProgramGraph {
  void addInstruction(ISmInstruction instruction);

  RenderedImage getImage();
}
