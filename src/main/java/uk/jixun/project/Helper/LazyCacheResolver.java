package uk.jixun.project.Helper;

import java.util.concurrent.atomic.AtomicBoolean;

public class LazyCacheResolver<T> {
  private T result = null;
  final private AtomicBoolean resolved = new AtomicBoolean(false);
  final private AtomicBoolean rejected = new AtomicBoolean(false);

  public void resolve(T data) {
    synchronized (resolved) {
      if (resolved.getAndSet(true)) {
        throw new RuntimeException("already resolved / rejected");
      }

      result = data;
    }
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
}
