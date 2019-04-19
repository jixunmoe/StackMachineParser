package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRsdAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRsd extends SmOpCodeRsdAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    // local is reversed of stack.
    // orig  rsu4  rsd4
    // 4th   3rd   1st
    // 3rd   2nd   4th
    // 2nd   1st   3rd
    // 1st   4th   2nd

    // orig  rsu3  rsd3
    // 3rd   2nd   1st
    // 2nd   1st   3rd
    // 1nd   3rd   2nd

    // rsd: pop last item and push it to beginning of the list.

    int index = getVariant();
    int value = stack.pop();

    // new index. -1 because the item already removed.
    index = -(index - 1);
    stack.insertBefore(value, index);
  }
}
