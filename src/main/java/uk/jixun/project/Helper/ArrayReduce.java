package uk.jixun.project.Helper;

import java.util.Iterator;
import java.util.List;

public class ArrayReduce {
  public static <T, R> R reduce(T[] array, ReduceFn<T, R> reducer, R initVal) {
    R result = initVal;
    for(int i = 0; i < array.length; i++) {
      result = reducer.reduce(result, array[i], i);
    }
    return result;
  }

  public static <T, R> R reduce(Iterable<T> array, ReduceFn<T, R> reducer, R initVal) {
    int i = 0;
    R result = initVal;

    for (T item : array) {
      result = reducer.reduce(result, item, i);
      i++;
    }

    return result;
  }

  public interface ReduceFn <T, R> {
    R reduce(R prevResult, T value, int index);
  }
}
