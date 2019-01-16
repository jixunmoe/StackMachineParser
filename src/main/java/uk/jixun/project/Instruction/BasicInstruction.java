package uk.jixun.project.Instruction;

import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Program.ISmProgram;

public class BasicInstruction extends AbstractBasicInstruction {
  @Override
  public int getCycleTime() {
    // TODO: get actual cycle time based on opcode / operand processing
    return 1;
  }
}
