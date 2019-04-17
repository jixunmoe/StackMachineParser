package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Util.FifoList;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class DispatchRecord implements IDispatchRecord, IResourceUsage {
  private static Logger logger = Logger.getLogger(DispatchRecord.class.getName());
  final private List<IDispatchRecord> dependencies = new LinkedList<>();
  private int cycleStart = 0;
  private int cycleEnd = 0;
  private int readAddress = 0;
  private int writeAddress = 0;
  private ISmInstruction inst = null;
  private int exeId = -1;
  private IExecutionContext context = null;
  private boolean executed = false;
  private List<Integer> executionStack = null;
  private AtomicBoolean dependencyResolved = new AtomicBoolean(false);

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
  public List<IDispatchRecord> getDependencies() {
    synchronized (dependencies) {
      if (!dependencyResolved.get()) {
        // Begin resolve dependency node
        int paramSkips = 0;
        int size = getInstruction().getOpCode().getConsume();
        int nextId = getExecutionId() - 1;

        while (size > 0) {
          // Current record have resolved without requested id.
          IDispatchRecord record = getContext().getHistory().getRecordAt(nextId);
          if (record == null) {
            // No more items on the chain, break.
            break;
          }

          ISmOpCode opcode = record.getInstruction().getOpCode();
          int consumes = opcode.getConsume();
          int produces = opcode.getProduce();

          // Skip if required.
          int skipThisTime = Math.min(paramSkips, produces);
          if (paramSkips > 0) {
            paramSkips -= skipThisTime;
            produces -= skipThisTime;
          }

          // If previous instruction produces any useful result, count it.
          if (produces > 0) {
            size -= produces;
            dependencies.add(record);
          }

          // Increase the number of items to skip next round.
          paramSkips += consumes;
        }

        dependencyResolved.set(true);
      } // if (!dependencyResolved)
    }

    return dependencies;
  }

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
