package uk.jixun.project.Gui;

import uk.jixun.project.Program.NodeGraph.ISmProgramGraph;

import javax.swing.*;
import java.awt.*;

public class GraphCanvas extends JPanel {
  private ISmProgramGraph graph = null;
  private Font msgFont;
  private int msgFontHeight;

  public ISmProgramGraph getGraph() {
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

  public void setGraph(ISmProgramGraph graph) {
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

    g.drawImage(graph.getImage(), 0, 0, null);
  }
}
