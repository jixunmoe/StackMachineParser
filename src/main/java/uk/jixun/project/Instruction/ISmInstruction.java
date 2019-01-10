package uk.jixun.project.Instruction;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.Operand.ISmOperand;

public interface ISmInstruction {
  /**
   * Get operand in this instruction at given index.
   * @param index Index of instruction; start from 0.
   * @return Requested Operand
   * @throws OutOfRangeOperand When the given index is invalid, this exception will be thrown.
   */
  ISmOperand GetOperand(int index) throws OutOfRangeOperand;

  /**
   * Get the number of operands in this instruction.
   * @return Number of operands.
   */
  int GetOperandCount();
}
