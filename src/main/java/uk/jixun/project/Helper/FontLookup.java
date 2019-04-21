package uk.jixun.project.Helper;

import java.awt.*;
import java.util.Arrays;

public class FontLookup {
  public static String lookup(String defaultFont, String[] names) {
    if (names.length == 0) {
      return defaultFont;
      // return "Courier New";
    }

    GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
    return Arrays
      .stream(g.getAvailableFontFamilyNames())
      .filter(font -> Arrays.asList(names).contains(font))
      .findFirst().orElse(defaultFont);
  }
}
