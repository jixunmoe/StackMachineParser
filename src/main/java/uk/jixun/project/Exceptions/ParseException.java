package uk.jixun.project.Exceptions;

public class ParseException extends IllegalArgumentException {
  public ParseException() {
  }

  public ParseException(String s) {
    super(s);
  }

  public static ParseException forInputString(String s) {
    return new ParseException("For input string: \"" + s + "\"");
  }
}
