package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeTestGtAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeTestGt extends SmOpCodeTestGtAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int param2 = stack.pop();
    int param1 = stack.pop();
    logger.finest(String.format("compare %d < %d", param1, param2));
    ctx.setJumpFlag(param1 > param2);
  }

  @Override
  public String toAssembly() {
    return "TEST: IF GREATER THAN";
  }
}
