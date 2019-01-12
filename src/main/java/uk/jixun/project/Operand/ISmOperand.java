package uk.jixun.project.Operand;

public interface ISmOperand {
  // Get operand type
  SmOperandType getOperandType();

  void setValue(Object val);

  Object getValue();

  void fromString(String value);

  // Convert operand to string.
  String toAssembly();
}
