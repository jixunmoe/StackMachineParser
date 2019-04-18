package uk.jixun.project.OpCode.OpCodeImpl;

import uk.jixun.project.OpCode.OpCodeAbs.SmOpCodeRegAddAbstract;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

import java.util.logging.Logger;

public class SmOpCodeRegAdd extends SmOpCodeRegAddAbstract {
  private static final Logger logger = Logger.getLogger(SmOpCodeRegAdd.class.getName());

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    // throw new Exception("not implemented");
    logger.warning(String.format("%s is not implemented and ignored", toAssembly()));
  }
}
