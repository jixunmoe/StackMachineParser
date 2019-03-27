
package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeTestNeqAbstract;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class SmOpCodeTestNeq extends SmOpCodeTestNeqAbstract {
  // TODO: Override any opcode specific methods here.

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    throw new Exception("not implemented");
  }
}
