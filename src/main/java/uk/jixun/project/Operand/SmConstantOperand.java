package uk.jixun.project.Operand;

import uk.jixun.project.Helper.ParseHelper;
import uk.jixun.project.Instruction.ISmInstruction;

/**
 * Stack Machine Constant Operand.
 */
public class SmConstantOperand extends SmBasicOperandAbstract {
  SmConstantOperand() {
    setValue(0);
  }
  SmConstantOperand(long value) {
    setValue(value);
  }

  @Override
  public SmOperandType getOperandType() {
    return SmOperandType.CONSTANT;
  }

  @Override
  public String toString() {
    return "SmConstantOperand{" +
      "value=" + getValue() +
      "}";
  }
}
