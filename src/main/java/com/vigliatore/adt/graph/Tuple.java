package com.vigliatore.adt.graph;

import java.util.AbstractMap;
import java.util.Map;

public class Tuple<K, V> {

  private final Map.Entry<K, V> tuple;

  public static <K, V> Tuple<K, V> instance(K key, V value) {
    return new Tuple<K, V>(key, value);
  }

  private Tuple(K key, V value) {
    this.tuple = new AbstractMap.SimpleImmutableEntry<>(key, value);
  }

  public K key() {
    return tuple.getKey();
  }

  public V value() {
    return tuple.getValue();
  }

}
