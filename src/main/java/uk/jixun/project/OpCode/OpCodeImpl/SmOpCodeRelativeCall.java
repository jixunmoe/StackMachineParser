package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRelativeCallAbstract;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRelativeCall extends SmOpCodeRelativeCallAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    // Same as SmOpCodeAbsoluteCall

    // save current address + 1 and jump to the new address.
    int returnAddress = getInstruction().getVirtualAddress() + 1;
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
}
