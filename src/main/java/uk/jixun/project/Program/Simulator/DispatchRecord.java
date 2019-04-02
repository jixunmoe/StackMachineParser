package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

public class DispatchRecord implements IDispatchRecord, IResourceUsage {
  private int cycleStart = 0;
  private int cycleEnd = 0;
  private int readAddress = 0;
  private int writeAddress = 0;

  private ISmInstruction inst = null;
  private int exeId = -1;
  private IExecutionContext context = null;

  public DispatchRecord() {
  }

  public DispatchRecord(ISmInstruction inst) {
    this.inst = inst;
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
  public boolean isFinished(IExecutionContext ctx) {
    return getInstEndCycle() > ctx.getCurrentCycle();
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
  public boolean readOrWriteRam() {
    return readOrWrite();
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
  public int getEip() {
    return getInstruction().getEip();
  }

  @Override
  public void setEip(int eip) {
    getInstruction().setEip(eip);
  }

  @Override
  public void setExecutionId(int index) {
    exeId = index;
  }

  @Override
  public int getExecutionId() {
    return exeId;
  }

  @Override
  public void setContext(IExecutionContext context) {
    this.context = context;
  }

  @Override
  public IExecutionContext getContext() {
    return context;
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
