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

  @Override
  public T peek() {
    return super.peek();
  }
}
