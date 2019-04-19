package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePushRegisterDecAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePushRegisterDec extends SmOpCodePushRegisterDecAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    // load value at register and decrement register
    int reg = ctx.getRegister(getRegisterVariant()).getAndDecrement();
    stack.push(ctx.read(reg));
  }
}
