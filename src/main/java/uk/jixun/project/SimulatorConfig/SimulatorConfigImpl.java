package uk.jixun.project.SimulatorConfig;

public class SimulatorConfigImpl implements ISimulatorConfig {
  private int searchDepth = 0;
  private int memoryPortCount = 0;
  private int aluCount = 0;

  public SimulatorConfigImpl(int memoryPortCount, int aluCount, int searchDepth) {
    this.memoryPortCount = memoryPortCount;
    this.aluCount = aluCount;
    this.searchDepth = searchDepth;
  }

  @Override
  public int getMemoryPortCount() {
    return memoryPortCount;
  }

  @Override
  public int getAluCount() {
    return aluCount;
  }

  @Override
  public int getSearchDepth() {
    return searchDepth;
  }
}
