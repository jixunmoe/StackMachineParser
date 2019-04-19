package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeTestNotZeroAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeTestNotZero extends SmOpCodeTestNotZeroAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int value = stack.pop();
    ctx.setJumpFlag(value != 0);
  }

  @Override
  public String toAssembly() {
    return "TEST: IF NOT ZERO";
  }
}
