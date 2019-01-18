package uk.jixun.project;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;
import uk.jixun.project.Instruction.CommentInstruction;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.ISmOpCode;
import uk.jixun.project.OpCode.SmOpCodeEnum;
import uk.jixun.project.Operand.SmLabelOperand;
import uk.jixun.project.Register.SmRegister;

import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class StackMachineInstParserTest {

  @Test
  void parseCodeSample1() throws Exception {
    URL url = Resources.getResource(StackMachineInstParserTest.class, "SampleCode/sample1.s");
    String text = Resources.toString(url, Charsets.UTF_8);
    StackMachineInstParser parser = new StackMachineInstParser(new Scanner(text));

    ISmInstruction inst;
    inst = parser.next();
    assertEquals(SmOpCodeEnum.ABSOLUTE_JUMP, inst.getOpCode().getOpCodeId());
    assertEquals(1, inst.getOperandCount());
    assertEquals("start", ((SmLabelOperand)inst.getOperand(0)).getLabel());

    inst = parser.next();
    assertEquals(SmOpCodeEnum.NONE, inst.getOpCode().getOpCodeId());
    assertTrue(inst instanceof CommentInstruction);

    inst = parser.next();
    assertEquals(SmOpCodeEnum.POP_REGISTER_DEC, inst.getOpCode().getOpCodeId());
    assertEquals(SmRegister.XP, inst.getOpCode().getRegisterVariant());
    assertEquals(0, inst.getOpCode().getVariant());
    assertEquals(0, inst.getOperandCount());

    inst = parser.next();
    assertEquals(SmOpCodeEnum.PUSH_REGISTER_DEC, inst.getOpCode().getOpCodeId());
    assertEquals(SmRegister.YP, inst.getOpCode().getRegisterVariant());
    assertEquals(0, inst.getOpCode().getVariant());
    assertEquals(0, inst.getOperandCount());

    inst = parser.next();
    assertEquals(SmOpCodeEnum.TEST_XP_ZERO, inst.getOpCode().getOpCodeId());

    inst = parser.next();
    assertEquals(SmOpCodeEnum.COND_RELATIVE_JUMP, inst.getOpCode().getOpCodeId());
    assertEquals("loop", ((SmLabelOperand)inst.getOperand(0)).getLabel());

    // Check for registered labels
    assertEquals(1, inst.getProgram().resolveLabel("loop"));
    assertEquals(1, inst.getProgram().resolveLabel("start"));
  }

  @Test
  void parseInstructionWithVariantOpcode() throws Exception {
    StackMachineInstParser parser = new StackMachineInstParser(new Scanner("COPY3"));
    ISmInstruction instruction = parser.next();
    ISmOpCode opcode = instruction.getOpCode();
    assertEquals(SmOpCodeEnum.COPY, opcode.getOpCodeId());
    assertEquals(3, opcode.getVariant());
  }
}
