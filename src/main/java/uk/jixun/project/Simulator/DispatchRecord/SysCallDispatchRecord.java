package uk.jixun.project.Simulator.DispatchRecord;

import org.jetbrains.annotations.Nullable;
import uk.jixun.project.Helper.LazyCache;
import uk.jixun.project.OpCode.IExecutable;
import uk.jixun.project.OpCode.SysCall.ISysCall;

import java.util.List;
import java.util.logging.Logger;

public class SysCallDispatchRecord extends AbstractDispatchRecord {
  private final static Logger logger = Logger.getLogger(SysCallDispatchRecord.class.getName());
  private LazyCache<List<Integer>> executionStack = new LazyCache<>(this::explicitExecuteAndRecordStack);
  private ISysCall sysCall;

  @Override
  public boolean executed() {
    return executionStack.isCached();
  }

  @Override
  public @Nullable List<Integer> executeAndGetStack() {
    return executionStack.get();
  }

  @Override
  public boolean needSync() {
    return true;
  }

  public void setSysCall(ISysCall sysCall) {
    this.sysCall = sysCall;
  }

  @Override
  public IExecutable getExecutable() {
    return sysCall;
  }
}
