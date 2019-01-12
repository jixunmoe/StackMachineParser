package uk.jixun.project.OpCode;

import uk.jixun.project.Register.SmRegister;

public class SmNoOpCode implements ISmOpCode {
  @Override
  public SmOpCode getOpCode() {
    return SmOpCode.NONE;
  }

  @Override
  public int getVariant() {
    return 0;
  }

  @Override
  public SmRegister getRegisterVariant() {
    return null;
  }

  @Override
  public String toAssembly() {
    return null;
  }
}
