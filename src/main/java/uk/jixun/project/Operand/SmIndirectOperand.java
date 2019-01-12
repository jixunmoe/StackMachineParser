package uk.jixun.project.Operand;

/**
 * Stack Machine Indirect Operand.
 */
public class SmIndirectOperand implements ISmOperand {
  private long address;

  SmIndirectOperand(long memoryAddress) {
    address = memoryAddress;
  }

  @Override
  public SmOperandType getOperandType() {
    return SmOperandType.INDIRECT;
  }

  @Override
  public void setValue(Object val) {
    if (!(val instanceof Long))
      throw new IllegalArgumentException("Invalid address type for SmIndirectOperand");
    address = (Long) val;
  }

  @Override
  public Object getValue() {
    return address;
  }

  @Override
  public void fromString(String value) {
    // TODO: Parse from string
  }

  @Override
  public String toAssembly() {
    return "ptr:[" + address + "]";
  }

  @Override
  public String toString() {
    return "SmIndirectOperand{" +
      "address=" + address +
      "}";
  }
}
