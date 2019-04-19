package uk.jixun.project.Simulator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.jixun.project.CodeLoader;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.SimulatorConfig.SimulatorConfigImpl;
import uk.jixun.project.StackMachineInstParser;

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
}
