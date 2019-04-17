package uk.jixun.project.OpCode;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

public class MockOpCode implements ISmOpCode {
  private ISmInstruction instruction;

  @Override
  public SmOpCodeEnum getOpCodeId() {
    return null;
  }

  @Override
  public int getVariant() {
    return 0;
  }

  @Override
  public void setVariant(int variant) {

  }

  @Override
  public SmRegister getRegisterVariant() {
    return SmRegister.NONE;
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {

  }

  @Override
  public String toAssembly() {
    return "";
  }

  @Override
  public ISmInstruction getInstruction() {
    return instruction;
  }

  @Override
  public void setInstruction(ISmInstruction instruction) {
    this.instruction = instruction;
  }

  @Override
  public int getConsume() {
    return 0;
  }

  @Override
  public int getProduce() {
    return 0;
  }

  @Override
  public boolean readRam() {
    return false;
  }

  @Override
  public boolean writeRam() {
    return false;
  }

  @Override
  public boolean isStaticRamAddress() {
    return false;
  }

  @Override
  public int accessRamAddress() throws Exception {
    return 0;
  }

  @Override
  public boolean isBranch() {
    return false;
  }

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {

  }
}
