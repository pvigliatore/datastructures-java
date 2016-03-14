package com.vigliatore.adt.heap;

public interface IndexedHeap<K, V> {

  boolean isEmpty();

  V pop();

  void add(K key, V value);

  void delete(K key);
}
