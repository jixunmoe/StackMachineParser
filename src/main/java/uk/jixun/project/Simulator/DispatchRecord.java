package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Util.FifoList;

import java.util.List;
import java.util.logging.Logger;

public class DispatchRecord implements IDispatchRecord, IResourceUsage {
  private static Logger logger = Logger.getLogger(DispatchRecord.class.getName());

  private int cycleStart = 0;
  private int cycleEnd = 0;
  private int readAddress = 0;
  private int writeAddress = 0;

  private ISmInstruction inst = null;
  private int exeId = -1;
  private IExecutionContext context = null;
  private boolean executed = false;
  private List<Integer> executionStack = null;

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
  public int getExecutionId() {
    return exeId;
  }

  @Override
  public void setExecutionId(int index) {
    exeId = index;
  }

  @Override
  public IExecutionContext getContext() {
    return context;
  }

  @Override
  public void setContext(IExecutionContext context) {
    this.context = context;
  }

  @Override
  public boolean executed() {
    return executed;
  }

  @Override
  public void executeAndRecord(IExecutionContext context) {
    // Should not be executed yet
    if (executed || getInstruction().notForExecute()) {
      return;
    }

    // Resolve input stack
    ISmOpCode opcode = getInstruction().getOpCode();
    FifoList<Integer> stack = new FifoList<>();
    stack.addAll(context.resolveStack(0, getExecutionId(), opcode.getConsume()));

    try {
      opcode.evaluate(stack, context);
      executed = true;
    } catch (Exception e) {
      logger.warning("Instruction evaluation failed: " + opcode.toAssembly() + " - check stack trace");
      e.printStackTrace();
    }

    this.executionStack = stack;
  }

  @Override
  public List<Integer> getInstructionStack() {
    if (!executed) {
      return null;
    }

    return executionStack;
  }

  @Override
  public boolean endAtCycle(int cycle) {
    return cycle == getInstEndCycle();
  }

  @Override
  public boolean endAtCycle(IExecutionContext context) {
    return endAtCycle(context.getCurrentCycle());
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
