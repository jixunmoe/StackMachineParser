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
  public SmOperandType GetOperandType() {
    return SmOperandType.INDIRECT;
  }

  @Override
  public void SetValue(Object val) {
    if (!(val instanceof Long))
      throw new IllegalArgumentException("Invalid address type for SmIndirectOperand");
    address = (Long) val;
  }

  @Override
  public Object GetValue() {
    return address;
  }

  @Override
  public void FromString(String value) {
    // TODO: Parse from string
  }

  @Override
  public String toString() {
    return "ptr:[" + address + "]";
  }
}
