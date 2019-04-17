package uk.jixun.project.Helper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParseHelperTest {
  @ParameterizedTest(name = "{0} should be parsed as {1}")
  @CsvSource({
    "0b00110011, 51",
    "0x123, 291",
    "0101b, 5",
    "1234567d, 1234567",
    "4553207o, 1234567",
    "321h, 801",
  })
  void parseLong_HappyPath(String str, long value) {
    assertEquals(ParseHelper.parseLong(str), value,
      () -> str + " should be parsed as " + value);
  }

  @ParameterizedTest(name = "{0} should throw NumberFormatException upon parsing")
  @CsvSource({
    "0b012",
    "342391o",
    "0x00fg",
    "0102b",
    "XII",
    "五",
    "12345678o",
    "90hh",
    "0b010b",
  })
  void parseLong_SadPath(String str) {
    assertThrows(NumberFormatException.class, () -> {
      ParseHelper.parseLong(str);
    });
  }
}
