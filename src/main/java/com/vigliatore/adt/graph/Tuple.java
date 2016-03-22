package com.vigliatore.adt.graph;

import java.util.AbstractMap;
import java.util.Map;

public class Tuple<K, V> {

  private Map.Entry<K, V> tuple;

  public static <K, V> Tuple<K, V> create(K key, V value) {
    return null;
  }

  public Tuple(K key, V value) {
    this.tuple = new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
  }

  public K key() {
    return tuple.getKey();
  }

  public V value() {
    return tuple.getValue();
  }

}
