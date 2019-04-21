package uk.jixun.project.Gui;

import uk.jixun.project.Helper.FontLookup;
import uk.jixun.project.Simulator.ISmHistory;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;
import uk.jixun.project.SimulatorConfig.SimulatorConfigImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphCanvas extends JComponent implements IGraphCanvas {
  private ISmHistory history = null;
  private BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

  public GraphCanvas() {
    setFont(GuiManager.getFontMono());
  }

  @Override
  protected void paintChildren(Graphics g) {
    super.paintChildren(g);

    g.setFont(getFont());

    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());

    if (history == null) {
      g.setColor(Color.BLACK);
      g.drawString("No execution History loaded.", 10, 10 + getFontMetrics(getFont()).getHeight());
    }
    // g.drawImage(graph.getImage(getSimulatorConfig()), 0, 0, null);
  }

  @Override
  public int getWidth() {
    return Math.max(image.getWidth(), super.getWidth());
  }

  @Override
  public int getHeight() {
    return Math.max(image.getHeight(), super.getHeight());
  }

  private ISimulatorConfig getSimulatorConfig() {
    // TODO: Read Config from somewhere.
    return new SimulatorConfigImpl(2, 2, 5);
  }
}
