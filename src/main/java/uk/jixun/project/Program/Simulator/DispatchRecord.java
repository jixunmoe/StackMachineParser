package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

public class DispatchRecord implements IDispatchRecord {
  private int cycle;
  private int cycleStart;
  private int cycleEnd;
  private ISmInstruction inst = null;
  private IResourceUsage res = null;

  public DispatchRecord(int cycle, int cycleStart, int cycleEnd) {
    this.cycle = cycle;
    this.cycleStart = cycleStart;
    this.cycleEnd = cycleEnd;
  }

  public void setCycle(int cycle) {
    this.cycle = cycle;
  }

  public void setCycleStart(int cycleStart) {
    this.cycleStart = cycleStart;
  }

  public void setCycleEnd(int cycleEnd) {
    this.cycleEnd = cycleEnd;
  }

  public void setInst(ISmInstruction inst) {
    this.inst = inst;
  }

  public void setRes(IResourceUsage res) {
    this.res = res;
  }

  @Override
  public int getCycle() {
    return cycle;
  }

  @Override
  public int getInstStartCycle() {
    return cycleStart;
  }

  @Override
  public int getInstEndCycle() {
    return cycleEnd;
  }

  @Override
  public ISmInstruction getInstruction() {
    return inst;
  }

  @Override
  public IResourceUsage getResourceUsed() {
    return res;
  }
}
