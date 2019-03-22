package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

  private int cycle;
  private int cycleStart;
  private int cycleEnd;
public class DispatchRecord implements IDispatchRecord, IResourceUsage {
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
  public int getCycleLength() {
    // Because both start and end cycle are inclusive, add 1 to it.
    return getInstEndCycle() - getInstStartCycle() + 1;
  }

  @Override
  public boolean executesAt(int cycle) {
    return getInstEndCycle() >= cycle && cycle >= getInstStartCycle();
  }

  @Override
  public ISmInstruction getInstruction() {
    return inst;
  }

  @Override
  public IResourceUsage getResourceUsed() {
    return this;
  }

  @Override
  public boolean usesAlu() {
    return getInstruction().usesAlu();
  }

  @Override
  public boolean reads() {
    return getInstruction().readRam();
  }

  @Override
  public boolean writes() {
    return getInstruction().writeRam();
  }

  @Override
  public boolean readOrWrite() {
    return getInstruction().readOrWriteRam();
  }

  @Override
  public int getReadAddress() {
    return readAddress;
  }

  @Override
  public void setReadAddress(int address) {
    if (reads()) {
      readAddress = address;
    }
  }

  @Override
  public int getWriteAddress() {
    return writeAddress;
  }

  @Override
  public void setWriteAddress(int address) {
    if (writes()) {
      writeAddress = address;
    }
  }
}
