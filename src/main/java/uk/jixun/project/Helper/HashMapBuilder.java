package uk.jixun.project.Helper;

import java.util.HashMap;
import java.util.Map;

public class HashMapBuilder<K, V> extends HashMap<K, V> implements Map<K, V> {
  public HashMapBuilder<K, V> set(K key, V val) {
    this.put(key, val);
    return this;
  }

  public static <K, V> HashMapBuilder<K, V> create() {
    return new HashMapBuilder<>();
  }
}
