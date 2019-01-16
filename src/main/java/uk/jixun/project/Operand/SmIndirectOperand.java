package uk.jixun.project.Operand;

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
  public String toString() {
    return "SmIndirectOperand{" +
      "address=" + getValue() +
      "}";
  }
}
