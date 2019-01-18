package uk.jixun.project.OpCode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import uk.jixun.project.Exceptions.UnknownOpCodeException;
import uk.jixun.project.Register.SmRegister;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SmOpcodeParserTest {
  @ParameterizedTest(name = "should parse {0} correctly => {1}-{2}-{3}")
  @CsvSource({
    "ADD, ADD, 0, NONE",
    "@[XP++], POP_REGISTER_INC, 0, XP",
    "COPY3, COPY, 3, NONE",
    "COPY4, COPY, 4, NONE",
  })
  void parse_happyPath(String str, SmOpCodeEnum opcodeEnum, int variant, SmRegister reg) {
    ISmOpCode opcode = SmOpcodeParser.parse(str);
    assertEquals(opcodeEnum, opcode.getOpCodeId());
    assertEquals(variant, opcode.getVariant());
    assertEquals(reg, opcode.getRegisterVariant());
  }

  @ParameterizedTest(name = "should now allow parse of {0}")
  @ValueSource(strings = {
    "ADD3",
    "COPY7",
    "![FP++]"
  })
  void parse_sadPath(String str) {
    assertThrows(UnknownOpCodeException.class, () -> {
      SmOpcodeParser.parse(str);
    });
  }
}
