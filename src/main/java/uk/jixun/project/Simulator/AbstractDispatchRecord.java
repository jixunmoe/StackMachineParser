package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

import java.util.logging.Logger;

public abstract class AbstractDispatchRecord implements IDispatchRecord {
  private static Logger logger = Logger.getLogger(AbstractDispatchRecord.class.getName());
  private int cycleStart = 0;
  private int cycleEnd = 0;
  private ISmInstruction inst = null;
  private int exeId = -1;
  private IExecutionContext context = null;

  // Cycle Setup

  public void setCycleStart(int cycleStart) {
    this.cycleStart = cycleStart;
  }

  public void setCycleEnd(int cycleEnd) {
    this.cycleEnd = cycleEnd;
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
  public boolean endAtCycle(int cycle) {
    return cycle == getInstEndCycle();
  }

  @Override
  public boolean endAtCycle() {
    return endAtCycle(getContext().getCurrentCycle());
  }

  @Override
  public boolean executesAt(int cycle) {
    return getInstEndCycle() >= cycle && cycle >= getInstStartCycle();
  }

  @Override
  public boolean isFinished() {
    return getInstEndCycle() > getContext().getCurrentCycle();
  }

  // Setup Instruction

  public void setInst(ISmInstruction inst) {
    this.inst = inst;
  }

  @Override
  public ISmInstruction getInstruction() {
    return inst;
  }

  // Instruction properties

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

  // EIP Setup

  @Override
  public int getEip() {
    return getInstruction().getEip();
  }

  @Override
  public void setEip(int eip) {
    getInstruction().setEip(eip);
  }

  // Execution Id setup

  @Override
  public int getExecutionId() {
    return exeId;
  }

  @Override
  public void setExecutionId(int index) {
    exeId = index;
  }

  // Context Setup

  @Override
  public IExecutionContext getContext() {
    return context;
  }

  @Override
  public void setContext(IExecutionContext context) {
    this.context = context;
  }

  // Dependency

  @Override
  public boolean depends(IDispatchRecord target) {
    // Not in the same program space.
    if (target.getContext() == getContext()) {
      logger.warning("Checking instructions with different context.");
      return false;
    }

    if (target == this) {
      logger.warning("Don't check self dependency!!!");
      return false;
    }

    return getDependencies().contains(target);
  }
}
