package uk.jixun.project.Instruction;

import com.google.common.collect.ImmutableList;
import uk.jixun.project.Exceptions.OutOfRangeOperand;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.OpCode.SmNoOpCode;
import uk.jixun.project.Operand.ISmOperand;
import uk.jixun.project.Operand.SmTextOperand;

import java.util.List;

public class CommentInstruction extends AbstractBasicInstruction {
  private String comment;
  private ImmutableList<ISmOperand> operands;

  public CommentInstruction(String comment) {
    this.comment = comment;
    this.operands = ImmutableList.<ISmOperand>of(new SmTextOperand(comment));
  }

  @Override
  protected List<ISmOperand> getOperands() {
    return operands;
  }

  @Override
  public ISmOpCode getOpCode() {
    return new SmNoOpCode();
  }

  @Override
  public ISmOperand getOperand(int index) throws OutOfRangeOperand {
    if (index != 0) {
      throw new OutOfRangeOperand(index, this);
    }

    return new SmTextOperand(comment);
  }

  @Override
  public int getOperandCount() {
    return 1;
  }

  @Override
  public int getCycleTime() {
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

  @Override
  public boolean notForExecute() {
    return true;
  }
}
