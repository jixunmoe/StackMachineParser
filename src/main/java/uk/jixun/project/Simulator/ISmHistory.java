package uk.jixun.project.Simulator;

import org.jetbrains.annotations.Nullable;
import uk.jixun.project.Simulator.DispatchRecord.IDispatchRecord;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ISmHistory {
  /**
   * Get a list of records between given dispatch record.
   *
   * @param start Start
   * @param end   End
   * @return list of records between (inclusive)
   */
  List<IDispatchRecord> getSortedHistoryBetween(int start, int end);

  /**
   * Get a list of records between given dispatch record. See {@link #getSortedHistoryBetween(int, int)}.
   *
   * @param a Start
   * @param b End
   * @return list of records between (inclusive)
   */
  List<IDispatchRecord> getHistoryBetween(IDispatchRecord a, IDispatchRecord b);

  /**
   * Get record at given execution id.
   *
   * @param exeId Execution id to query.
   * @return return {@code null} if invalid or not exist.
   */
  @Nullable
  IDispatchRecord getRecordAt(int exeId);

  IDispatchRecord getLastRecord();
  Stream<IDispatchRecord> filter(Predicate<? super IDispatchRecord> predicate);
}
