package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.OpCode.SmOpCodeEnum;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Util.FifoList;

public class MockOpCode implements ISmOpCode {
  private int consumeValue = 0;
  private int produceValue = 0;
  private boolean readRamValue = false;
  private boolean writeRamValue = false;
  private boolean isStaticRamAddressValue = false;
  private int accessRamAddressValue = 0;
  private boolean isBranchValue = false;

  public static MockOpCode createDependencyTestOpCode(int consume, int produce) {
    MockOpCode opcode = new MockOpCode();
    opcode.setConsumeValue(consume);
    opcode.setProduceValue(produce);
    return opcode;
  }

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
    return null;
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {

  }

  @Override
  public String toAssembly() {
    return null;
  }

  @Override
  public ISmInstruction getInstruction() {
    return null;
  }

  @Override
  public void setInstruction(ISmInstruction instruction) {

  }

  @Override
  public int getConsume() {
    return consumeValue;
  }

  @Override
  public int getProduce() {
    return produceValue;
  }

  @Override
  public boolean readRam() {
    return readRamValue;
  }

  @Override
  public boolean writeRam() {
    return writeRamValue;
  }

  @Override
  public boolean isStaticRamAddress() {
    return isStaticRamAddressValue;
  }

  @Override
  public int accessRamAddress() throws Exception {
    return accessRamAddressValue;
  }

  @Override
  public boolean isBranch() {
    return isBranchValue;
  }

  public void setConsumeValue(int value) {
    this.consumeValue = value;
  }

  public void setProduceValue(int value) {
    this.produceValue = value;
  }

  public void setReadRamValue(boolean value) {
    this.readRamValue = value;
  }

  public void setWriteRamValue(boolean value) {
    this.writeRamValue = value;
  }

  public void setIsStaticRamAddressValue(boolean value) {
    this.isStaticRamAddressValue = value;
  }

  public void setAccessRamAddressValue(int value) {
    this.accessRamAddressValue = value;
  }

  public void setIsBranchValue(boolean value) {
    this.isBranchValue = value;
  }

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {

  }
}
