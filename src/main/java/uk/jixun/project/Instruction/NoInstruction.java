package uk.jixun.project.Instruction;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.Operand.ISmOperand;

/**
 * Empty line for padding.
 */
public class NoInstruction implements ISmInstruction {
  @Override
  public ISmOperand GetOperand(int index) throws OutOfRangeOperand {
    throw new OutOfRangeOperand(index, this);
  }

  @Override
  public int GetOperandCount() {
    return 0;
  }

  @Override
  public int GetCycleTime() {
    return 0;
  }

  @Override
  public String toAssembly() {
    return "";
  }

  @Override
  public String toString() {
    return "NoInstruction{}";
  }
}
