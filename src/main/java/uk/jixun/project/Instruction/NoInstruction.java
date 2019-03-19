package uk.jixun.project.Instruction;

import com.google.common.collect.ImmutableList;
import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Operand.ISmOperand;

import java.util.List;

/**
 * Empty line for padding.
 */
public class NoInstruction extends AbstractBasicInstruction {
  private List<ISmOperand> operands;

  public NoInstruction() {
    this.operands = ImmutableList.of();
  }

  @Override
  public ISmOpCode getOpCode() {
    return null;
  }

  @Override
  protected List<ISmOperand> getOperands() {
    return operands;
  }

  @Override
  public int getCycleTime() {
    return 0;
  }

  @Override
  public String toAssembly() {
    return "";
  }

  @Override
  public boolean notForExecute() {
    return true;
  }
}
