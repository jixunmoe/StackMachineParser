package uk.jixun.project.Simulator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.jixun.project.CodeLoader;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.SimulatorConfig.SimulatorConfigImpl;
import uk.jixun.project.StackMachineInstParser;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SmSimulatorTest {
  @ParameterizedTest(name = "correct simulate with {0} ram port, {1} alu, {2} search depth (program1)")
  @CsvSource({
    "1, 1, 5",
    "2, 2, 5",
    "5, 5, 5",
  })
  void simulateProgram1(int ram, int alu, int depth) throws Exception {
    String text = CodeLoader.loadSampleCode("program1");
    StackMachineInstParser parser = new StackMachineInstParser(new Scanner(text));
    ISmProgram program = parser.toProgram();
    SmSimulator sim = new SmSimulator();
    sim.setProgram(program);
    sim.setConfig(new SimulatorConfigImpl(ram, alu, depth));

    while(!sim.isHalt()) {
      sim.dispatch();
    }

    IExecutionContext ctx = sim.getContext();
    int result = ctx.read(ctx.getRegister(SmRegister.FP).get() - 2 + 1);
    System.out.println("Execution result: " + result);
    System.out.println("Program completed in " + ctx.getCurrentCycle() + " cycles.");

    assertEquals(5050, result);
  }

  @ParameterizedTest(name = "(loop1) correct simulate with {0} ram port, {1} alu, {2} search depth")
  @CsvSource({
    "1, 1, 5",
    "2, 2, 5",
    "5, 5, 5",
  })
  void simulateLoopTest(int ram, int alu, int depth) throws Exception {
    String text = CodeLoader.loadSampleCode("loop1");
    StackMachineInstParser parser = new StackMachineInstParser(new Scanner(text));
    ISmProgram program = parser.toProgram();
    SmSimulator sim = new SmSimulator();
    sim.setProgram(program);
    sim.setConfig(new SimulatorConfigImpl(ram, alu, depth));

    while(!sim.isHalt()) {
      sim.dispatch();
    }

    IExecutionContext ctx = sim.getContext();
    System.out.println("Program completed in " + ctx.getCurrentCycle() + " cycles.");

    List<Integer> fp = ctx.dump(ctx.getRegister(SmRegister.FP).get() - 6, 6);

    List<Integer> A = ctx.dump(fp.get(0), 12);
    List<Integer> B = ctx.dump(fp.get(1), 12);
    List<Integer> C = ctx.dump(fp.get(2), 12);
    List<Integer> D = ctx.dump(fp.get(3), 12);
    Integer[] ABCD = new Integer[]{
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
    };
    assertArrayEquals(ABCD, A.toArray());
    assertArrayEquals(ABCD, B.toArray());
    assertArrayEquals(ABCD, C.toArray());
    assertArrayEquals(ABCD, D.toArray());

    int i = fp.get(5);
    assertEquals(12, i);

    List<Integer> K = ctx.dump(fp.get(4), 12);
    assertArrayEquals(new Integer[]{
      0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 121
    }, K.toArray());
  }
}
