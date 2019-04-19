package uk.jixun.project.Operand;

import uk.jixun.project.Helper.ParseHelper;
import uk.jixun.project.Instruction.ISmInstruction;

public abstract class SmBasicOperandAbstract implements ISmOperand {
  private int value;
  private ISmInstruction instruction;

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public void setValue(Object val) {
    if (val instanceof Long) {
      this.value = (int) (long) val;
    } else {
      this.value = (int) val;
    }
  }

  @Override
  public ISmInstruction getInstruction() {
    return instruction;
  }

  @Override
  public void setInstruction(ISmInstruction instruction) {
    this.instruction = instruction;
  }

  @Override
  public void fromString(String value) {
    setValue(ParseHelper.parseLong(value));
  }

  @Override
  public String toAssembly() {
    // TODO: Format by a given param?
    return getValue().toString();
  }
}
