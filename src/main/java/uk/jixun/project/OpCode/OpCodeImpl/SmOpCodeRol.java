package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Helper.LogicalRotate;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRolAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRol extends SmOpCodeRolAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int shift = stack.pop();
    int value = stack.pop();

    stack.push(LogicalRotate.rotateLeft(value, shift));
  }
}
