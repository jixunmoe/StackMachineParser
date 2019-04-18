package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

public class MockHistory extends SmHistory {
  public void add(ISmInstruction ...instructions) {
    for (ISmInstruction inst : instructions) {
      add(MockDispatchRecord.createFromInstruction(inst));
    }
  }

  public void add(IDispatchRecord ...records) {
    for (IDispatchRecord inst : records) {
      add(inst);
    }
  }

  public static MockHistory create() {
    return new MockHistory();
  }
}
