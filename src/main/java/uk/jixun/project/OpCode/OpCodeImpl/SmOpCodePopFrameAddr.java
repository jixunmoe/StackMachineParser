
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePopFrameAddrAbstract;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePopFrameAddr extends SmOpCodePopFrameAddrAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx)
    throws OutOfRangeOperand {

    // pop data from stack to operand 1
    int address = getInstruction().getOperand(0).resolve(ctx);
    int value = stack.pop();
    ctx.write(address, value);
  }
}
