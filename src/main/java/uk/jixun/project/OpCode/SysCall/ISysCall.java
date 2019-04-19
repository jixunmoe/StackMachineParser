package uk.jixun.project.OpCode.SysCall;

import uk.jixun.project.OpCode.IExecutable;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public interface ISysCall extends IExecutable {
  int getConsume();
  int getProduce();

  void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception;
}
