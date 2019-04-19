package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePushRegisterAbstract;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePushRegister extends SmOpCodePushRegisterAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    SmRegister reg = getRegisterVariant();
    if (reg == SmRegister.TOS) {
      stack.push(ctx.read(stack.pop()));
    } else {
      stack.push(ctx.read(ctx.getRegister(reg).get()));
    }
  }
}
