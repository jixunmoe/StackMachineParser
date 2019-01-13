package uk.jixun.project.Helper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class ParseHelper {
  private static ImmutableMap<String, Integer> prefixRadix = ImmutableMap.of(
    "0x", 16,
    "0b", 2
  );

  private static ImmutableMap<String, Integer> suffixRadix = ImmutableMap.of(
    "h", 16,
    "d", 10,
    "o", 8,
    "b", 2
  );

  /**
   * Parsing an integer(long) from given string with prefix.
   * @param value String representation
   * @return Parsed long
   * @throws NumberFormatException When string is mal-formatted.
   */
  public static long parseLong(String value) throws NumberFormatException {
    String v = value.trim();
    if (v.length() < 1) return 0;

    int radix = 10;
    boolean baseRead = false;

    if (v.length() > 2) {
      // Check for prefix
      String prefix = v.substring(0, 2);
      if (prefixRadix.containsKey(prefix)) {
        radix = prefixRadix.get(prefix);

        // Remove prefix
        v = v.substring(2);
        baseRead = true;
      }
    }

    if (!baseRead && v.length() > 1) {
      String suffix = v.substring(v.length() - 1);
      if (suffixRadix.containsKey(suffix)) {
        radix = suffixRadix.get(suffix);

        // Remove suffix
        v = v.substring(0, v.length() - 1);
        baseRead = true;
      }
    }

    // If any other invalid character present in this string, it will throw "NumberFormatException".
    return Long.valueOf(v, radix);
  }

  public static boolean isWhiteSpace(char c) {
    return c == ' ' || c == '\t';
  }

  public static boolean isNewLine(char c) {
    return c == '\r' || c == '\n';
  }
}
