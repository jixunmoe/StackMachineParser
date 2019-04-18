package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePushRegisterIncAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePushRegisterInc extends SmOpCodePushRegisterIncAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    // load value at register and increment register
    int reg = ctx.getRegister(getRegisterVariant()).getAndIncrement();
    stack.push(ctx.read(reg));
  }
}
