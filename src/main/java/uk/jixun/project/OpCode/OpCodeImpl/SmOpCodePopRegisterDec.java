package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePopRegisterDecAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePopRegisterDec extends SmOpCodePopRegisterDecAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    int reg = ctx.getRegister(getRegisterVariant()).getAndDecrement();
    ctx.write(reg, stack.pop());
  }
}
