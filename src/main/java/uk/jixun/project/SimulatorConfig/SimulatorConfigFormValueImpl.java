package uk.jixun.project.SimulatorConfig;

public class SimulatorConfigFormValueImpl extends SimulatorConfigImpl implements ISimulatorConfigFormValue {
  private final int threshold;

  public SimulatorConfigFormValueImpl(int memoryPortCount, int aluCount, int searchDepth, int threshold) {
    super(memoryPortCount, aluCount, searchDepth);

    this.threshold = threshold;
  }

  @Override
  public int getDependencyThreshold() {
    return threshold;
  }
}
