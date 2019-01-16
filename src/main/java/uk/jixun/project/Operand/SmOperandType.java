package uk.jixun.project.Operand;

/**
 * Stack Machine Operand Type
 */
public enum SmOperandType {
  // Indirect: memory location
  INDIRECT,

  // Value: constant
  CONSTANT,

  // Text: Text Constant (comment)
  TEXT,

  // Label: Label that is associated to an address
  LABEL,
}
