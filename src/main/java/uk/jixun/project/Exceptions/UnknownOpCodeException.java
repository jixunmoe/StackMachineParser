package uk.jixun.project.Exceptions;

import uk.jixun.project.Register.SmRegister;

public class UnknownOpCodeException extends UnsupportedOperationException {
  public UnknownOpCodeException(String opcode) {
    super("Unsupported opcode [" + opcode + "]");
  }
  public UnknownOpCodeException(String opcode, int variant) {
    super("Unsupported opcode [" + opcode + ":" + variant + "]");
  }
  public UnknownOpCodeException(String opcode, SmRegister regVariant) {
    super("Unsupported opcode [" + opcode + ":" + regVariant.toString() + "]");
  }
}
