
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePushRegValueAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePushRegValue extends SmOpCodePushRegValueAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    int value = ctx.getRegister(getRegisterVariant()).get();
    stack.push(value);
  }
}
