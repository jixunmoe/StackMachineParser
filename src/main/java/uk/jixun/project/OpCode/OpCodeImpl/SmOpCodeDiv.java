
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeDivAbstract;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeDiv extends SmOpCodeDivAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int param2 = stack.pop();
    int param1 = stack.pop();
    stack.push(param1 / param2);
  }
}
