package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePopRegisterAbstract;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePopRegister extends SmOpCodePopRegisterAbstract {
  // TODO: Override any opcode specific methods here.

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    SmRegister reg = getRegisterVariant();

    if (reg == SmRegister.NOS) {
      int addr = stack.pop();
      int data = stack.pop();
      ctx.write(addr, data);
    } else if (reg == SmRegister.TOS) {
      int data = stack.pop();
      int addr = stack.pop();
      ctx.write(addr, data);
    } else {
      ctx.getRegister(reg).set(stack.pop());
    }
  }

  @Override
  public String toAssembly() {
    if (getRegisterVariant() == SmRegister.NOS) {
      return "STORE";
    }

    return super.toAssembly();
  }
}
