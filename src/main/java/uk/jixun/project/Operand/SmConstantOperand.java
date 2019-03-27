package uk.jixun.project.Operand;

import uk.jixun.project.Program.Simulator.IExecutionContext;

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
  public int resolve(IExecutionContext ctx) {
    return (int)getValue();
  }

  @Override
  public String toString() {
    return "SmConstantOperand{" +
      "value=" + getValue() +
      "}";
  }
}
