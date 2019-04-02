package uk.jixun.project.Program.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

import java.util.List;

public interface ISmHistory {
  List<IDispatchRecord> getSortedHistoryBetween(int start, int end);

  List<IDispatchRecord> getHistoryBetween(IDispatchRecord a, IDispatchRecord b);
  List<IDispatchRecord> getHistoryBetween(ISmInstruction a, ISmInstruction b);
}
