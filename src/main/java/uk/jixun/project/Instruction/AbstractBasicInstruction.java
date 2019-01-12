package uk.jixun.project.Instruction;

import org.apache.commons.lang3.NotImplementedException;
import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Operand.ISmOperand;

import java.util.List;

public abstract class AbstractBasicInstruction implements ISmInstruction {
  protected abstract List<ISmOperand> getOperands();

  @Override
  public ISmOperand getOperand(int index) throws OutOfRangeOperand {
    return getOperands().get(index);
  }

  @Override
  public int getOperandCount() {
    return getOperands().size();
  }

  @Override
  public String toAssembly() {
    // TODO
    throw new NotImplementedException("TODO: Decode opcode and operands.");
  }
}
