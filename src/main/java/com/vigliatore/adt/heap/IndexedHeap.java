package com.vigliatore.adt.heap;

public interface IndexedHeap<K, V> extends Heap<V> {

  void delete(K key);

  void update(K key, V value);

}
