package uk.jixun.project.Simulator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import uk.jixun.project.CodeLoader;
import uk.jixun.project.Helper.ArrayReduce;
import uk.jixun.project.Helper.PerformanceHelper;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.SimulatorConfig.SimulatorConfigImpl;
import uk.jixun.project.StackMachineInstParser;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SmSimulatorTest {
  SmSimulator simulate(String code, int ram, int alu, int depth) throws Exception {
    String text = CodeLoader.loadSampleCode(code);
    StackMachineInstParser parser = new StackMachineInstParser(new Scanner(text));
    ISmProgram program = parser.toProgram();
    SmSimulator sim = new SmSimulator();
    sim.setProgram(program);
    sim.setConfig(new SimulatorConfigImpl(ram, alu, depth));

    while(!sim.isHalt()) {
      sim.dispatch();
    }

    return sim;
  }

  private static Stream<Arguments> getTestConfig() {
    if ("YES".equals(System.getenv("RUN_FIGURES"))) {
      final int n = 10;
      Arguments[] args = new Arguments[n];
      for(int i = 0; i < n; i++) {
        // NOTE: Do not commit changes to the next line.
        args[i] = Arguments.of(999, i + 1, 999);
      }
      return Stream.of(args);
    }
    return Stream.of(
      Arguments.of(1, 1, 5),
      Arguments.of(2, 2, 5),
      Arguments.of(5, 5, 5));
  }

  @DisplayName("Loop 1: Appendix C.1 from Shi")
  @ParameterizedTest(name = "with {0} ram port, {1} alu, {2} search depth")
  @MethodSource("getTestConfig")
  void testSimLoop1(int ram, int alu, int depth) throws Exception {
    SmSimulator sim = simulate("loop1", ram, alu, depth);

    IExecutionContext ctx = sim.getContext();
    PerformanceHelper.printEfficiency(ctx.getHistory());

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

  @DisplayName("Loop 1 (loop unrolled): Appendix C.1 from Shi")
  @ParameterizedTest(name = "with {0} ram port, {1} alu, {2} search depth")
  @MethodSource("getTestConfig")
  void testSimLoop1_imp(int ram, int alu, int depth) throws Exception {
    SmSimulator sim = simulate("loop1_imp", ram, alu, depth);

    IExecutionContext ctx = sim.getContext();
    PerformanceHelper.printEfficiency(ctx.getHistory());

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

  @DisplayName("Loop 2: Appendix C.1 from Shi")
  @ParameterizedTest(name = "with {0} ram port, {1} alu, {2} search depth")
  @MethodSource("getTestConfig")
  void testSimLoop2(int ram, int alu, int depth) throws Exception {
    SmSimulator sim = simulate("loop2", ram, alu, depth);

    IExecutionContext ctx = sim.getContext();
    PerformanceHelper.printEfficiency(ctx.getHistory());

    List<Integer> fp = ctx.dump(ctx.getRegister(SmRegister.FP).get() - 4, 4);

    List<Integer> X = ctx.dump(fp.get(0), 12);
    List<Integer> Y = ctx.dump(fp.get(1), 12);
    // int i = fp.get(1);
    // int T = fp.get(2);

    Integer[] expectedX = new Integer[]{
      50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61
    };
    Integer[] expectedY = new Integer[]{
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
    };
    assertArrayEquals(expectedX, X.toArray());
    assertArrayEquals(expectedY, Y.toArray());
  }

  @DisplayName("Sample 1: sum(100)")
  @ParameterizedTest(name = "with {0} ram port, {1} alu, {2} search depth")
  @MethodSource("getTestConfig")
  void testSimSample1(int ram, int alu, int depth) throws Exception {
    SmSimulator sim = simulate("sample1", ram, alu, depth);

    IExecutionContext ctx = sim.getContext();
    int result = ctx.read(ctx.getRegister(SmRegister.FP).get() - 2 + 1);
    System.out.println("Execution result: " + result);
    PerformanceHelper.printEfficiency(ctx.getHistory());

    assertEquals(5050, result);
  }

  @DisplayName("Sample 2: factorial(10)")
  @ParameterizedTest(name = "with {0} ram port, {1} alu, {2} search depth")
  @MethodSource("getTestConfig")
  void testSimSample2(int ram, int alu, int depth) throws Exception {
    // 10! = 3628800
    SmSimulator sim = simulate("sample2", ram, alu, depth);

    IExecutionContext ctx = sim.getContext();
    int result = ctx.resolveStack(0, ctx.getHistory().getLastRecord().getExecutionId());
    System.out.println("Execution result: " + result);
    PerformanceHelper.printEfficiency(ctx.getHistory());

    assertEquals(3628800, result);
  }

  @DisplayName("Sample 3: fibonacci(10)")
  @ParameterizedTest(name = "with {0} ram port, {1} alu, {2} search depth")
  @MethodSource("getTestConfig")
  void testSimSample3(int ram, int alu, int depth) throws Exception {
    // fab(10) = 55
    // fab(11) = 89
    // fab(12) = 144
    // fab(13) = 233
    // fab(14) = 377
    // fab(15) = 610
    SmSimulator sim = simulate("sample3", ram, alu, depth);

    IExecutionContext ctx = sim.getContext();
    int result = ctx.resolveStack(0, ctx.getHistory().getLastRecord().getExecutionId());
    System.out.println("Execution result: " + result);
    PerformanceHelper.printEfficiency(ctx.getHistory());

    assertEquals(55, result);
  }

  @DisplayName("Sample 4: fibonacci(20) + cache")
  @ParameterizedTest(name = "with {0} ram port, {1} alu, {2} search depth")
  @MethodSource("getTestConfig")
  void testSimSample4(int ram, int alu, int depth) throws Exception {
    // fab(10) = 55
    // fab(11) = 89
    // fab(12) = 144
    // fab(13) = 233
    // fab(14) = 377
    // fab(15) = 610
    // fab(20) = 6765
    SmSimulator sim = simulate("sample4", ram, alu, depth);

    IExecutionContext ctx = sim.getContext();
    int result = ctx.resolveStack(0, ctx.getHistory().getLastRecord().getExecutionId());
    System.out.println("Execution result: " + result);
    PerformanceHelper.printEfficiency(ctx.getHistory());

    assertEquals(6765, result);
  }
}
