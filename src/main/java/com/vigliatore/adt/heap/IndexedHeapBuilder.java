package com.vigliatore.adt.heap;

import java.util.Comparator;

public class IndexedHeapBuilder<K, V> {

  private Comparator<V> comparator;

  public IndexedHeapBuilder<K, V> setComparator(Comparator<V> comparator) {
    this.comparator = comparator;
    return this;
  }

  public IndexedHeap<K, V> build() {
    return new SimpleIndexedHeap<K, V>(comparator);
  }

}
