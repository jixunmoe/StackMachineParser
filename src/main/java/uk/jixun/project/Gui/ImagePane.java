package uk.jixun.project.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePane extends JComponent {
  private BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

  public ImagePane() {
    setFont(GuiManager.getFontMono());
  }

  @Override
  protected void paintChildren(Graphics g) {
    super.paintChildren(g);

    g.setFont(getFont());

    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());

    g.setColor(Color.BLACK);
    g.drawString("No execution History loaded.", 10, 10 + getFontMetrics(getFont()).getHeight());

    // g.drawImage(image, 0, 0, null);
  }

  @Override
  public int getWidth() {
    return Math.max(image.getWidth(), super.getWidth());
  }

  @Override
  public int getHeight() {
    return Math.max(image.getHeight(), super.getHeight());
  }
}
