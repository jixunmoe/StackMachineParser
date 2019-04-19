package uk.jixun.project.Simulator;

import com.google.common.base.Throwables;
import org.jetbrains.annotations.Nullable;
import uk.jixun.project.Helper.LazyCache;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.OpCode.SysCall.ISysCall;
import uk.jixun.project.Util.FifoList;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class SysCallDispatchRecord extends AbstractDispatchRecord {
  private final static Logger logger = Logger.getLogger(SysCallDispatchRecord.class.getName());
  private LazyCache<List<Integer>> executionStack = new LazyCache<>(this::explicitExecuteAndRecordStack);
  private ISysCall sysCall;

  private List<Integer> explicitExecuteAndRecordStack() {
    ISmOpCode opcode = getInstruction().getOpCode();
    FifoList<Integer> stack = new FifoList<>();
    stack.addAll(getContext().resolveStack(0, getExecutionId(), opcode.getConsume()));
    try {
      opcode.evaluate(stack, getContext());
    } catch (Exception e) {
      logger.warning(
        "Instruction failed when executing " + getInstruction().toAssembly() + "; " +
          "trace: " + Throwables.getStackTraceAsString(e)
      );
      return Collections.emptyList();
    }
    return stack;
  }

  @Override
  public IResourceUsage getResourceUsed() {
    return null;
  }

  @Override
  public boolean executed() {
    return executionStack.isCached();
  }

  @Override
  public @Nullable List<Integer> executeAndGetStack() {
    return executionStack.get();
  }

  @Override
  public List<IDispatchRecord> getDependencies() {
    return Collections.singletonList(getContext().getHistory().getLastRecord());
  }

  @Override
  public boolean needSync() {
    return true;
  }
}
