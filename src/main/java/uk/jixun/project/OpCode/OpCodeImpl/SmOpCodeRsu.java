package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRsuAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRsu extends SmOpCodeRsuAbstract {
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

    // rsu: move n-th item to top of the stack.

    int index = getVariant();

    // -index should be the first item.
    int value = stack.pop(-index);
    stack.push(value);
  }
}
