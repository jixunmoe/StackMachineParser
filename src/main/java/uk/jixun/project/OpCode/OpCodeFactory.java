package uk.jixun.project.OpCode;

import uk.jixun.project.Exceptions.UnknownOpCodeException;
import uk.jixun.project.Register.SmRegister;

import java.util.HashMap;

public class OpCodeFactory {
  private static boolean registered = false;
  private static HashMap<SmOpCodeEnum, Class<? extends ISmOpCode>> opcodes = new HashMap<>();

  public static void register(SmOpCodeEnum opcode, Class<? extends ISmOpCode> cls) {
    if (opcodes.containsKey(opcode)) {
      throw new IllegalArgumentException("OpCode " + opcode.toString() + " already defined.");
    }

    opcodes.put(opcode, cls);
  }

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
    if (!registered) {
      OpCodeRegistry.registerAll();
      registered = true;
    }

    if (regVariant != SmRegister.NONE && variant != 0) {
      throw new UnknownOpCodeException(opcode.toString(), variant);
    }

    if (!opcodes.containsKey(opcode)) {
      throw new UnknownOpCodeException(opcode.toString(), variant);
    }

    try {
      ISmOpCode opcodeInst = opcodes.get(opcode).getDeclaredConstructor().newInstance();
      opcodeInst.setRegisterVariant(regVariant);
      opcodeInst.setVariant(variant);
      return opcodeInst;
    } catch (Exception e) {
      // FIXME: This should not happen.
      e.printStackTrace();
      throw new RuntimeException("Should not reach here: " + e.toString());
    }
  }
}
