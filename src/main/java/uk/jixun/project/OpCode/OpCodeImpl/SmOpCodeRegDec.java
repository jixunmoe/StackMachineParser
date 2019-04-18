package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRegDecAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRegDec extends SmOpCodeRegDecAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    ctx.getRegister(getRegisterVariant()).decrementAndGet();
  }
}
