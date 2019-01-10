package uk.jixun.project.Helper;

public class ParseHelper {
  /**
   * Parsing an integer(long) from given string with prefix.
   * @param value String representation
   * @return Parsed long
   * @throws IllegalArgumentException When string is mal-formatted.
   */
  public static long parseLong(String value) throws IllegalArgumentException {
    String v = value.trim();
    if (v.length() < 1) return 0;

    // TODO: Implement parsing string to long (d, h suffix; 0x, 0b prefix)

    return 0;
  }
}
