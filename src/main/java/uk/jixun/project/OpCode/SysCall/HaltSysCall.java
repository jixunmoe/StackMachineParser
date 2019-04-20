package uk.jixun.project.OpCode.SysCall;

import uk.jixun.project.OpCode.SmRegStatus;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class HaltSysCall extends AbstractSysCall {
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
  public SmRegister getRegisterAccess() {
    return null;
  }

  @Override
  public SmRegStatus getRegisterStatus() {
    return null;
  }

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    ctx.halt();
  }

  @Override
  public String toString() {
    return "<SYS:HALT>";
  }
}
