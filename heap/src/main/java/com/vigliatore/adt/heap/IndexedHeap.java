package com.vigliatore.adt.heap;

public interface IndexedHeap<K, V extends Comparable<V>> {

  boolean isEmpty();

  V pop();

  void add(K key, V value);

  void deleteKey(K key);
}
