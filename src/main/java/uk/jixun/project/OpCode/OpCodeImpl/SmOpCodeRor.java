package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Helper.LogicalRotate;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRorAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRor extends SmOpCodeRorAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int shift = stack.pop();
    int value = stack.pop();

    stack.push(LogicalRotate.rotateRight(value, shift));
  }
}
