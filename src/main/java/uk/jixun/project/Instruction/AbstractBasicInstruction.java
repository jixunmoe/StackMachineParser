package uk.jixun.project.Instruction;

import org.apache.commons.lang3.NotImplementedException;
import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Program.ISmProgram;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBasicInstruction implements ISmInstruction {
  private ISmProgram program;
  private ISmOpCode opcode;
  private List<ISmOperand> operands = new ArrayList<>();

  protected List<ISmOperand> getOperands() {
    return operands;
  }

  @Override
  public ISmOpCode getOpCode() {
    return opcode;
  }

  public void setOpcode(ISmOpCode opcode) {
    this.opcode = opcode;
  }

  @Override
  public ISmProgram getProgram() {
    return program;
  }

  @Override
  public void setProgram(ISmProgram program) {
    this.program = program;
  }


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
    StringBuilder result = new StringBuilder();
    result.append(getOpCode().toAssembly());

    for(int i = 0; i < getOperandCount(); i++) {
      result.append(" ");

      try {
        result.append(getOperand(i).toAssembly());
      } catch (OutOfRangeOperand outOfRangeOperand) {
        // FIXME: This exception should never occur.
        outOfRangeOperand.printStackTrace();
      }
    }

    return result.toString();
}

  @Override
  public void setOperand(int index, ISmOperand operand) throws OutOfRangeOperand {
    if (index < getOperandCount()) {
      operands.set(index, operand);
    } else if (index == getOperandCount()) {
      operands.add(operand);
    } else {
      throw new OutOfRangeOperand(index, this);
    }
  }

  @Override
  public void setOperands(List<ISmOperand> operands) {
    this.operands = operands;
  }
}
