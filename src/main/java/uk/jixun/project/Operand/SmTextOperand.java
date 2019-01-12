package uk.jixun.project.Operand;

public class SmTextOperand implements ISmOperand {
  private String text;

  public SmTextOperand() {
    this.text = "";
  }

  public SmTextOperand(String text) {
    this.text = text;
  }

  @Override
  public SmOperandType getOperandType() {
    return SmOperandType.TEXT;
  }

  @Override
  public void setValue(Object val) {
    this.text = String.valueOf(val);
  }

  @Override
  public Object getValue() {
    return text;
  }

  @Override
  public void fromString(String value) {
    text = value;
  }

  @Override
  public String toAssembly() {
    return text;
  }

  @Override
  public String toString() {
    return "SmTextOperand{" +
      "text=" + text +
      "}";
  }
}
