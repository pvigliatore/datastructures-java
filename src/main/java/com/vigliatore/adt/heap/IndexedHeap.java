package com.vigliatore.adt.heap;

import com.vigliatore.adt.graph.Tuple;

public interface IndexedHeap<K, V> {

  int size();

  void add(K key, V value);

  void set(K key, V value);

  boolean isEmpty();

  Tuple<K, V> pop();

  void delete(K key);

  void update(K key, V value);

}
