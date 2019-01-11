package uk.jixun.project.Exceptions;

import uk.jixun.project.Instruction.ISmInstruction;

/**
 * Out of range index provided when requesting operand.
 */
public class OutOfRangeOperand extends Exception {
  private ISmInstruction inst;
  private int oprIndex;

  public OutOfRangeOperand(int index, ISmInstruction inst) {
    oprIndex = index;
    this.inst = inst;
  }

  public String toString() {
    return "OutOfRangeOperand["
      + "inst=" + inst.toString() + "; "
      + "index=" + oprIndex + "; "
      + "len=" + inst.GetOperandCount()
      + "]";
  }
}
