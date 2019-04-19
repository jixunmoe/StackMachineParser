package uk.jixun.project.OpCode.SysCall;

import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class AllocateSysCall extends AbstractSysCall {
  @Override
  public int getConsume() {
    return 1;
  }

  @Override
  public int getProduce() {
    return 1;
  }

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    int size = stack.pop();
    int address = ctx.getRegister(SmRegister.ALLOC_REGISTER).getAndAdd(size);
    stack.push(address);
  }
}
