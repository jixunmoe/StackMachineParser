package uk.jixun.project.OpCode;

import uk.jixun.project.Register.SmRegister;

public class SmNoOpCode implements ISmOpCode {
  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.NONE;
  }

  @Override
  public int getVariant() {
    return 0;
  }

  @Override
  public void setVariant(int variant) {
    // Do Nothing
  }

  @Override
  public SmRegister getRegisterVariant() {
    return null;
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    // Do Nothing
  }

  @Override
  public String toAssembly() {
    return null;
  }
}
