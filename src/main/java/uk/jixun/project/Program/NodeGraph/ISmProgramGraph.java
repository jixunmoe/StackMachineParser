package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;

import java.awt.*;

public interface ISmProgramGraph {
  /**
   * Initialise graph with given program.
   * @param program The parsed program.
   */
  void setProgram(ISmProgram program);

  /**
   * Check of given node exists in this graph
   * @param node node to check for
   * @return true if exists
   */
  boolean containsNode(ISmProgramNode node);

  Image getImage(ISimulatorConfig config);
}
