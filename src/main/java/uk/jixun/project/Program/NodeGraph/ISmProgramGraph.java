package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.RenderConfig.IRenderConfig;

import java.awt.*;

public interface ISmProgramGraph {
  /**
   * Initialise graph with given program.
   * @param program The parsed program.
   */
  void setProgram(ISmProgram program);

  Image getImage(IRenderConfig config);
}
