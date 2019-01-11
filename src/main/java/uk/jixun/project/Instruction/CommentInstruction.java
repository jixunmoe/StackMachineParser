package uk.jixun.project.Instruction;

import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Operand.SmTextOperand;

public class CommentInstruction implements ISmInstruction {
  private String comment;
  public CommentInstruction(String comment) {
    this.comment = comment;
  }

  @Override
  public ISmOperand GetOperand(int index) throws OutOfRangeOperand {
    if (index != 0) {
      throw new OutOfRangeOperand(index, this);
    }

    return new SmTextOperand(comment);
  }

  @Override
  public int GetOperandCount() {
    return 1;
  }

  @Override
  public int GetCycleTime() {
    // This instruction is comment
    return 0;
  }

  @Override
  public String toAssembly() {
    return "# " + comment.replace("\n", "\\n");
  }

  @Override
  public String toString() {
    return "CommentInstruction{" +
      "comment='" + comment + '\'' +
      '}';
  }
}
