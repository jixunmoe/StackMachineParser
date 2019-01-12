package uk.jixun.project.OpCode;

import uk.jixun.project.Register.SmRegister;

public abstract class AbstractBasicOpCode implements ISmOpCode {
  @Override
  public int getVariant() {
    return 0;
  }

  @Override
  public SmRegister getRegisterVariant() {
    return SmRegister.NONE;
  }

  @Override
  public String toAssembly() {
    // TODO: Test this function
    StringBuilder sb = new StringBuilder();

    SmRegister registerVariant = getRegisterVariant();
    if (registerVariant != SmRegister.NONE) {
      sb.append(registerVariant.toString());
    }

    sb.append(this.getOpCode());

    int variant = getVariant();
    if (variant != 0) {
      sb.append(variant);
    }

    return this.getOpCode().toString();
  }
}
