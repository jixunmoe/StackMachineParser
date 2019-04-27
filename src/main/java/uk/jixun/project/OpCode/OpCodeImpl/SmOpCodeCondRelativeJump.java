package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeCondRelativeJumpAbstract;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeCondRelativeJump extends SmOpCodeCondRelativeJumpAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    if (!ctx.getJumpFlag()) {
      // Do nothing when jump flag is not set.
      return;
    }

    try {
      ctx.setEip(getInstruction().getOperand(0).resolve(ctx));
    } catch (OutOfRangeOperand outOfRangeOperand) {
      outOfRangeOperand.printStackTrace();
    }
  }
}
