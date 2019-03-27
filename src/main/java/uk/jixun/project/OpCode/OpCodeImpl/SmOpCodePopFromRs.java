
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePopFromRsAbstract;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePopFromRs extends SmOpCodePopFromRsAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx)
    throws OutOfRangeOperand {

    // pop data from stack to operand 1
    int address = getInstruction().getOperand(0).resolve(ctx);
    int value = ctx.read(address);

    stack.push(value);
  }
}
