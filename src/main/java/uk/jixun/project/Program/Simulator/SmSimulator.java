package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;
import uk.jixun.project.Util.FifoList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SmSimulator implements ISmSimulator {
  private int aluLimit;
  private int ramLimit;
  private int searchDepth;

  private ISmProgram program;
  private List<IDispatchRecord> history = new ArrayList<>();

  /**
   * Instruction held in the "to be processed" queue.
   */
  private FifoList<ISmInstruction> queuedInst = new FifoList<>();

  SimulatorContext context;

  public SmSimulator() {
    // Initialise context.
    context = new SimulatorContext();
  }

  @Override
  public void setConfig(ISimulatorConfig config) {
    aluLimit = config.getAluCount();
    ramLimit = config.getMemoryPortCount();
    searchDepth = config.getSearchDepth();
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
    IExecutionContext ctx = getContext();

    List<IDispatchRecord> results = new ArrayList<>();

    // Calculate current available ALU and ram port.
    int cycle = ctx.getCycles();
    Stream<IDispatchRecord> executeStream = history.stream()
      .filter(record -> record.executesAt(cycle));

    // Get the available resource counts
    int aluCount = aluLimit - (int) executeStream
      .filter(IDispatchRecord::usesAlu)
      .count();
    int ramCount = ramLimit - (int) executeStream
      .filter(IDispatchRecord::readOrWrite)
      .count();

    while(queuedInst.size() < searchDepth) {
      // If the last item is a branch, don't continue.
      if (queuedInst.last().isBranch()) {
        break;
      }

      queuedInst.push(program.getInstruction(ctx.getEip()));
      ctx.incEip();

      // Skip dispatch if we are out of resource or full.
      if (aluCount == 0 && ramCount == 0) {
        continue;
      }

      // Try to dispatch instructions.
      while(!queuedInst.isEmpty()) {
        // Ensure they are legal in debug build
        assert aluCount >= 0;
        assert ramCount >= 0;

        ISmInstruction inst = queuedInst.peekFirst();
        if (inst.usesAlu() && aluCount > 0) {
          // Not enough ALU available
          continue;
        }

        if (inst.readOrWriteRam() && ramCount > 0) {
          continue;
        }

        // FIXME: Instruction dependency check here.

        // Decrease available resource counter.
        if (inst.usesAlu()) aluCount--;
        if (inst.readOrWriteRam()) ramCount--;

        // FIXME: Assume each instruction uses 1 cycle.
        DispatchRecord record = new DispatchRecord(inst);
        record.setCycleStart(cycle);
        record.setCycleEnd(cycle);

        history.add(record);
        results.add(record);
      }
    }

    // TODO: Simulate instructions that should complete in this cycle.
    ctx.nextCycle();

    return results;
  }

  @Override
  public List<IDispatchRecord> getDispatchHistory() {
    return history;
  }
}
