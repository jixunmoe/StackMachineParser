package uk.jixun.project.Instruction;

import org.apache.commons.lang3.NotImplementedException;
import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Program.ISmProgram;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBasicInstruction implements ISmInstruction {
  long line = 0;
  long virtualAddress = 0;
  private ISmOpCode opcode;
  private ISmProgram program;
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
  public boolean isBranch() {
    return getOpCode().isBranch();
  }

  @Override
  public boolean usesAlu() {
    // FIXME: Assume all instructions uses ALU.
    return true;
  }

  @Override
  public boolean readRam() {
    return getOpCode().readRam();
  }

  @Override
  public boolean writeRam() {
    return getOpCode().writeRam();
  }

  @Override
  public boolean readOrWriteRam() {
    return readRam() || writeRam();
  }

  @Override
  public long getLine() {
    return line;
  }

  @Override
  public void setLine(long line) {
    this.line = line;
  }

  @Override
  public long getVirtualAddress() {
    return virtualAddress;
  }

  @Override
  public void setVirtualAddress(long virtualAddress) {
    this.virtualAddress = virtualAddress;
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

  @Override
  public boolean notForExecute() {
    return false;
  }
}
