package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeInsertByteAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeInsertByte extends SmOpCodeInsertByteAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int position = getVariant();
    int toInsert = stack.pop() & 0xFF;
    int value = stack.pop();
    int shift = (8 * (position) - 1);

    int mask = ~(0xFF << shift);
    stack.push((value & mask) | (toInsert << shift));
  }
}
