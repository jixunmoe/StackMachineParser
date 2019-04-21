package uk.jixun.project.Simulator.DispatchRecord;

import com.google.common.base.Throwables;
import uk.jixun.project.Helper.HLogger;
import uk.jixun.project.Helper.LazyCacheResolver;
import uk.jixun.project.OpCode.IExecutable;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDispatchRecord implements IDispatchRecord {
  protected static Logger logger = HLogger.getLogger(AbstractDispatchRecord.class.getName(), Level.FINE);

  private int cycleStart = 0;
  private int cycleEnd = 0;
  private int exeId = -1;
  private IExecutionContext context = null;
  private int eip = -1;

  // Cycle Setup

  @Override
  public void setCycleStart(int cycleStart) {
    this.cycleStart = cycleStart;
  }

  @Override
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
    return executed() && getContext().getCurrentCycle() > getInstEndCycle();
  }

  // Instruction properties

  @Override
  public boolean usesAlu() {
    return getExecutable().usesAlu();
  }

  @Override
  public boolean reads() {
    return getExecutable().readRam();
  }

  @Override
  public boolean writes() {
    return getExecutable().writeRam();
  }

  @Override
  public boolean readOrWrite() {
    IExecutable executable = getExecutable();
    return executable.readRam() || executable.writeRam();
  }

  // EIP Setup

  @Override
  public int getEip() {
    return eip;
  }

  @Override
  public void setEip(int eip) {
    this.eip = eip;
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
    if (target.getContext() != getContext()) {
      logger.warning("Checking instructions with different context. " + target.getContext().toString() + " <--> " + getContext().toString());
      return false;
    }

    if (target == this) {
      logger.warning("Don't check self dependency!!!");
      return false;
    }

    List<IDispatchRecord> dependencies = getDependencies();
    if (dependencies == null) {
      logger.info("can't resolve dependency yet, assume dependency.\n" + getExecutable().toString());
      return true;
    }

    return dependencies.contains(target);
  }

  void explicitExecuteAndRecordStack(LazyCacheResolver<List<Integer>> promise) {
    IExecutable opcode = getExecutable();
    FifoList<Integer> stack = new FifoList<>();
    stack.addAll(getContext().resolveStack(0, getExecutionId(), opcode.getConsume()));
    String log = null;

    if (logger.isLoggable(Level.FINER)) {
      log = ("\n(dispatch) [" + stack.join(",") + "] => [");
    }

    try {
      opcode.evaluate(stack, getContext());
      promise.resolve(stack);
      if (log != null) {
        log += stack.join(",") + "]";
        logger.finer(log);
      }

    } catch (Exception e) {
      logger.warning(
        "Instruction failed when executing " + getExecutable().toString() + "; " +
          "trace: " + Throwables.getStackTraceAsString(e)
      );
      promise.reject();
    }
  }
}
