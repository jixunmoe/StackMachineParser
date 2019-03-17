package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Helper.GridData;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.RenderConfig.IRenderConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SmExecutionGraph implements ISmProgramGraph {
  private ISmProgram program;

  private List<ISmProgramNode> nodes;

  private int aluCount;
  private int ramCount;
  private BufferedImage image;
  private GridData<ISmProgramNode> nodesTable;

  @Override
  public void setProgram(ISmProgram program) {
    this.program = program;
    invalidateCache();
  }

  @Override
  public boolean containsNode(ISmProgramNode node) {
    return nodes.contains(node);
  }

  @Override
  public Image getImage(IRenderConfig config) {
    if (aluCount != config.getAluCount() || ramCount != config.getMemoryPortCount()) {
      image = null;

      ramCount = config.getMemoryPortCount();
      aluCount = config.getMemoryPortCount();
    }

    if (program == null) {
      throw new IllegalStateException("call setProgram to initliase.");
    }

    if (nodes == null) {
      cacheNodes();
    }

    if (nodesTable == null) {
      cacheNodeTable();
    }

    if (image == null) {
      cacheImage();
    }

    return image;
  }

  private void invalidateCache() {
    nodes = null;
    image = null;
    aluCount = 0;
    ramCount = 0;
    nodesTable = null;
  }

  private void cacheNodes() {
    nodes = new ArrayList<>();

    for (ISmInstruction inst : program.getInstructions()) {
      ISmProgramNode node = new SmExecutionNode();
      node.setInstruction(inst);
      nodes.add(node);
    }
  }

  private void cacheNodeTable() {
    // TODO: Cache node table
  }

  private void cacheImage() {
    // new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
  }
}
