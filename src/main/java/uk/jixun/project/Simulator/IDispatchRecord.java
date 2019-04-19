package uk.jixun.project.Simulator;

import org.jetbrains.annotations.Nullable;
import uk.jixun.project.Instruction.ISmInstruction;

import java.util.List;

public interface IDispatchRecord {
  int getInstStartCycle();

  int getInstEndCycle();

  int getCycleLength();

  boolean executesAt(int cycle);

  boolean isFinished();

  ISmInstruction getInstruction();

  IResourceUsage getResourceUsed();

  /**
   * Declare if this record uses ALU.
   *
   * @return true if the instruction uses ALU.
   */
  boolean usesAlu();

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
   * Get instruction stack after execution, and execute if not executed yet.
   *
   * @return Instruction stack.
   */
  @Nullable
  List<Integer> executeAndGetStack();

  boolean endAtCycle(int cycle);

  boolean endAtCycle();

  List<IDispatchRecord> getDependencies();

  /**
   * Check if current record depends on a given record.
   *
   * @param record Record to check on.
   * @return Check result, {@code true} if they do depends on.
   */
  boolean depends(IDispatchRecord record);

  boolean needSync();
}
