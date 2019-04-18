package uk.jixun.project.Program;

import uk.jixun.project.Exceptions.LabelDuplicationException;
import uk.jixun.project.Exceptions.LabelNotFoundException;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Program.NodeGraph.ISmProgramGraph;

import java.util.List;
import java.util.Map;

public interface ISmProgram {
  List<ISmInstruction> getInstructions();

  void setInstructions(List<ISmInstruction> instructions);

  ISmInstruction getInstruction(int index) throws ArrayIndexOutOfBoundsException;

  void addInstruction(ISmInstruction instruction);

  Map<String, Long> getLabelMapping();

  void registerLabel(String label, long address);

  long resolveLabel(String label) throws LabelNotFoundException;

  ISmProgramGraph createGraph();

  /**
   * Register sys call label.
   * @param name SysCall Name
   * @param sysCallId SysCall function id.
   */
  void registerSysCall(String name, int sysCallId);

  /**
   * Check if given address is syscall
   * @param address Address to check
   * @return {@code true} if this is an syscall.
   */
  boolean isSysCall(int address);
}
