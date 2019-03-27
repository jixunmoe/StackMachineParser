
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePushFrameAddrAbstract;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePushFrameAddr extends SmOpCodePushFrameAddrAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx)
    throws OutOfRangeOperand {

    // pop data from stack to operand 1
    int address = getInstruction().getOperand(0).resolve(ctx);
    int value = ctx.read(address);

    stack.push(value);
  }
}
