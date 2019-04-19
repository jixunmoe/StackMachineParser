package uk.jixun.project.OpCode;

import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public interface IExecutable {
  /**
   * How many items in the stack does it consume (number of operands, destructive).
   *
   * @return Number of items to consume from stack.
   */
  int getConsume();

  /**
   * How many items will be pushed into the stack (number of results)?
   *
   * @return Number of items to produce after execution.
   */
  int getProduce();

  boolean readRam();
  boolean writeRam();
  boolean isStaticRamAddress();

  int resolveRamAddress(IExecutionContext ctx) throws Exception;

  // If this opcode indicates a branch
  boolean isBranch();

  boolean isWriteFlag();
  boolean isReadFlag();

  SmRegister getRegisterAccess();
  SmRegisterStatusEnum getRegisterStatus();

  /**
   * Executes instruction on given stack.
   *
   * @param stack Stack to manipulate.
   *              Last {@link ISmOpCode#getProduce()} values will be treated as output, and
   *              the first {@link ISmOpCode#getConsume()} items are located from the front of the stack.
   * @param ctx   Execution context, if memory access or context is required, this variable will be handy.
   * @throws Exception Exception might be thrown when evaluating instruction.
   *                   Any exception should be treated as a system panic.
   */
  void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception;

  boolean usesAlu();

  int getCycleTime();
}
