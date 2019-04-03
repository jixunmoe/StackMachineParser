package uk.jixun.project.Simulator;

import java.util.ArrayList;
import java.util.Stack;

public class SimulatorContext implements IExecutionContext {
  private ArrayList<Integer> memory;
  private Stack<Integer> stack;
  private int eip = 0;
  private int cycles = 0;
  private boolean testFlag = false;
  private ISmHistory history = null;

  public SimulatorContext() {
    memory = new ArrayList<>(256);
  }

  @Override
  public int read(int address) {
    return memory.get(address);
  }

  @Override
  public void write(int address, int value) {
    memory.set(address, value);
  }

  @Override
  public void setStack(int offset, int value) {
    stack.set(stack.size() - 1 - offset, value);
  }

  @Override
  public int getStack(int offset) {
    return stack.get(stack.size() - 1 - offset);
  }

  @Override
  public void push(int value) {
    stack.push(value);
  }

  @Override
  public int pop() {
    return stack.pop();
  }

  @Override
  public int getEip() {
    return eip;
  }

  @Override
  public void setEip(int eip) {
    if (eip >= 0) {
      this.eip = eip;
    }
  }

  @Override
  public void incEip() {
    eip++;
  }

  @Override
  public int getCurrentCycle() {
    return cycles;
  }

  @Override
  public void nextCycle() {
    addCycles(1);
  }

  @Override
  public void addCycles(int cycles) {
    this.cycles += cycles;
  }

  @Override
  public boolean getJumpFlag() {
    return testFlag;
  }

  @Override
  public void setJumpFlag(boolean flag) {
    testFlag = flag;
  }

  @Override
  public ISmHistory getHistory() {
    return history;
  }

  @Override
  public void setHistory(ISmHistory smHistory) {
    history = smHistory;
  }
}
