package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeTestPositiveAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeTestPositive extends SmOpCodeTestPositiveAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    // UTSA Instruction set defined positive to be >= 0.

    int value = stack.pop();
    ctx.setJumpFlag(value >= 0);
  }

  @Override
  public String toAssembly() {
    return "TEST: IF POSITIVE (>= 0)";
  }
}
