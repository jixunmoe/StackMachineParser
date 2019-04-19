package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePopRegisterIncAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePopRegisterInc extends SmOpCodePopRegisterIncAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    int reg = ctx.getRegister(getRegisterVariant()).getAndIncrement();
    ctx.write(reg, stack.pop());
  }
}
