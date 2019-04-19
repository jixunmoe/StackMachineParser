package uk.jixun.project.Helper;

import java.util.concurrent.atomic.AtomicBoolean;

public class LazyCacheResolver<T> {
  private T result = null;
  final private AtomicBoolean resolved = new AtomicBoolean(false);
  final private AtomicBoolean rejected = new AtomicBoolean(false);
  private boolean dirty = false;

  public void resolve(T data, boolean dirty) {
    synchronized (resolved) {
      if (resolved.getAndSet(true)) {
        throw new RuntimeException("already resolved / rejected");
      }

      result = data;
      this.dirty = dirty;
    }
  }

  public void resolve(T data) {
    resolve(data, false);
  }

  public void reject() {
    synchronized (resolved) {
      if (resolved.getAndSet(true)) {
        throw new RuntimeException("already resolved / rejected");
      }

      rejected.set(true);
    }
  }

  T getResult() {
    return result;
  }

  boolean isResolved() {
    return resolved.get();
  }
  boolean isRejected() {
    return rejected.get();
  }
  boolean isDirty() {
    return dirty;
  }

  void reset() {
    synchronized (resolved) {
      resolved.set(false);
      result = null;
      dirty = false;
    }
  }
}
