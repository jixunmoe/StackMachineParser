package uk.jixun.project.SimulatorConfig;

public class SimulatorConfigImpl implements ISimulatorConfig {
  private int memoryPortCount = 0;
  private int aluCount = 0;

  public SimulatorConfigImpl(int memoryPortCount, int aluCount) {
    this.memoryPortCount = memoryPortCount;
    this.aluCount = aluCount;
  }

  @Override
  public int getMemoryPortCount() {
    return memoryPortCount;
  }

  @Override
  public int getAluCount() {
    return aluCount;
  }
}
