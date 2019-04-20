package uk.jixun.project.Simulator.Context;

import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.ISmHistory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public abstract class AbstractExecutionContext implements IExecutionContext {
  protected final static Logger logger = Logger.getLogger(AbstractExecutionContext.class.getName());
  private final static int memoryCapacity = 0x4000;

  // App Space:         0x0000 ~ 0x1FFF
  // Memory Allocation: 0x2000 ~ 0x2FFF
  // Stack / Frame:     0x3000 ~ 0x3FFF   <- shared?
  private final static int appSpace     = 0;
  private final static int allocAddress = 0x2000;
  private final static int stackAddress = 0x3000;
  private final static int frameAddress = 0x3FFF;
  // FIXME: Proper memory page allocation.

  private ISmHistory history;
  private AtomicInteger eip = new AtomicInteger(0);
  private AtomicInteger cycle = new AtomicInteger(0);
  private AtomicBoolean jumpFlag = new AtomicBoolean(false);
  private List<Integer> memory = setupMemory(memoryCapacity);
  private final Map<SmRegister, AtomicInteger> registers = setupRegisters();

  private boolean halt = false;

  private static Map<SmRegister, AtomicInteger> setupRegisters() {
    Map<SmRegister, AtomicInteger> registers = new HashMap<>();
    registers.put(SmRegister.FP, new AtomicInteger(frameAddress));
    registers.put(SmRegister.SP, new AtomicInteger(stackAddress));
    registers.put(SmRegister.ALLOC_REGISTER, new AtomicInteger(allocAddress));
    return registers;
  }

  private static List<Integer> setupMemory(int size) {
    ArrayList<Integer> memory = new ArrayList<>(size);
    for(int i = 0; i < size; i++) {
      memory.add(0);
    }
    return memory;
  }

  @Override
  public int read(int address) {
    int value = memory.get(address);
    logger.info(String.format("read from 0x%04x: %d", address, value));
    return value;
  }

  @Override
  public void write(int address, int value) {
    memory.set(address, value);
    logger.info(String.format("write to 0x%04x: %d", address, value));
  }

  @Override
  public List<Integer> dump(int start, int len) {
    assert len >= 0;
    return memory.subList(start, start + len);
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
      logger.info("Change EIP to " + eip);
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

  @Override
  public void halt(boolean halt) {
    this.halt = halt;
  }

  @Override
  public void halt() {
    halt(true);
  }

  @Override
  public boolean isHalt() {
    return halt;
  }
}
