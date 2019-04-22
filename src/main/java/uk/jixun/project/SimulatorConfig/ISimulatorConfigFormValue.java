package uk.jixun.project.SimulatorConfig;

public interface ISimulatorConfigFormValue extends ISimulatorConfig {
  /**
   * Dependency threshold, if the difference between the ending cycle number of
   * two dependent instructions, the line shall not be drawn.
   * @return Threshold value.
   */
  int getDependencyThreshold();
}
