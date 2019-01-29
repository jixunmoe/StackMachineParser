package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Gui.GuiManager;
import uk.jixun.project.Instruction.ISmInstruction;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.Stack;

public class SmBasicProgramNode implements ISmProgramNode {
  private static Font nodeFont;
  private static final int padding = 3;

  static {
    nodeFont = GuiManager.getFontMono();
  }

  private Stack<ISmProgramNode> stack = new Stack<>();
  private ISmInstruction instruction;

  @Override
  public ISmInstruction getInstruction() {
    return instruction;
  }

  @Override
  public void setInstruction(ISmInstruction instruction) {
    this.instruction = instruction;
  }

  @Override
  public Stack<ISmProgramNode> getConsumedInstructions() {
    return stack;
  }

  @Override
  public void pushStack(ISmProgramNode instruction) {
    getConsumedInstructions().push(instruction);
  }

  @Override
  public RenderedImage getImage() {
    String text = getInstruction().toAssembly();
    Rectangle2D rect = GuiManager.getFontBound(nodeFont, text);

    int w = (int)(rect.getWidth())  + padding * 2;
    int h = (int)(rect.getHeight()) + padding * 2;

    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    Graphics g = image.getGraphics();

    g.setColor(GuiManager.getNodeBorderColour());
    g.drawRect(0, 0, w, h);

    g.setColor(GuiManager.getNodeBackgroundColour());
    g.fillRect(1, 1, w - 2, h - 2);

    g.setColor(GuiManager.getNodeTextColour());
    g.drawString(text, padding, padding + (int)rect.getHeight());

    return image;
  }
}
