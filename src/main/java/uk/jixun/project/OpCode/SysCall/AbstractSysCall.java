package uk.jixun.project.OpCode.SysCall;

import uk.jixun.project.Simulator.IExecutionContext;

public abstract class AbstractSysCall implements ISysCall {
  @Override
  public int resolveRamAddress(IExecutionContext ctx) throws Exception {
    if (readRam() || writeRam())
      throw new Exception("`resolveRamAddress` Method not implemented.");

    return 0;
  }

  @Override
  public boolean isStaticRamAddress() {
    return false;
  }

  @Override
  public boolean usesAlu() {
    // FIXME: Assume all instructions uses ALU.
    return true;
  }

  @Override
  public String toString() {
    return "<unknown syscall>";
  }

  @Override
  public int getCycleTime() {
    // FIXME: Assume all sys call uses 1 cycle to complete.
    return 1;
  }
}
