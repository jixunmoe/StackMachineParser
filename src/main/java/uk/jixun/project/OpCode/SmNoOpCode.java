package uk.jixun.project.OpCode;

import uk.jixun.project.Register.SmRegister;

public class SmNoOpCode extends AbstractBasicOpCode {
  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.NONE;
  }

  @Override
  public void setVariant(int variant) {
    // Do Nothing
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    // Do Nothing
  }

  @Override
  public String toAssembly() {
    return "";
  }

  @Override
  public String toString() {
    return "SmNoOpCode{}";
  }
}
