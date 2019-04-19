package uk.jixun.project.Helper;

import java.util.concurrent.atomic.AtomicBoolean;

public class LazyCache<T> {
  private final AtomicBoolean cached = new AtomicBoolean(false);
  private T cache = null;

  private GetResult<T> callback;

  public LazyCache(GetResult<T> callback) {
    this.callback = callback;
  }

  public T get() {
    synchronized (cached) {
      if (!cached.getAndSet(true)) {
        LazyCacheResolver<T> resolver = new LazyCacheResolver<>();
        callback.resolve(resolver);
        assert resolver.isResolved();

        if (resolver.isRejected()) {
          cached.set(false);
        } else {
          cache = resolver.getResult();
        }
      }
    }

    return cache;
  }

  /**
   * Cache data if not present.
   * @param data Data to be isCached.
   * @return {@code true} if isCached; {@code false} if a cache already present.
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

  public boolean isCached() {
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
    void resolve(LazyCacheResolver<T> resolver);
  }
}
