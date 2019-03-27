
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeExtractRightByteAbstract;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeExtractRightByte extends SmOpCodeExtractRightByteAbstract {
  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) {
    int value = stack.pop();
    stack.push(value & 0xFF);
  }
}
