package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodePushFrameAddrAbstract;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodePushFrameAddr extends SmOpCodePushFrameAddrAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx)
    throws OutOfRangeOperand {

    // push [fp + n]
    int fp = ctx.getRegister(SmRegister.FP).get();
    int offset = getInstruction().getOperand(0).resolve(ctx);
    int value = ctx.read(fp + offset);

    stack.push(value);
  }

  @Override
  public String toAssembly() {
    return "PUSH  FP+";
  }

  @Override
  public boolean readRam() {
    return true;
  }

  @Override
  public boolean isStaticRamAddress() {
    return false;
  }

  @Override
  public int resolveRamAddress(IExecutionContext ctx) throws Exception {
    return ctx.getRegister(SmRegister.FP).get()
      + getInstruction().getOperand(0).resolve(ctx);
  }
}
