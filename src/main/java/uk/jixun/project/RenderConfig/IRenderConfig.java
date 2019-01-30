package uk.jixun.project.RenderConfig;

public interface IRenderConfig {
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
}
