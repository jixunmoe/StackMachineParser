package uk.jixun.project.Simulator;

import java.util.List;

public interface IMemoryModel {
  // Simulator needs to have ram and stack.
  int read(int address);

  void write(int address, int value);

  List<Integer> dump(int start, int len);
}
