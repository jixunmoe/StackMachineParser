package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Helper.LogicalRotate;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRotateRightByteAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRotateRightByte extends SmOpCodeRotateRightByteAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int value = stack.pop();

    stack.push(LogicalRotate.rotateRightByte(value));
  }
}
