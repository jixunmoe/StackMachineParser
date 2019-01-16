package uk.jixun.project.OpCode.OpCodeAbs;



// !!!                               !!!
// !!!             STOP              !!!
// !!!                               !!!
//     DO NOT EDIT THIS FILE BY HAND    


// This file was generated using an automated script.
// See 'scripts' directory for more information



import uk.jixun.project.OpCode.AbstractBasicOpCode;
import uk.jixun.project.OpCode.SmOpCodeEnum;
import uk.jixun.project.Register.SmRegister;

public abstract class SmOpCodeAbsoluteCallAbstract extends AbstractBasicOpCode {
  @Override
  public SmOpCodeEnum getOpCode() {
    return SmOpCodeEnum.ABSOLUTE_CALL;
  }

  @Override
  public void setVariant(int variant) {
    throw new RuntimeException("Variant does not apply for this opcode.");
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    throw new RuntimeException("RegisterVariant does not apply for this opcode.");
  }
}