package uk.jixun.project.Operand;

public interface ISmOperand {
  // Get operand type
  SmOperandType GetOperandType();

  void SetValue(Object val);

  Object GetValue();

  void FromString(String value);

  // Convert operand to string.
  String toString();
}
