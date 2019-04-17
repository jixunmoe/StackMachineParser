package uk.jixun.project.Simulator;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import uk.jixun.project.Helper.LazyCache;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Util.FifoList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class DispatchRecord extends AbstractDispatchRecord implements IDispatchRecord {
  private final static Logger logger = Logger.getLogger(DispatchRecord.class.getName());

  private LazyCache<List<IDispatchRecord>> dependencies = new LazyCache<>(this::explicitGetDependencies);
  private LazyCache<IResourceUsage> resourceUsage = new LazyCache<>(this::explicitGetResourceUsed);
  private LazyCache<List<Integer>> executionStack = new LazyCache<>(this::explicitExecuteAndRecordStack);

  public DispatchRecord() {
    setInst(null);
    init();
  }

  public DispatchRecord(ISmInstruction inst) {
    setInst(inst);
    init();
  }

  private void init() {

  }

  @Override
  public IResourceUsage getResourceUsed() {
    return resourceUsage.get();
  }

  private IResourceUsage explicitGetResourceUsed() {
    return ResourceUsage.fromInstruction(getInstruction());
  }

  // Instruction execution

  @Override
  public boolean executed() {
    return executionStack.isCached();
  }

  @Override
  public List<Integer> executeAndGetStack() {
    if (getInstruction().notForExecute()) {
      logger.info("try to execute non-executable instruction: " + getInstruction().toAssembly());
      return Collections.emptyList();
    }

    return executionStack.get();
  }

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

  // Resolve Dependency

  @Override
  public List<IDispatchRecord> getDependencies() {
    assert getExecutionId() != -1;
    return dependencies.get();
  }

  private List<IDispatchRecord> explicitGetDependencies() {
    List<IDispatchRecord> dependencies = new LinkedList<>();
    // Begin resolve dependency node
    int paramSkips = 0;
    int size = getInstruction().getOpCode().getConsume();
    AtomicInteger nextId = new AtomicInteger(getExecutionId() - 1);

    while (size > 0) {
      // Current record have resolved without requested id.
      IDispatchRecord record = getContext().getHistory().getRecordAt(nextId.getAndDecrement());
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

    return Lists.reverse(dependencies);
  }
}
