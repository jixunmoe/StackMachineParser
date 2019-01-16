package uk.jixun.project.Operand;

import uk.jixun.project.Exceptions.LabelNotFoundException;

public class SmLabelOperand extends SmBasicOperandAbstract {
  private String label = "";

  @Override
  public SmOperandType getOperandType() {
    return SmOperandType.LABEL;
  }

  @Override
  public void setValue(Object val) {
    throw new RuntimeException("Do not use setValue/getValue on SmLabelOperand.");
  }

  @Override
  public Object getValue() {
    throw new RuntimeException("Do not use setValue/getValue on SmLabelOperand.");
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public long resolveAddress() throws LabelNotFoundException {
    return this.getInstruction().getProgram().resolveLabel(label);
  }
}
