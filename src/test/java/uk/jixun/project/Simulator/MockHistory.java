package uk.jixun.project.Simulator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MockHistory implements ISmHistory {
  private List<IDispatchRecord> records = new ArrayList<>();

  @Override
  public List<IDispatchRecord> getSortedHistoryBetween(int start, int end) {
    if (start > end) {
      return getSortedHistoryBetween(end, start);
    }

    return records
      .stream()
      .filter(x -> x.getExecutionId() >= start && x.getExecutionId() <= end)
      .sorted(Comparator.comparing(IDispatchRecord::getExecutionId))
      .collect(Collectors.toList());
  }

  @Override
  public List<IDispatchRecord> getHistoryBetween(IDispatchRecord a, IDispatchRecord b) {
    return getSortedHistoryBetween(a.getExecutionId(), b.getExecutionId());
  }

  @Override
  public IDispatchRecord getRecordAt(int exeId) {
    return null;
  }

  public List<IDispatchRecord> getRecords() {
    return records;
  }
}
