package uk.jixun.project.OpCode;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Program.Simulator.IExecutionContext;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Util.FifoList;

public interface ISmOpCode {
  SmOpCodeEnum getOpCodeId();

  /**
   * Number variant.
   * e.g. copy1 and copy2 is COPY with variant of 1 and 2.
   *
   * @return Variant.
   */
  int getVariant();
  void setVariant(int variant);

  /**
   * Register variant.
   * e.g. tos++ and xp++ is increment with register variant.
   * TODO: Using any of those variant in this project?
   * @return Register variant for this instruction.
   */
  SmRegister getRegisterVariant();
  void setRegisterVariant(SmRegister regVariant);

  String toAssembly();

  ISmInstruction getInstruction();
  void setInstruction(ISmInstruction instruction);

  /**
   * How many items in the stack does it consume (number of operands, destructive).
   * @return Number of items to consume from stack.
   */
  int getConsume();

  /**
   * How many items will be pushed into the stack (number of results)?
   * @return Number of items to produce after execution.
   */
  int getProduce();

  boolean readRam();
  boolean writeRam();
  boolean isStaticRamAddress();
  int accessRamAddress() throws Exception;

  // If this opcode indicates a branch
  boolean isBranch();

  /**
   * Executes instruction on given stack.
   * @param stack Stack to manipulate.
   *              Last {@link ISmOpCode#getProduce()} values will be treated as output, and
   *              the first {@link ISmOpCode#getConsume()} items are located from the front of the stack.
   * @param ctx Execution context, if memory access or context is required, this variable will be handy.
   * @exception Exception Exception might be thrown when evaluating instruction.
   *                      Any exception should be treated as a system panic.
   */
  void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception;
}
