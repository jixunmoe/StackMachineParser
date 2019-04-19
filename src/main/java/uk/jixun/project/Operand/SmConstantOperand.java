package uk.jixun.project.Operand;

import uk.jixun.project.Simulator.Context.IExecutionContext;

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
    Object value = getValue();
    if (value instanceof Long) {
      return (int)(long) value;
    }
    return (int) value;
  }

  @Override
  public String toString() {
    return "SmConstantOperand{" +
      "value=" + getValue() +
      "}";
  }
}
