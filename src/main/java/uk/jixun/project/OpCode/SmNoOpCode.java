package uk.jixun.project.OpCode;

import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

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
  public int resolveRamAddress(IExecutionContext ctx) {
    return 0;
  }

  @Override
  public boolean isBranch() {
    return false;
  }

  @Override
  public boolean isWriteFlag() {
    return false;
  }

  @Override
  public boolean isReadFlag() {
    return false;
  }

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {

  }

  @Override
  public String toString() {
    return "SmNoOpCode{}";
  }
}
