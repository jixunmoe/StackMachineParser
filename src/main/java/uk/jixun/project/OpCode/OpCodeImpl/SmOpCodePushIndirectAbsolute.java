package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePushIndirectAbsoluteAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePushIndirectAbsolute extends SmOpCodePushIndirectAbsoluteAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx)
    throws OutOfRangeOperand {

    // pop data from stack to operand 1
    int address = getInstruction().getOperand(0).resolve(ctx);
    int value = ctx.read(address);

    stack.push(value);
  }
}
