package uk.jixun.project.Operand;

import uk.jixun.project.Instruction.ISmInstruction;

public interface ISmOperand {
  // Get operand type
  SmOperandType getOperandType();

  void setValue(Object val);

  Object getValue();

  void fromString(String value);

  // Convert operand to string.
  String toAssembly();

  ISmInstruction getInstruction();
  void setInstruction(ISmInstruction instruction);
}
