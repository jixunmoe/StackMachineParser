package uk.jixun.project.RenderConfig;

public class RenderConfigImpl implements IRenderConfig {
  private int memoryPortCount = 0;
  private int aluCount = 0;

  public RenderConfigImpl(int memoryPortCount, int aluCount) {
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
