package uk.jixun.project.Program;

import uk.jixun.project.Exceptions.LabelDuplicationException;
import uk.jixun.project.Exceptions.LabelNotFoundException;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.SysCall.ISysCall;
import uk.jixun.project.Program.NodeGraph.ISmProgramGraph;

import java.util.List;
import java.util.Map;

public interface ISmProgram {
  List<ISmInstruction> getInstructions();

  void setInstructions(List<ISmInstruction> instructions);

  ISmInstruction getInstruction(int index) throws ArrayIndexOutOfBoundsException;

  void addInstruction(ISmInstruction instruction);

  Map<String, Integer> getLabelMapping();

  void registerLabel(String label, int address);

  long resolveLabel(String label) throws LabelNotFoundException;

  ISmProgramGraph createGraph();

  /**
   * Register sys call label.
   * @param name SysCall Name
   * @param sysCall SysCall item
   */
  void registerSysCall(String name, ISysCall sysCall);

  /**
   * Check if given address is syscall
   * @param address Address to check
   * @return {@code true} if this is an syscall.
   */
  boolean isSysCall(int address);

  ISysCall getSysCall(int address);
}
