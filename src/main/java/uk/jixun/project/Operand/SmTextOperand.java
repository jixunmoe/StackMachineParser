package uk.jixun.project.Operand;

import uk.jixun.project.Program.Simulator.IExecutionContext;

public class SmTextOperand extends SmBasicOperandAbstract {
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
    setValue(value);
  }

  @Override
  public String toAssembly() {
    return "\"" + text + "\"";
  }

  @Override
  public int resolve(IExecutionContext ctx) {
    // Nothing to resolve
    return 0;
  }

  @Override
  public String toString() {
    return "SmTextOperand{" +
      "text=" + text +
      "}";
  }
}
