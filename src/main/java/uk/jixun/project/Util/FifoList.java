package uk.jixun.project.Util;

import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public class FifoList<T> extends LinkedList<T> {
  @SafeVarargs
  public static <T> FifoList<T> create(T... elements) {
    FifoList<T> result = new FifoList<T>();
    for (T v : elements) {
      result.push(v);
    }
    return result;
  }

  public static <T> FifoList<T> fromList(List<T> src) {
    FifoList<T> result = new FifoList<T>();
    result.addAll(src);
    return result;
  }

  @Override
  public void push(T o) {
    this.add(o);
  }

  public void pushUnique(T o) {
    if (!contains(o)) {
      push(o);
    }
  }

  @Override
  public T pop() {
    return super.removeLast();
  }

  /**
   * Recalculate address when index is negative (goes backwards)
   *
   * @param i index to be fixed
   * @return Fixed index (positive or zero)
   */
  private int idx(int i) {
    return i + (i < 0 ? size() : 0);
  }

  /**
   * Pop n-th item.
   *
   * @param index Index of the element to pop.
   * @return Popped item.
   */
  public T pop(int index) {
    index = idx(index);

    T result = this.get(index);
    this.remove(index);
    return result;
  }

  /**
   * Insert an element before given index (move all elements from this index back by 1).
   *
   * @param el    Element
   * @param index index to insert before
   */
  public void insertBefore(T el, int index) {
    index = idx(index);
    if (index >= size()) {
      push(el);
    } else {
      add(index, el);
    }
  }

  @Override
  public T peek() {
    return first();
  }

  public T first() {
    return super.peekFirst();
  }

  public T last() {
    return super.peekLast();
  }

  public T get(int index) {
    return super.get(idx(size() + index));
  }

  public FifoList<T> copy() {
    FifoList<T> result = new FifoList<>();
    result.addAll(ImmutableList.copyOf(this));
    return result;
  }
}
