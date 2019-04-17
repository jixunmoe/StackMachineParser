package uk.jixun.project.Program;

import uk.jixun.project.Exceptions.LabelDuplicationException;
import uk.jixun.project.Exceptions.LabelNotFoundException;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Program.NodeGraph.ISmProgramGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockProgram implements ISmProgram {
  private List<ISmInstruction> instructions;
  private Map<String, Long> mapping = new HashMap<>();

  public MockProgram() {
    this.instructions = new ArrayList<>();
  }

  @Override
  public List<ISmInstruction> getInstructions() {
    return instructions;
  }

  @Override
  public void setInstructions(List<ISmInstruction> instructions) {
    this.instructions = instructions;
  }

  @Override
  public ISmInstruction getInstruction(int index) throws ArrayIndexOutOfBoundsException {
    return instructions.get(index);
  }

  @Override
  public void addInstruction(ISmInstruction instruction) {
    instructions.add(instruction);
  }

  @Override
  public Map<String, Long> getLabelMapping() {
    return mapping;
  }

  @Override
  public void registerLabel(String label, long address) throws LabelDuplicationException {
    mapping.put(label, address);
  }

  @Override
  public long resolveLabel(String label) throws LabelNotFoundException {
    return mapping.getOrDefault(label, -1L);
  }

  @Override
  public ISmProgramGraph createGraph() {
    return null;
  }
}
