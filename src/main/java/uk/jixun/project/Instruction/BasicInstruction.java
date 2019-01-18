package uk.jixun.project.Instruction;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Program.ISmProgram;

public class BasicInstruction extends AbstractBasicInstruction {
  @Override
  public int getCycleTime() {
    // TODO: get actual cycle time based on opcode / operand processing
    return 1;
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
