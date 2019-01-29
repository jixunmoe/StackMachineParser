package uk.jixun.project.Gui;

import uk.jixun.project.Program.NodeGraph.ISmProgramNodeGraph;

import javax.swing.*;
import java.awt.*;

public class GraphCanvas extends JPanel {
  private ISmProgramNodeGraph graph = null;
  private Font msgFont;
  private int msgFontHeight;

  public ISmProgramNodeGraph getGraph() {
    return graph;
  }

  public GraphCanvas() {
    setBorder(BorderFactory.createLineBorder(Color.black));
    msgFont = new Font("Courier New", Font.PLAIN, 12);
    FontMetrics metrics = getFontMetrics(msgFont);
    msgFontHeight = metrics.getAscent();
  }

  public Dimension getPreferredSize() {
    return new Dimension(400,400);
  }

  public void setGraph(ISmProgramNodeGraph graph) {
    this.graph = graph;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (graph == null) {
      g.setFont(msgFont);
      g.drawString("No Graph Loaded", 10, 10 + msgFontHeight);
      return;
    }

    graph.getImage(g);
  }
}
