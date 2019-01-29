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

import java.util.HashMap;

public abstract class SmOpCodePushRegisterDecAbstract extends AbstractBasicOpCode {

  private static HashMap<SmRegister, Integer> mapConsume = new HashMap<>();
  private static HashMap<SmRegister, Integer> mapProduce = new HashMap<>();

  static {
    
    mapConsume.put(SmRegister.XP, 0);
    mapProduce.put(SmRegister.XP, 1);
    
    mapConsume.put(SmRegister.YP, 0);
    mapProduce.put(SmRegister.YP, 1);
    

    
  }

  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.PUSH_REGISTER_DEC;
  }

  @Override
  public int getProduce() {
    return mapProduce.getOrDefault(getRegisterVariant(), 0);
  }

  @Override
  public int getConsume() {
    return mapConsume.getOrDefault(getRegisterVariant(), 0);
  }

  @Override
  public boolean accessRam() {
    
    if (getRegisterVariant() == SmRegister.XP) {
      return true;
    }

    if (getRegisterVariant() == SmRegister.YP) {
      return true;
    }

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public String toAssembly() {
    
    if (getRegisterVariant() == SmRegister.XP) {
      return "@[XP--]";
    }

    if (getRegisterVariant() == SmRegister.YP) {
      return "@[YP--]";
    }

    throw new RuntimeException("Unsupported register variant for this opcode.");
  }

  @Override
  public void setVariant(int variant) {
    if (variant != 0) {
      throw new RuntimeException("Variant does not apply for this opcode.");
    }
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {
    if ((regVariant == SmRegister.XP) || (regVariant == SmRegister.YP)) {
      this.regVariant = regVariant;
    } else {
      throw new RuntimeException("Register variant " + regVariant.toString()
        + " is not allowed for opcode PUSH_REGISTER_DEC");
    }
  }
}
