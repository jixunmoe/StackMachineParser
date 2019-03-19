package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;

public interface ISmSimulator {
  void setConfig(ISimulatorConfig config);
  void setProgram(ISmProgram program);

  /**
   * Execution context
   * @return Execution context of current simulator.
   */
  IExecutionContext getContext();

  /**
   * Dispatch some command.
   * @return Dispatched instructions in this cycle.
   */
  IDispatchedInstructions dispatchInstruction();
}
