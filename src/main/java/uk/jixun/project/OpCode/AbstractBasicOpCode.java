package uk.jixun.project.OpCode;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Register.SmRegister;

public abstract class AbstractBasicOpCode implements ISmOpCode {
  protected int variant = 0;
  protected SmRegister regVariant = SmRegister.NONE;
  protected ISmInstruction instruction;

  @Override
  public int getVariant() {
    return variant;
  }

  @Override
  public SmRegister getRegisterVariant() {
    return regVariant;
  }

  @Override
  public String toAssembly() {
    if (getRegisterVariant() != SmRegister.NONE) {
      throw new RuntimeException("Need to override toAssembly for this opcode.");
    }

    StringBuilder sb = new StringBuilder();
    sb.append(this.getOpCodeId().toString());

    int variant = getVariant();
    if (variant != 0) {
      sb.append(variant);
    }

    return sb.toString();
  }

  @Override
  public ISmInstruction getInstruction() {
    return instruction;
  }

  @Override
  public void setInstruction(ISmInstruction instruction) {
    this.instruction = instruction;
  }
}
