package uk.jixun.project.Register;

public enum SmRegister {
  // Special Register indicates register was not used.
  NONE,

  // User Address Pointer
  XP, YP,

  // Stack Pointer
  SP,

  // Return Pointer
  RP,

  // Frame Stack Pointer
  FP,

  // TOS: Top of (data) Stack
  TOS,
  // NOS: Next of (data) Stack
  NOS,

  // icr and psw may be implemented as a combined single register (DWORD)
  // ICR: Interrupt ContRol Word
  // PSW: Processor Status Word
  ICR, PSW
}
