package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeTestXpZeroAbstract;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeTestXpZero extends SmOpCodeTestXpZeroAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    ctx.setJumpFlag(ctx.getRegister(SmRegister.XP).get() == 0);
  }

  @Override
  public String toAssembly() {
    return "TEST: IF XP IS ZERO";
  }
}
