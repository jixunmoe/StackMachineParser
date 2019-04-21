package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeCopyAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeCopy extends SmOpCodeCopyAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int index = getVariant();
    int value = stack.get(-index);
    // logger.info(" >> (copy) value = " + value);
    stack.push(value);
  }
}
