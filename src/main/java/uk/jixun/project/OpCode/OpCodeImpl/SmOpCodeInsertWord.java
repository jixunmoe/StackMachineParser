package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeInsertWordAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeInsertWord extends SmOpCodeInsertWordAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int position = getVariant();
    int toInsert = stack.pop() & 0xFFFF;
    int value = stack.pop();
    int shift = (16 * (position) - 1);

    int mask = ~(0xFFFF << shift);
    stack.push((value & mask) | (toInsert << shift));
  }
}
