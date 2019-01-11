package uk.jixun.project.Operand;

public class SmTextOperand implements ISmOperand {
  String text;

  public SmTextOperand() {
    this.text = "";
  }

  public SmTextOperand(String text) {
    this.text = text;
  }

  @Override
  public SmOperandType GetOperandType() {
    return SmOperandType.TEXT;
  }

  @Override
  public void SetValue(Object val) {
    this.text = String.valueOf(val);
  }

  @Override
  public Object GetValue() {
    return text;
  }

  @Override
  public void FromString(String value) {
    text = value;
  }
}
