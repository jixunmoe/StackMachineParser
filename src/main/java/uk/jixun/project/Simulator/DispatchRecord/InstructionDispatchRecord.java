package uk.jixun.project.Simulator.DispatchRecord;

import uk.jixun.project.Helper.LazyCache;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.IExecutable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class InstructionDispatchRecord extends AbstractDispatchRecord implements IDispatchRecord {
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
    if (getInstruction().isMetaInst()) {
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

  @Override
  public boolean needSync() {
    return getInstruction().isBranch();
  }

  @Override
  public String toString() {
    return "InstructionDispatchRecord{"
      + "\n  inst=" + getInstruction().toString() + ","
      + "\n  exeId" + getExecutionId()
      + "\n}";
  }
}
