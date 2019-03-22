package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

public interface IDispatchRecord {
  int getCycle();

  int getInstStartCycle();
  int getInstEndCycle();
  int getCycleLength();

  boolean executesAt(int cycle);

  ISmInstruction getInstruction();
  IResourceUsage getResourceUsed();

  /**
   * Declare if this record uses ALU.
   * @return true if the instruction uses ALU.
   */
  boolean usesAlu();

  boolean reads();
  boolean writes();
  boolean readOrWrite();
}
