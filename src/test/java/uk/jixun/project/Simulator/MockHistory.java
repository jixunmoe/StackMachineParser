package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MockHistory implements ISmHistory {
  private List<IDispatchRecord> records = new ArrayList<>();
  private AtomicInteger id = new AtomicInteger(0);

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
    return records.stream().filter(r -> r.getExecutionId() == exeId).findFirst().orElse(null);
  }

  public List<IDispatchRecord> getRecords() {
    return records;
  }

  public void add(IDispatchRecord record) {
    record.setExecutionId(id.getAndIncrement());
    records.add(record);
  }

  public void add(ISmInstruction ...instructions) {
    for (ISmInstruction inst : instructions) {
      add(MockDispatchRecord.createFromInstruction(inst));
    }
  }

  public static MockHistory create() {
    return new MockHistory();
  }
}
