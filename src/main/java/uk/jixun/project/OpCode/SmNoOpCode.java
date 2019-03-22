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
  public int getConsume() {
    return 0;
  }

  @Override
  public int getProduce() {
    return 0;
  }

  @Override
  public boolean readRam() {
    return false;
  }

  @Override
  public boolean writeRam() {
    return false;
  }

  @Override
  public boolean isStaticRamAddress() {
    return false;
  }

  @Override
  public int accessRamAddress() {
    return 0;
  }

  @Override
  public boolean isBranch() {
    return false;
  }

  @Override
  public String toString() {
    return "SmNoOpCode{}";
  }
}
