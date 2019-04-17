package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

import java.util.List;

public interface IDispatchRecord {
  int getInstStartCycle();

  int getInstEndCycle();

  int getCycleLength();

  boolean executesAt(int cycle);

  boolean isFinished(IExecutionContext cxt);

  ISmInstruction getInstruction();

  IResourceUsage getResourceUsed();

  /**
   * Declare if this record uses ALU.
   *
   * @return true if the instruction uses ALU.
   */
  boolean usesAlu();

  boolean readOrWriteRam();

  boolean reads();

  boolean writes();

  boolean readOrWrite();

  int getEip();

  void setEip(int eip);

  int getExecutionId();

  void setExecutionId(int index);

  IExecutionContext getContext();

  void setContext(IExecutionContext context);

  /**
   * Check if current instruction executed yet.
   *
   * @return True if already executed.
   */
  boolean executed();

  /**
   * Execute current instruction with given context, then save the result.
   *
   * @param context Context where the instruction executes.
   */
  void executeAndRecord(IExecutionContext context);

  /**
   * Get instruction stack after execution.
   *
   * @return `null` if not executed.
   */
  List<Integer> getInstructionStack();

  boolean endAtCycle(int cycle);

  boolean endAtCycle(IExecutionContext context);

  List<IDispatchRecord> getDependencies();

  /**
   * Check if current record depends on a given record.
   *
   * @param record Record to check on.
   * @return Check result, {@code true} if they do depends on.
   */
  boolean depends(IDispatchRecord record);
}
