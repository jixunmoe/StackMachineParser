package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeMulAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeMul extends SmOpCodeMulAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int param2 = stack.pop();
    int param1 = stack.pop();
    stack.push(param1 * param2);
  }
}
