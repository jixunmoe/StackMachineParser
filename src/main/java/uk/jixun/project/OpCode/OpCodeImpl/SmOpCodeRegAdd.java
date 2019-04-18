package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRegAddAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

import java.util.logging.Logger;

public class SmOpCodeRegAdd extends SmOpCodeRegAddAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    ctx.getRegister(this.getRegisterVariant()).addAndGet(stack.pop());
  }
}
