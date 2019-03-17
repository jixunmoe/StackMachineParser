package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Gui.GuiManager;
import uk.jixun.project.Instruction.ISmInstruction;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SmDependencyNode implements ISmDependencyNode {
  private static Font nodeFont;
  private static final int padding = 3;

  static {
    nodeFont = GuiManager.getFontMono();
  }

  private Stack<ISmProgramNode> stack = new Stack<>();
  private ISmInstruction instruction;
  private List<ISmProgramNode> dependencies = new ArrayList<>();

  @Override
  public ISmInstruction getInstruction() {
    return instruction;
  }

  @Override
  public void setInstruction(ISmInstruction instruction) {
    this.instruction = instruction;
  }

  @Override
  public boolean dependsOn(ISmProgramNode node) {
    List<ISmProgramNode> dependencies = getDependencies();
    List<ISmProgramNode> visited = new ArrayList<>();

    while(!dependencies.isEmpty()) {
      int n = dependencies.size();

      for (int i = 0; i < n; i++) {
        ISmProgramNode dep = dependencies.get(i);
        visited.add(dep);

        // Contains dependency to given node?
        if (dep == node) {
          return true;
        }

        // Include dependency of the dependency to the list
        dependencies.addAll(dep.getDependencies());
      }

      // Remove checked nodes, so they are not checked again.
      for (int i = 0; i < n; i++) {
        dependencies.removeIf(visited::contains);
      }
    }

    return false;
  }

  public SmDependencyNode addDependency(ISmProgramNode dep) {
    dependencies.add(dep);
    return this;
  }

  @Override
  public List<ISmProgramNode> getDependencies() {
    return dependencies;
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
  public Image getImage() {
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
