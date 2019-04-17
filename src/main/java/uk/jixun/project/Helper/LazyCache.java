package uk.jixun.project.Helper;

import uk.jixun.project.Exceptions.NotCachedException;

import java.util.concurrent.atomic.AtomicBoolean;

public class LazyCache<T> {
  private final AtomicBoolean cached = new AtomicBoolean(false);
  private T cache = null;

  public T cache(GetResult<T> fn) {
    synchronized (cached) {
      if (!cached.getAndSet(true)) {
        cache = fn.resolve();
      }
    }

    return cache;
  }

  public T exceptionalCache(GetResultWithException<T> fn) throws Exception {
    synchronized (cached) {
      if (!cached.get()) {
        cache = fn.resolve();
        cached.set(true);
      }
    }

    return cache;
  }

  /**
   * Cache data if not present.
   * @param data Data to be cached.
   * @return {@code true} if cached; {@code false} if a cache already present.
   */
  public boolean cache(T data) {
    synchronized (cached) {
      if (!cached.getAndSet(true)) {
        cache = data;
        return true;
      }
    }

    return false;
  }

  public T get() throws NotCachedException {
    synchronized (cached) {
      if (!cached.get()) {
        throw new NotCachedException();
      }
    }

    return cache;
  }

  public boolean cached() {
    synchronized (cached) {
      return cached.get();
    }
  }

  public void invalidate() {
    synchronized (cached) {
      if (cached.getAndSet(false)) {
        cache = null;
      }
    }
  }

  public interface GetResult<T> {
    T resolve();
  }
  public interface GetResultWithException<T> {
    T resolve() throws Exception;
  }
}
