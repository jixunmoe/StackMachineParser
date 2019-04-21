package uk.jixun.project.Gui;

import uk.jixun.project.SimulatorConfig.ISimulatorConfig;
import uk.jixun.project.SimulatorConfig.SimulatorConfigImpl;

import javax.swing.*;
import java.awt.*;

public class GraphCanvas extends JPanel {
  private Font msgFont;
  private int msgFontHeight;

  public GraphCanvas() {
    setBorder(BorderFactory.createLineBorder(Color.black));
    msgFont = new Font("Courier New", Font.PLAIN, 12);
    FontMetrics metrics = getFontMetrics(msgFont);
    msgFontHeight = metrics.getAscent();
  }

  public Dimension getPreferredSize() {
    return new Dimension(400, 400);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // g.drawImage(graph.getImage(getSimulatorConfig()), 0, 0, null);
  }

  private ISimulatorConfig getSimulatorConfig() {
    // TODO: Read Config from somewhere.
    return new SimulatorConfigImpl(2, 1, 5);
  }
}
