package uk.jixun.project.OpCode;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.IExecutionContext;
import uk.jixun.project.Util.FifoList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MockOpCode extends AbstractBasicOpCode {
  private MockEvaluate evaluateFn = null;
  private boolean readRam = false;
  private boolean writeRam = false;
  private boolean staticRamAddress = true;
  private int accessRamAddress = 0;
  private boolean isBranch = false;
  private int produce = 0;
  private int consume = 0;

  @Override
  public SmOpCodeEnum getOpCodeId() {
    return SmOpCodeEnum.NONE;
  }

  @Override
  public void setVariant(int variant) {
    this.variant = variant;
  }

  @Override
  public SmRegister getRegisterVariant() {
    return SmRegister.NONE;
  }

  @Override
  public void setRegisterVariant(SmRegister regVariant) {

  }

  // Properties

  @Override
  public int getConsume() {
    return consume;
  }

  @Override
  public int getProduce() {
    return produce;
  }

  @Override
  public boolean readRam() {
    return readRam;
  }

  @Override
  public boolean writeRam() {
    return writeRam;
  }

  @Override
  public boolean isStaticRamAddress() {
    return staticRamAddress;
  }

  @Override
  public int accessRamAddress() throws Exception {
    return accessRamAddress;
  }

  @Override
  public boolean isBranch() {
    return isBranch;
  }

  @Override
  public boolean isWriteFlag() {
    return false;
  }

  @Override
  public boolean isReadFlag() {
    return false;
  }

  public void setEvaluateFn(MockEvaluate evaluateFn) {
    this.evaluateFn = evaluateFn;
  }

  public void setReadRam(boolean readRam) {
    this.readRam = readRam;
  }

  public void setWriteRam(boolean writeRam) {
    this.writeRam = writeRam;
  }

  public void setStaticRamAddress(boolean staticRamAddress) {
    this.staticRamAddress = staticRamAddress;
  }

  public void setAccessRamAddress(int accessRamAddress) {
    this.accessRamAddress = accessRamAddress;
  }

  public void setBranch(boolean branch) {
    isBranch = branch;
  }

  public void setProduce(int produce) {
    this.produce = produce;
  }

  public void setConsume(int consume) {
    this.consume = consume;
  }

  // Evaluate

  @Override
  public void evaluate(FifoList<Integer> stack, IExecutionContext ctx) throws Exception {
    if (evaluateFn == null) {
      throw new Exception("evaluateFn is null");
    }

    evaluateFn.evaluate(stack, ctx);
  }

  public void evaluateWith(MockEvaluate callback) {
    evaluateFn = callback;
  }

  public static MockOpCode createDependencyTestOpCode(int consume, int produce) {
    MockOpCode opcode = new MockOpCode();
    opcode.setConsume(consume);
    opcode.setProduce(produce);
    return opcode;
  }

  public static MockOpCode createMockPush(int value) {
    MockOpCode opcode = new MockOpCode();
    opcode.setConsume(0);
    opcode.setProduce(1);
    opcode.evaluateWith((FifoList<Integer> stack, IExecutionContext ctx) -> {
      stack.push(value);
    });
    return opcode;
  }

  public static MockOpCode createMockPop(int count) {
    MockOpCode opcode = new MockOpCode();
    opcode.setConsume(count);
    opcode.setProduce(0);
    opcode.evaluateWith((FifoList<Integer> stack, IExecutionContext ctx) -> {
      assert stack.size() == count;
      stack.clear();
    });
    return opcode;
  }

  public static MockOpCode createMockCalculation(int paramCount, int ...results) {
    MockOpCode opcode = new MockOpCode();
    opcode.setConsume(paramCount);
    opcode.setProduce(results.length);
    opcode.evaluateWith((FifoList<Integer> stack, IExecutionContext ctx) -> {
      assert stack.size() == paramCount;
      stack.clear();

      Arrays.stream(results).forEach(stack::push);
    });
    return opcode;
  }
}
