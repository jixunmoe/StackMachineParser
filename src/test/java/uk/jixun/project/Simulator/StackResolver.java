package uk.jixun.project.Simulator;


import java.util.List;

public interface StackResolver {
  List<Integer> resolve(int offset, int exeId, int size);
}
