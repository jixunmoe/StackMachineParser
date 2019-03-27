package uk.jixun.project.Operand;

import uk.jixun.project.Program.Simulator.IExecutionContext;

/**
 * Stack Machine Indirect Operand.
 */
public class SmIndirectOperand extends SmBasicOperandAbstract {
  SmIndirectOperand() {
    setValue(0);
  }
  SmIndirectOperand(long value) {
    setValue(value);
  }

  @Override
  public SmOperandType getOperandType() {
    return SmOperandType.INDIRECT;
  }

  @Override
  public String toAssembly() {
    return "ptr:[" + getValue() + "]";
  }

  @Override
  public int resolve(IExecutionContext ctx) {
    return ctx.read((int)getValue());
  }

  @Override
  public String toString() {
    return "SmIndirectOperand{" +
      "address=" + getValue() +
      "}";
  }
}
