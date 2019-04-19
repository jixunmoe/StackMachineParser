package uk.jixun.project.OpCode;


import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public interface MockEvaluate {
  void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception;
}
