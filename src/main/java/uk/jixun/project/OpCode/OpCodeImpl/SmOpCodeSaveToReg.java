
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeSaveToRegAbstract;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeSaveToReg extends SmOpCodeSaveToRegAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    int value = stack.pop();
    ctx.getRegister(getRegisterVariant()).set(value);
  }
}
