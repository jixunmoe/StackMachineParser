package uk.jixun.project.Simulator;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import uk.jixun.project.Helper.LazyCache;
import uk.jixun.project.Helper.LazyCacheResolver;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.IExecutable;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Util.FifoList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DispatchRecord extends AbstractDispatchRecord implements IDispatchRecord {
  private final static Logger logger = Logger.getLogger(DispatchRecord.class.getName());

  private LazyCache<List<IDispatchRecord>> dependencies = new LazyCache<>(this::explicitGetDependencies);
  private LazyCache<IResourceUsage> resourceUsage = new LazyCache<>(this::explicitGetResourceUsed);
  private LazyCache<List<Integer>> executionStack = new LazyCache<>(this::explicitExecuteAndRecordStack);
  private ISmInstruction inst;

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

  private void explicitGetResourceUsed(LazyCacheResolver<IResourceUsage> promise) {
    promise.resolve(ResourceUsage.fromInstruction(getInstruction()));
  }

  // Setup Instruction

  public void setInst(ISmInstruction inst) {
    this.inst = inst;
  }

  public ISmInstruction getInstruction() {
    return inst;
  }

  @Override
  public IExecutable getExecutable() {
    return getInstruction().getOpCode();
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

    if (!executed() && logger.isLoggable(Level.FINE)) {
      logger.fine(String.format(
        "c%03d: %03d: %s",
        getContext().getCurrentCycle(),
        getInstruction().getVirtualAddress(),
        getInstruction().toAssembly()
      ));
    }
    return executionStack.get();
  }

  // Resolve Dependency

  @Override
  public List<IDispatchRecord> getDependencies() {
    assert getExecutionId() != -1;
    assert canResolveDependency();

    synchronized (dependencies) {
      List<IDispatchRecord> result = dependencies.get();
      if (result == null || dependencies.isCached()) {
        return null;
      }
      return result;
    }
  }

  @Override
  public boolean needSync() {
    return getInstruction().isBranch();
  }

  private void explicitGetDependencies(LazyCacheResolver<List<IDispatchRecord>> promise) {
    FifoList<IDispatchRecord> dependencies = new FifoList<>();
    IExecutable mainExe = getExecutable();

    // Begin resolve dependency node
    int paramSkips = 0;
    int size = mainExe.getConsume();
    AtomicInteger nextId = new AtomicInteger(getExecutionId() - 1);

    SmRegister register = mainExe.getRegisterAccess();
    boolean foundFlag = !mainExe.isReadFlag();

    while (size > 0) {
      int id = nextId.getAndDecrement();
      if (id < 0) break;

      // Current record have resolved without requested id.
      IDispatchRecord record = getContext().getHistory().getRecordAt(id);
      if (record == null) {
        // No more items on the chain, break.
        break;
      }

      IExecutable exe = record.getExecutable();
      int consumes = exe.getConsume();
      int produces = exe.getProduce();

      // Skip if required.
      int skipThisTime = Math.min(paramSkips, produces);
      if (paramSkips > 0) {
        paramSkips -= skipThisTime;
        produces -= skipThisTime;
      }

      // If previous instruction produces any useful result, count it.
      if (produces > 0) {
        size -= produces;
        dependencies.pushUnique(record);
      }

      if (!foundFlag && exe.isWriteFlag()) {
        foundFlag = true;
        dependencies.pushUnique(record);
      }

      if (register != SmRegister.NONE) {
        if (register == exe.getRegisterAccess()) {
          // TODO: Check if we depend on the register.
          if (/* ??? */ true) {
            dependencies.pushUnique(record);
          }
        }
      }

      // FIXME: Other dependency check (e.g. address)


      // Increase the number of items to skip next round.
      paramSkips += consumes;
    }

    promise.resolve(Lists.reverse(dependencies));
  }
}
