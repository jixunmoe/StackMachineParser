package uk.jixun.project.Instruction;

import uk.jixun.project.Exceptions.OutOfRangeOperand;

public class BasicInstruction extends AbstractBasicInstruction {
  @Override
  public int getCycleTime() {
    return getOpCode().getCycleTime();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("BasicInstruction{");
    sb.append("opcode=").append(getOpCode().toAssembly());
    sb.append(", operand.len=").append(getOperandCount());
    for (int i = 0; i < getOperandCount(); i++) {
      try {
        sb.append(", operand[").append(i).append("]=").append(getOperand(i).toAssembly());
      } catch (OutOfRangeOperand ignored) {
      }
    }
    sb.append("}");
    return sb.toString();
  }
}
