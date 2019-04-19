package uk.jixun.project.OpCode;

public enum SmRegisterStatusEnum {
  /**
   * This instruction does not read or write register.
   */
  NONE,

  /**
   * Multiple read can occur at the same time.
   */
  READ,

  WRITE,
}
