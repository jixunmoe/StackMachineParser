package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

public interface IDispatchRecord {
  int getCycle();

  int getInstStartCycle();
  int getInstEndCycle();

  ISmInstruction getInstruction();
  IResourceUsage getResourceUsed();
}
