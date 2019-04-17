package uk.jixun.project.Instruction;

import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Program.ISmProgram;

public class MockInstruction extends AbstractBasicInstruction {
  private boolean notForExecuteValue = false;
  private boolean usesAluValue = true;
  private int cycleTime = 1;
  private ISmProgram program;

  public static MockInstruction createFromOpCode(ISmOpCode opcode) {
    MockInstruction result = new MockInstruction();
    result.setOpcode(opcode);
    return result;
  }

  @Override
  public int getCycleTime() {
    return cycleTime;
  }

  public void setCycleTime(int cycleTime) {
    this.cycleTime = cycleTime;
  }

  public void setNotForExecute(boolean notForExecuteValue) {
    this.notForExecuteValue = notForExecuteValue;
  }

  public void setUsesAlu(boolean usesAluValue) {
    this.usesAluValue = usesAluValue;
  }

  @Override
  public boolean notForExecute() {
    return notForExecuteValue;
  }

  @Override
  public boolean usesAlu() {
    return usesAluValue;
  }

  @Override
  public boolean depends(ISmInstruction inst) {
    return false;
  }

  public ISmProgram getProgram() {
    return program;
  }

  public void setProgram(ISmProgram program) {
    this.program = program;
  }
}
