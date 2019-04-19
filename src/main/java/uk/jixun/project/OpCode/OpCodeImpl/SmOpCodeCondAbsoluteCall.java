package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeCondAbsoluteCallAbstract;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeCondAbsoluteCall extends SmOpCodeCondAbsoluteCallAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    if (!ctx.getJumpFlag()) {
      // Do nothing when jump flag is not set.
      return;
    }

    // save current address (+1) and jump to the new address.
    int returnAddress = ctx.getEip();
    stack.push(returnAddress);

    ISmOperand operand = null;
    try {
      operand = getInstruction().getOperand(0);
    } catch (OutOfRangeOperand outOfRangeOperand) {
      outOfRangeOperand.printStackTrace();
    }
    assert operand != null;
    int functionAddress = operand.resolve(ctx);
    ctx.setEip(functionAddress);
  }

  @Override
  public String toAssembly() {
    return "CALL?";
  }
}
