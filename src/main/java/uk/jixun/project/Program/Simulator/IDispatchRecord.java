package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

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
   * @return true if the instruction uses ALU.
   */
  boolean usesAlu();
  boolean readOrWriteRam();

  boolean reads();
  boolean writes();
  boolean readOrWrite();

  int getEip();
  void setEip(int eip);

  void setExecutionId(int index);
  int getExecutionId();
}
