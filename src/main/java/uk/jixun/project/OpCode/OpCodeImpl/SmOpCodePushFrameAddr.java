package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePushFrameAddrAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePushFrameAddr extends SmOpCodePushFrameAddrAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx)
    throws OutOfRangeOperand {

    // push [fp + n]
    int fp = ctx.getRegister(getRegisterVariant()).get();
    int offset = getInstruction().getOperand(0).resolve(ctx);
    int value = ctx.read(fp + offset);

    stack.push(value);
  }
}
