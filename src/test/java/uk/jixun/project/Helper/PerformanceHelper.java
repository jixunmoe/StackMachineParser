package uk.jixun.project.Helper;

import uk.jixun.project.Simulator.DispatchRecord.IDispatchRecord;
import uk.jixun.project.Simulator.ISmHistory;

public class PerformanceHelper {
  public static void printEfficiency(ISmHistory history) {
    long instCount = history.getAllRecords().size();
    long cycles = history
      .getAllRecords()
      .stream()
      .mapToInt(IDispatchRecord::getInstEndCycle)
      .max().orElse(0) + 1;
    double cpi = 1.0 * cycles / instCount;
    System.out.println(String.format(
      "Program completed in %d cycles, %d instructions, cpi = %.3f",
      cycles, instCount, cpi
    ));
  }
}
