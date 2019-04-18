package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRegIncAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRegInc extends SmOpCodeRegIncAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    ctx.getRegister(getRegisterVariant()).incrementAndGet();
  }
}
