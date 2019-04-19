package uk.jixun.project.Simulator;

import uk.jixun.project.Simulator.DispatchRecord.IDispatchRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SmHistory implements ISmHistory {
  private List<IDispatchRecord> records = new ArrayList<>();
  private AtomicInteger id = new AtomicInteger(0);

  @Override
  public List<IDispatchRecord> getSortedHistoryBetween(int start, int end) {
    if (start > end) {
      return getSortedHistoryBetween(end, start);
    }

    return this
      .filter(x -> x.getExecutionId() >= start && x.getExecutionId() <= end)
      .collect(Collectors.toList());
  }

  @Override
  public List<IDispatchRecord> getHistoryBetween(IDispatchRecord a, IDispatchRecord b) {
    return getSortedHistoryBetween(a.getExecutionId(), b.getExecutionId());
  }

  @Override
  public IDispatchRecord getRecordAt(int exeId) {
    return this
      .filter(r -> r.getExecutionId() == exeId).findFirst().orElse(null);
  }

  @Override
  public IDispatchRecord getLastRecord() {
    return records
      .stream()
      .max(Comparator.comparing(IDispatchRecord::getExecutionId))
      .orElse(null);
  }

  @Override
  public Stream<IDispatchRecord> filter(Predicate<? super IDispatchRecord> predicate) {
    return records.stream().filter(predicate);
  }

  public void add(IDispatchRecord record) {
    record.setExecutionId(id.getAndIncrement());
    records.add(record);
  }

  public void add(IDispatchRecord ...records) {
    for (IDispatchRecord inst : records) {
      add(inst);
    }
  }
}
