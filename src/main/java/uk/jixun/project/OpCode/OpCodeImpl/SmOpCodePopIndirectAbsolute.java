package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePopIndirectAbsoluteAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePopIndirectAbsolute extends SmOpCodePopIndirectAbsoluteAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx)
    throws OutOfRangeOperand {

    // pop data from stack to operand 1
    int address = getInstruction().getOperand(0).resolve(ctx);
    int value = stack.pop();
    ctx.write(address, value);
  }
}
