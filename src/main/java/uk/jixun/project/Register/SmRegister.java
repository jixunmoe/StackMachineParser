package uk.jixun.project.Register;

public enum SmRegister {
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

  // icr and psw may be implemented as a combined single register (DWORD)
  // ICR: Interrupt ContRol Word
  // PSW: Processor Status Word
  ICR, PSW
}
