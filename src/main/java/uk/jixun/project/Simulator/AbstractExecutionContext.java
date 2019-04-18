package uk.jixun.project.Simulator;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractExecutionContext implements IExecutionContext {
  private ISmHistory history;
  private AtomicInteger eip = new AtomicInteger(0);
  private AtomicInteger cycle = new AtomicInteger(0);
  private AtomicBoolean jumpFlag = new AtomicBoolean(false);

  @Override
  public int getEip() {
    return eip.get();
  }

  @Override
  public void setEip(int eip) {
    if (eip >= 0) {
      this.eip.set(eip);
    }
  }

  @Override
  public int resolveStack(int offset, int exeId) {
    return resolveStack(offset, exeId, 1).get(0);
  }

  @Override
  public void incEip() {
    this.eip.incrementAndGet();
  }

  @Override
  public int getCurrentCycle() {
    return this.cycle.get();
  }

  @Override
  public void nextCycle() {
    cycle.incrementAndGet();
  }

  @Override
  public void addCycles(int cycles) {
    assert cycles >= 0;
    cycle.addAndGet(cycles);
  }

  @Override
  public boolean getJumpFlag() {
    return jumpFlag.get();
  }

  @Override
  public void setJumpFlag(boolean flag) {
    jumpFlag.set(flag);
  }

  @Override
  public ISmHistory getHistory() {
    return this.history;
  }

  @Override
  public void setHistory(ISmHistory smHistory) {
    this.history = smHistory;
  }
}
