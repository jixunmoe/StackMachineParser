package uk.jixun.project.Simulator;

import org.junit.jupiter.api.Test;
import uk.jixun.project.CodeLoader;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.SimulatorConfig.SimulatorConfigImpl;
import uk.jixun.project.StackMachineInstParser;

import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SmSimulatorTest {
  @Test
  void simulateProgram1() throws Exception {
    String text = CodeLoader.loadSampleCode("program1");
    StackMachineInstParser parser = new StackMachineInstParser(new Scanner(text));
    ISmProgram program = parser.toProgram();
    SmSimulator sim = new SmSimulator();
    sim.setProgram(program);
    sim.setConfig(new SimulatorConfigImpl(1, 1, 5));

    while(!sim.isHalt()) {
      sim.dispatch();
    }

    IExecutionContext ctx = sim.getContext();
    int result = ctx.read(ctx.getRegister(SmRegister.FP).get() - 2 + 1);
    System.out.println("Execution result: " + result);
    System.out.println("Program completed in " + sim.getContext().getCurrentCycle() + " cycles.");

    assertEquals(5050, result);
  }

  @Test
  void simulateProgram1Accel() throws Exception {
    String text = CodeLoader.loadSampleCode("program1");
    StackMachineInstParser parser = new StackMachineInstParser(new Scanner(text));
    ISmProgram program = parser.toProgram();

    String code = program.getInstructions().stream().map(inst -> inst.toAssemblyWithAddress(3)).collect(Collectors.joining("\n"));
    System.out.println(code);

    SmSimulator sim = new SmSimulator();
    sim.setProgram(program);
    sim.setConfig(new SimulatorConfigImpl(5, 5, 10));

    while(!sim.isHalt()) {
      sim.dispatch();
    }

    IExecutionContext ctx = sim.getContext();
    int result = ctx.read(ctx.getRegister(SmRegister.FP).get() - 2 + 1);
    System.out.println("Execution result: " + result);
    System.out.println("Program completed in " + sim.getContext().getCurrentCycle() + " cycles.");

    assertEquals(5050, result);
  }
}
