package uk.jixun.project.Operand;

import uk.jixun.project.Exceptions.ParseException;
import uk.jixun.project.Helper.ParseHelper;
import uk.jixun.project.Instruction.ISmInstruction;

public class SmOperandParser {
  public static ISmOperand parse(String operandStr, ISmInstruction instruction) {
    // Operand can be one of the following:
    // number, or label (address as an label as a number)

    // First, see if the string is a valid number.
    long operandLong = 0;
    try {
      operandLong = ParseHelper.parseLong(operandStr);
      SmConstantOperand operand = new SmConstantOperand();
      operand.setValue(operandLong);
      operand.setInstruction(instruction);
      return operand;
    } catch (NumberFormatException ignored) {
      // not a number
    }

    // then, check if the string is a valid label.
    // label must:
    // 1. not start with a digit;
    // 2. does not contain space;
    if (!ParseHelper.isDigit(operandStr.charAt(0)) && !ParseHelper.containsWhiteSpace(operandStr)) {
      SmLabelOperand labelOperand = new SmLabelOperand();
      labelOperand.setLabel(operandStr);
      labelOperand.setInstruction(instruction);
      return labelOperand;
    }

    throw ParseException.forInputString(operandStr);
  }
}
