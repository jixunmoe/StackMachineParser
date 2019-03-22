package uk.jixun.project.SimulatorConfig;

public interface ISimulatorConfig {
  /**
   * Get the number of memory ports.
   * @return number of memory ports.
   */
  int getMemoryPortCount();

  /**
   * Get the number of ALU present.
   * @return number of ALU.
   */
  int getAluCount();

  /**
   * Get the number of instructions to look ahead (for pipeline)
   * @return The number of instructions to look ahead
   */
  int getSearchDepth();
}
