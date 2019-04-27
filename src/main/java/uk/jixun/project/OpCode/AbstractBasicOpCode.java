package uk.jixun.project.OpCode;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Register.SmRegister;

import java.util.logging.Logger;

public abstract class AbstractBasicOpCode implements ISmOpCode {
  protected static Logger logger = Logger.getLogger(AbstractBasicOpCode.class.getName());
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

  @Override
  public SmRegister getRegisterAccess() {
    return getRegisterVariant();
  }

  @Override
  public SmRegStatus getRegisterStatus() {
    assert getRegisterVariant() == SmRegister.NONE;
    return SmRegStatus.NONE;
  }

  @Override
  public boolean usesAlu() {
    // FIXME: Assume all instructions uses ALU.
    return true;
  }

  @Override
  public String toString() {
    if (getInstruction() == null) {
      return "<NONE>";
    }

    return getInstruction().toAssemblyWithAddress(3);
  }

  private static final int RAM_DELAY = 1;

  @Override
  public int getCycleTime() {
    int time = 0;

    if (usesAlu()) {
      time += 2;
    }

    if (readRam() || writeRam()) {
      time += RAM_DELAY;
    }

    time += RAM_DELAY * (getProduce() + getConsume());

    return time;
  }

  private String originalText = null;

  @Override
  public String getOriginalText() {
    if (originalText == null) {
      logger.warning(String.format("%s did not provide original text, use re-constructed text.", toAssembly()));
      return toAssembly();
    }
    return originalText;
  }

  @Override
  public void setOriginalText(String originalText) {
    this.originalText = originalText;
  }

  @Override
  public String getOriginalLine() {
    return getInstruction().getStackAssembly();
  }
}
