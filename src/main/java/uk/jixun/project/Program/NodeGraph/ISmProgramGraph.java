package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Program.ISmProgram;

import java.awt.image.RenderedImage;

public interface ISmProgramGraph {
  /**
   * Initialise graph with given program.
   * @param program The parsed program.
   */
  void setProgram(ISmProgram program);

  RenderedImage getImage();
}
