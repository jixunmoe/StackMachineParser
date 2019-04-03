package uk.jixun.project.Gui;

import uk.jixun.project.Program.NodeGraph.ISmProgramGraph;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;
import uk.jixun.project.SimulatorConfig.SimulatorConfigImpl;

import javax.swing.*;
import java.awt.*;

public class GraphCanvas extends JPanel {
  private ISmProgramGraph graph = null;
  private Font msgFont;
  private int msgFontHeight;

  public GraphCanvas() {
    setBorder(BorderFactory.createLineBorder(Color.black));
    msgFont = new Font("Courier New", Font.PLAIN, 12);
    FontMetrics metrics = getFontMetrics(msgFont);
    msgFontHeight = metrics.getAscent();
  }

  public ISmProgramGraph getGraph() {
    return graph;
  }

  public void setGraph(ISmProgramGraph graph) {
    this.graph = graph;
  }

  public Dimension getPreferredSize() {
    return new Dimension(400, 400);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (graph == null) {
      g.setFont(msgFont);
      g.drawString("No Graph Loaded", 10, 10 + msgFontHeight);
      return;
    }

    g.drawImage(graph.getImage(getSimulatorConfig()), 0, 0, null);
  }

  private ISimulatorConfig getSimulatorConfig() {
    // TODO: Read Config from somewhere.
    return new SimulatorConfigImpl(2, 1, 5);
  }
}
