package uk.jixun.project.Exceptions;

public class LabelNotFoundException extends Exception {
  public LabelNotFoundException() { super(); }
  public LabelNotFoundException(String s) { super(s); }

  static public LabelNotFoundException fromLabel(String l) {
    return new LabelNotFoundException("From label '" + l + "'");
  }
}
