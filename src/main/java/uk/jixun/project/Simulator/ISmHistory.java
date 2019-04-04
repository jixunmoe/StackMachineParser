package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

import java.util.List;

public interface ISmHistory {
  /**
   * Get a list of records between given dispatch record.
   * @param start Start
   * @param end End
   * @return list of records between (inclusive)
   */
  List<IDispatchRecord> getSortedHistoryBetween(int start, int end);

  /**
   * Get a list of records between given dispatch record. See {@link #getSortedHistoryBetween(int, int)}.
   * @param a Start
   * @param b End
   * @return list of records between (inclusive)
   */
  List<IDispatchRecord> getHistoryBetween(IDispatchRecord a, IDispatchRecord b);

  /**
   * Get a list of records between given dispatch record. See {@link #getSortedHistoryBetween(int, int)}.
   * @param a Start
   * @param b End
   * @return list of records between (inclusive)
   */
  List<IDispatchRecord> getHistoryBetween(ISmInstruction a, ISmInstruction b);

  /**
   * Get record at given execution id.
   * @param exeId Execution id to query.
   * @return return {@code null} if invalid or not exist.
   */
  IDispatchRecord getRecordAt(int exeId);
}
