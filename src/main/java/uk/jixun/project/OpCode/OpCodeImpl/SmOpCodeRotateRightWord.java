package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Helper.LogicalRotate;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRotateRightWordAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRotateRightWord extends SmOpCodeRotateRightWordAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int value = stack.pop();

    stack.push(LogicalRotate.rotateRightShort(value));
  }
}
