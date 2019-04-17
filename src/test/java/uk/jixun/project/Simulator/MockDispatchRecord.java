package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

import java.util.List;

public class MockDispatchRecord extends AbstractDispatchRecord {
  private List<IDispatchRecord> dependencies = null;

  @Override
  public IResourceUsage getResourceUsed() {
    return null;
  }

  @Override
  public boolean executed() {
    return false;
  }

  @Override
  public List<Integer> executeAndGetStack() {
    return null;
  }

  @Override
  public List<IDispatchRecord> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<IDispatchRecord> dependencies) {
    this.dependencies = dependencies;
  }

  public static MockDispatchRecord createFromInstruction(ISmInstruction inst) {
    MockDispatchRecord record = new MockDispatchRecord();
    record.setInst(inst);
    return record;
  }

  @Override
  public String toString() {
    return "MockDispatchRecord{" +
      "exe id=" + getExecutionId() + ", " +
      "inst=" + getInstruction().toString() +
      '}';
  }
}
