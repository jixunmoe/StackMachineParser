package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRegDecAbstract;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeRegDec extends SmOpCodeRegDecAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    SmRegister reg = getRegisterVariant();
    if (reg == SmRegister.TOS) {
      int value = stack.pop();
      value--;
      stack.push(value);
    } else {
      ctx.getRegister(getRegisterVariant()).decrementAndGet();
    }
  }
}
