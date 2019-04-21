package uk.jixun.project.Instruction;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Program.ISmProgram;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBasicInstruction implements ISmInstruction {
  private long line = 0;
  private int virtualAddress = 0;
  private int eip = 0;
  private ISmOpCode opcode;
  private ISmProgram program;
  private List<ISmOperand> operands = new ArrayList<>();

  protected List<ISmOperand> getOperands() {
    return operands;
  }

  @Override
  public void setOperands(List<ISmOperand> operands) {
    this.operands = operands;
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
    return getOpCode().usesAlu();
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
  public int getVirtualAddress() {
    return virtualAddress;
  }

  @Override
  public void setVirtualAddress(int virtualAddress) {
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

  private String toAssembly(String opcode, int prefix, boolean line) {
    StringBuilder result = new StringBuilder();
    if (line) {
      result.append(String.format("%0" + prefix + "d: l %0" + prefix + "d: ", getVirtualAddress(), getLine()));
    }
    result.append(opcode);

    for (int i = 0; i < getOperandCount(); i++) {
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
  public String toAssembly() {
    return toAssembly(getOpCode().toAssembly(), 0, false);
  }

  @Override
  public String getStackAssembly() {
    if (getOpCode() == null || getOpCode().getOriginalText() == null) {
      assert false;
    }
    return toAssembly(getOpCode().getOriginalText(), 3, true);
  }

  public String toAssemblyWithAddress(int prefix) {
    return toAssembly(getOpCode().toAssembly(), 3, true);
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
  public boolean isMetaInst() {
    return false;
  }

  @Override
  public int getEip() {
    return eip;
  }

  @Override
  public void setEip(int eip) {
    if (eip >= 0) {
      this.eip = eip;
    }
  }

  @Override
  public boolean depends(ISmInstruction inst) {
    return false;
  }
}
