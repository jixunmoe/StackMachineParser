package uk.jixun.project.Simulator;

import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;

import java.util.List;

public interface ISmSimulator {
  void setConfig(ISimulatorConfig config);

  void setProgram(ISmProgram program);

  /**
   * Execution context
   *
   * @return Execution context of current simulator.
   */
  IExecutionContext getContext();

  /**
   * Dispatch some command.
   *
   * @return Dispatched instructions in this cycle, that were not dispatched earlier.
   */
  List<IDispatchRecord> dispatch();

  /**
   * Check if the simulator has halt.
   * @return system halt status
   */
  boolean isHalt();
}
