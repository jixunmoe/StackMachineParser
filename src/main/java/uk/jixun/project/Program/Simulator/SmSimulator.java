package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;

public class SmSimulator implements ISmSimulator {
  private int aluCount;
  private int ramCount;
  private ISmProgram program;

  SimulatorContext context;

  public SmSimulator() {

  }

  @Override
  public void setConfig(ISimulatorConfig config) {
    aluCount = config.getAluCount();
    ramCount = config.getMemoryPortCount();
  }

  @Override
  public void setProgram(ISmProgram program) {
    this.program = program;
  }

  @Override
  public IExecutionContext getContext() {
    return null;
  }

  @Override
  public IDispatchedInstructions dispatchInstruction() {
    return null;
  }
}
