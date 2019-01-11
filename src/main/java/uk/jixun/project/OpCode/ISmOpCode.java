package uk.jixun.project.OpCode;

import uk.jixun.project.Register.SmRegister;

public interface ISmOpCode {
  SmOpCode GetOpCode();

  /**
   * Number variant.
   * e.g. copy1 and copy2 is COPY with variant of 1 and 2.
   *
   * @return Variant.
   */
  int GetVariant();

  /**
   * Register variant.
   * e.g. tos++ and xp++ is increment with register variant.
   * TODO: Using any of those variant in this project?
   * @return Register variant for this instruction.
   */
  SmRegister GetRegisterVariant();
}
