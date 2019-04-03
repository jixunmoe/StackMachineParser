package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

public interface IDispatchedInstructions {
  int size();

  ISmInstruction get(int index);

  /**
   * Get the number of cycles to span for this instruction.
   *
   * @param index index of dispatched instructions.
   * @return number of cycles took for this instruction.
   * @throws IndexOutOfBoundsException thrown when index is invalid.
   */
  int getCycle(int index) throws IndexOutOfBoundsException;
}
