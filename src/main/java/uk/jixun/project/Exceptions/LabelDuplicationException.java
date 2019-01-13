package uk.jixun.project.Exceptions;

public class LabelDuplicationException extends Exception {
  public LabelDuplicationException(String label) {
    super("Duplicated label '" + label + "'");
  }

  // TODO: Override toString for a more useful error message.
}
