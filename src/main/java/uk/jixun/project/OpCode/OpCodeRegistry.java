package uk.jixun.project.OpCode;



// !!!                               !!!
// !!!             STOP              !!!
// !!!                               !!!
//     DO NOT EDIT THIS FILE BY HAND    


// This file was generated using an automated script.
// See 'scripts' directory for more information



import uk.jixun.project.OpCode.OpCodeImpl.*;

class OpCodeRegistry {
  static void registerAll() {
    
    OpCodeFactory.register(SmOpCodeEnum.ADD, SmOpCodeAdd.class);
    OpCodeFactory.register(SmOpCodeEnum.SUB, SmOpCodeSub.class);
    OpCodeFactory.register(SmOpCodeEnum.HMUL, SmOpCodeHmul.class);
    OpCodeFactory.register(SmOpCodeEnum.HDIV, SmOpCodeHdiv.class);
    OpCodeFactory.register(SmOpCodeEnum.MUL, SmOpCodeMul.class);
    OpCodeFactory.register(SmOpCodeEnum.DIV, SmOpCodeDiv.class);
    OpCodeFactory.register(SmOpCodeEnum.AND, SmOpCodeAnd.class);
    OpCodeFactory.register(SmOpCodeEnum.OR, SmOpCodeOr.class);
    OpCodeFactory.register(SmOpCodeEnum.NOT, SmOpCodeNot.class);
    OpCodeFactory.register(SmOpCodeEnum.XOR, SmOpCodeXor.class);
    OpCodeFactory.register(SmOpCodeEnum.LSL, SmOpCodeLsl.class);
    OpCodeFactory.register(SmOpCodeEnum.LSR, SmOpCodeLsr.class);
    OpCodeFactory.register(SmOpCodeEnum.ROL, SmOpCodeRol.class);
    OpCodeFactory.register(SmOpCodeEnum.ROR, SmOpCodeRor.class);
    OpCodeFactory.register(SmOpCodeEnum.ROTATE_RIGHT_BYTE, SmOpCodeRotateRightByte.class);
    OpCodeFactory.register(SmOpCodeEnum.ROTATE_RIGHT_WORD, SmOpCodeRotateRightWord.class);
    OpCodeFactory.register(SmOpCodeEnum.EXTRACT_RIGHT_BYTE, SmOpCodeExtractRightByte.class);
    OpCodeFactory.register(SmOpCodeEnum.EXTRACT_RIGHT_WORD, SmOpCodeExtractRightWord.class);
    OpCodeFactory.register(SmOpCodeEnum.INSERT_BYTE, SmOpCodeInsertByte.class);
    OpCodeFactory.register(SmOpCodeEnum.INSERT_WORD, SmOpCodeInsertWord.class);
    OpCodeFactory.register(SmOpCodeEnum.DUP, SmOpCodeDup.class);
    OpCodeFactory.register(SmOpCodeEnum.COPY, SmOpCodeCopy.class);
    OpCodeFactory.register(SmOpCodeEnum.DROP, SmOpCodeDrop.class);
    OpCodeFactory.register(SmOpCodeEnum.RSU, SmOpCodeRsu.class);
    OpCodeFactory.register(SmOpCodeEnum.RSD, SmOpCodeRsd.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_TO_RS, SmOpCodePushToRs.class);
    OpCodeFactory.register(SmOpCodeEnum.POP_FROM_RS, SmOpCodePopFromRs.class);
    OpCodeFactory.register(SmOpCodeEnum.TEST_EQ, SmOpCodeTestEq.class);
    OpCodeFactory.register(SmOpCodeEnum.TEST_NEQ, SmOpCodeTestNeq.class);
    OpCodeFactory.register(SmOpCodeEnum.TEST_GT, SmOpCodeTestGt.class);
    OpCodeFactory.register(SmOpCodeEnum.TEST_LT, SmOpCodeTestLt.class);
    OpCodeFactory.register(SmOpCodeEnum.TEST_NEGATIVE, SmOpCodeTestNegative.class);
    OpCodeFactory.register(SmOpCodeEnum.TEST_POSITIVE, SmOpCodeTestPositive.class);
    OpCodeFactory.register(SmOpCodeEnum.TEST_NOT_ZERO, SmOpCodeTestNotZero.class);
    OpCodeFactory.register(SmOpCodeEnum.TEST_XP_ZERO, SmOpCodeTestXpZero.class);
    OpCodeFactory.register(SmOpCodeEnum.TEST_YP_ZERO, SmOpCodeTestYpZero.class);
    OpCodeFactory.register(SmOpCodeEnum.RELATIVE_JUMP, SmOpCodeRelativeJump.class);
    OpCodeFactory.register(SmOpCodeEnum.ABSOLUTE_JUMP, SmOpCodeAbsoluteJump.class);
    OpCodeFactory.register(SmOpCodeEnum.RELATIVE_CALL, SmOpCodeRelativeCall.class);
    OpCodeFactory.register(SmOpCodeEnum.ABSOLUTE_CALL, SmOpCodeAbsoluteCall.class);
    OpCodeFactory.register(SmOpCodeEnum.RETURN, SmOpCodeReturn.class);
    OpCodeFactory.register(SmOpCodeEnum.COND_RELATIVE_JUMP, SmOpCodeCondRelativeJump.class);
    OpCodeFactory.register(SmOpCodeEnum.COND_PAGE_JUMP, SmOpCodeCondPageJump.class);
    OpCodeFactory.register(SmOpCodeEnum.COND_ABSOLUTE_JUMP, SmOpCodeCondAbsoluteJump.class);
    OpCodeFactory.register(SmOpCodeEnum.COND_RELATIVE_CALL, SmOpCodeCondRelativeCall.class);
    OpCodeFactory.register(SmOpCodeEnum.COND_ABSOLUTE_CALL, SmOpCodeCondAbsoluteCall.class);
    OpCodeFactory.register(SmOpCodeEnum.COND_RETURN, SmOpCodeCondReturn.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_BYTE, SmOpCodePushByte.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_SHORT, SmOpCodePushShort.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_EXTEND, SmOpCodePushExtend.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_FRAME_ADDR, SmOpCodePushFrameAddr.class);
    OpCodeFactory.register(SmOpCodeEnum.POP_FRAME_ADDR, SmOpCodePopFrameAddr.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_INDIRECT_RELATIVE, SmOpCodePushIndirectRelative.class);
    OpCodeFactory.register(SmOpCodeEnum.POP_INDIRECT_RELATIVE, SmOpCodePopIndirectRelative.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_INDIRECT_ABSOLUTE, SmOpCodePushIndirectAbsolute.class);
    OpCodeFactory.register(SmOpCodeEnum.POP_INDIRECT_ABSOLUTE, SmOpCodePopIndirectAbsolute.class);
    OpCodeFactory.register(SmOpCodeEnum.SAVE_TO_REG, SmOpCodeSaveToReg.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_REG_VALUE, SmOpCodePushRegValue.class);
    OpCodeFactory.register(SmOpCodeEnum.POP_REGISTER, SmOpCodePopRegister.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_REGISTER, SmOpCodePushRegister.class);
    OpCodeFactory.register(SmOpCodeEnum.POP_REGISTER_INC, SmOpCodePopRegisterInc.class);
    OpCodeFactory.register(SmOpCodeEnum.POP_REGISTER_DEC, SmOpCodePopRegisterDec.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_REGISTER_INC, SmOpCodePushRegisterInc.class);
    OpCodeFactory.register(SmOpCodeEnum.PUSH_REGISTER_DEC, SmOpCodePushRegisterDec.class);
    OpCodeFactory.register(SmOpCodeEnum.REG_ADD, SmOpCodeRegAdd.class);
    OpCodeFactory.register(SmOpCodeEnum.REG_SUB, SmOpCodeRegSub.class);
    OpCodeFactory.register(SmOpCodeEnum.REG_INC, SmOpCodeRegInc.class);
    OpCodeFactory.register(SmOpCodeEnum.REG_DEC, SmOpCodeRegDec.class);
  }
}
