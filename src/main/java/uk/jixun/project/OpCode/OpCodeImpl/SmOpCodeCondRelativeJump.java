
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeCondRelativeJumpAbstract;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeCondRelativeJump extends SmOpCodeCondRelativeJumpAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    if (!ctx.getJumpFlag()) {
      // Do nothing when jump flag is not set.
      return;
    }

    // save current address (+1) and jump to the new address.
    int returnAddress = ctx.getEip();

    ISmOperand operand = null;
    try {
      operand = getInstruction().getOperand(0);
    } catch (OutOfRangeOperand outOfRangeOperand) {
      outOfRangeOperand.printStackTrace();
    }
    assert operand != null;
    // return address - sizeof(instruction size)
    // instruction size = 1 (for this model)
    int functionAddress = (returnAddress - 1) + operand.resolve(ctx);
    ctx.setEip(functionAddress);
  }
}
