
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeAbsoluteJumpAbstract;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeAbsoluteJump extends SmOpCodeAbsoluteJumpAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    try {
      ctx.setEip(getInstruction().getOperand(0).resolve(ctx));
    } catch (OutOfRangeOperand outOfRangeOperand) {
      outOfRangeOperand.printStackTrace();
    }
  }
}
