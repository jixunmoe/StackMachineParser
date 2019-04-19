package uk.jixun.project.Simulator.DispatchRecord;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import uk.jixun.project.Helper.LazyCache;
import uk.jixun.project.Helper.LazyCacheResolver;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.IExecutable;
import uk.jixun.project.OpCode.SmRegStatus;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.IResourceUsage;
import uk.jixun.project.Simulator.ResourceUsage;
import uk.jixun.project.Util.FifoList;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstructionDispatchRecord extends AbstractDispatchRecord implements IDispatchRecord {
  private final LazyCache<List<IDispatchRecord>> dependencies = new LazyCache<>(this::explicitGetDependencies);
  private LazyCache<IResourceUsage> resourceUsage = new LazyCache<>(this::explicitGetResourceUsed);
  private LazyCache<List<Integer>> executionStack = new LazyCache<>(this::explicitExecuteAndRecordStack);
  private ISmInstruction inst;

  public InstructionDispatchRecord() {
    setInst(null);
    init();
  }

  public InstructionDispatchRecord(ISmInstruction inst) {
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
    assert getExecutionId() >= 0;

    synchronized (dependencies) {
      List<IDispatchRecord> result = dependencies.get();
      if (result == null || !dependencies.isCached()) {
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
    IDependencyResolver resolver = new DependencyResolver(this);
    AtomicInteger nextId = new AtomicInteger(getExecutionId());

    int id = 0;
    if (!resolver.allResolved()) {
      while ((id = nextId.decrementAndGet()) >= 0) {
        // Current record have resolved without requested id.
        IDispatchRecord record = getContext().getHistory().getRecordAt(id);
        if (record == null) {
          // No more items on the chain, break.
          break;
        }

        if (resolver.resolveDependency(record)) {
          break;
        }
      }
    }

    // Should this result be cached?
    boolean dirty = id > 0 && !resolver.allResolved();

    promise.resolve(Lists.reverse(resolver.getDependencies()), dirty);
  }

  @Override
  public String toString() {
    return "InstructionDispatchRecord{"
      + "\n  inst=" + getInstruction().toString() + ","
      + "\n  exeId" + getExecutionId()
      + "\n}";
  }
}
