package uk.jixun.project.OpCode.SysCall;

import uk.jixun.project.Simulator.IExecutionContext;
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
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    ctx.halt();
  }
}
