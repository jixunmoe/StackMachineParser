package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeTestYpZeroAbstract;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeTestYpZero extends SmOpCodeTestYpZeroAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    ctx.setJumpFlag(ctx.getRegister(SmRegister.YP).get() == 0);
  }

  @Override
  public String toAssembly() {
    return "TEST: IF YP IS ZERO";
  }
}
