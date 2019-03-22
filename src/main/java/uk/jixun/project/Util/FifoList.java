package uk.jixun.project.Util;

import java.util.LinkedList;

public class FifoList<T> extends LinkedList<T> {
  @Override
  public void push(T o) {
    this.add(o);
  }

  @Override
  public T pop() {
    return this.poll();
  }

  /**
   * Pop n-th item.
   * @param index Index of the element to pop.
   * @return Popped item.
   */
  public T pop(int index) {
    T result = this.get(index);
    this.remove(result);
    this.remove(index);
    return result;
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
}
