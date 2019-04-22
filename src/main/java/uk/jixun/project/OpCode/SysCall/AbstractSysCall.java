package uk.jixun.project.OpCode.SysCall;

import uk.jixun.project.Simulator.Context.IExecutionContext;

import java.util.logging.Logger;

public abstract class AbstractSysCall implements ISysCall {
  protected static Logger logger = Logger.getLogger(AbstractSysCall.class.getName());

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

  @Override
  public String getOriginalText() {
    return toString();
  }

  @Override
  public void setOriginalText(String originalText) {
    logger.fine(String.format(
      "Setting original text to sys-call '%s' of value '%s' is ignored.",
      toString(),
      originalText
    ));
  }

  @Override
  public String getOriginalLine() {
    return getOriginalText();
  }


}
