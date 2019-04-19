package uk.jixun.project.Operand;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Simulator.Context.IExecutionContext;

public interface ISmOperand {
  // Get operand type
  SmOperandType getOperandType();

  Object getValue();

  void setValue(Object val);

  void fromString(String value);

  // Convert operand to string.
  String toAssembly();

  ISmInstruction getInstruction();

  void setInstruction(ISmInstruction instruction);

  /**
   * Resolve operand to integer.
   *
   * @param ctx Execution context to resolve.
   * @return Resolved value.
   */
  int resolve(IExecutionContext ctx);
}
