package uk.jixun.project.Program.NodeGraph;

import uk.jixun.project.Instruction.ISmInstruction;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.util.List;
import java.util.Stack;

public interface ISmProgramNodeGraph {
  Stack<ISmProgramNode> getInstructionStack();
  List<ISmProgramNode> getDisconnectedNodes();

  void addInstruction(ISmInstruction instruction);

  RenderedImage getImage();
}
