package uk.jixun.project.Simulator;

import uk.jixun.project.Register.SmRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractExecutionContext implements IExecutionContext {
  private final static int memoryCapacity = 0x4000;
  private ISmHistory history;
  private AtomicInteger eip = new AtomicInteger(0);
  private AtomicInteger cycle = new AtomicInteger(0);
  private AtomicBoolean jumpFlag = new AtomicBoolean(false);
  private ArrayList<Integer> memory = new ArrayList<>(memoryCapacity);
  private final Map<SmRegister, AtomicInteger> registers = setupRegisters();

  private static Map<SmRegister, AtomicInteger> setupRegisters() {
    Map<SmRegister, AtomicInteger> registers = new HashMap<>();
    registers.put(SmRegister.FP, new AtomicInteger(memoryCapacity - 1));
    registers.put(SmRegister.SP, new AtomicInteger(memoryCapacity - 0x1000));
    return registers;
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
  public AtomicInteger getRegister(SmRegister register) {
    // NOS is an alias of TOS-1
    if (register == SmRegister.NOS) {
      return new AtomicInteger(getRegister(SmRegister.TOS).get() - 1);
    }

    // TOS is really SP.
    if (register == SmRegister.TOS) {
      return getRegister(SmRegister.SP);
    }

    synchronized (registers) {
      if (!registers.containsKey(register)) {
        registers.put(register, new AtomicInteger(0));
      }

      return registers.get(register);
    }
  }

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
