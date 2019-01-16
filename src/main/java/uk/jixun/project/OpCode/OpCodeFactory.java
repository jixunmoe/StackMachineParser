package uk.jixun.project.OpCode;

import uk.jixun.project.Exceptions.UnknownOpCodeException;
import uk.jixun.project.Register.SmRegister;

class OpCodeFactory {

  static ISmOpCode create(SmOpCodeEnum opcode) {
    return create(opcode, 0, SmRegister.NONE);
  }

  static ISmOpCode create(SmOpCodeEnum opcode, int variant) {
    return create(opcode, variant, SmRegister.NONE);
  }

  static ISmOpCode create(SmOpCodeEnum opcode, SmRegister regVariant) {
    return create(opcode, 0, regVariant);
  }

  private static ISmOpCode create(SmOpCodeEnum opcode, int variant, SmRegister regVariant) {
    if (regVariant != SmRegister.NONE && variant != 0) {
      throw new UnknownOpCodeException(opcode.toString(), variant);
    }

    // TODO: Create the actual opcode object here.
    return null;
  }
}
