package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;
import uk.jixun.project.Util.FifoList;

import java.util.ArrayList;
import java.util.List;

public class SmSimulator implements ISmSimulator {
  private int aluCount;
  private int ramCount;
  private ISmProgram program;
  private List<IDispatchRecord> history = new ArrayList<>();
  private List<IDispatchRecord> dispatchInstructions = new ArrayList<>();
  private FifoList<ISmInstruction> instructionsHeld = new FifoList<>();

  SimulatorContext context;

  public SmSimulator() {
    // Initialise context.
    context = new SimulatorContext();
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
    return context;
  }

  @Override
  public List<IDispatchRecord> dispatch() {
    // Get current executing instruction.
    ISmInstruction inst = null;
    IExecutionContext ctx = getContext();

    while(inst == null) {
      inst = program.getInstruction(ctx.getEip());
      ctx.incEip();
    }

    int cycle = ctx.getCycles();

    new DispatchRecord(cycle, cycle, cycle);

    return null;
  }

  @Override
  public List<IDispatchRecord> getDispatchHistory() {
    return history;
  }
}
