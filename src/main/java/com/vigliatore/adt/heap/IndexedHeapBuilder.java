package com.vigliatore.adt.heap;

import java.util.Comparator;
import java.util.function.Function;

public class IndexedHeapBuilder<K, V> {

  private Comparator<V> comparator;

  private Function<V, K> idFunction;

  public IndexedHeapBuilder<K, V> setComparator(Comparator<V> comparator) {
    this.comparator = comparator;
    return this;
  }

  public IndexedHeapBuilder<K, V> setIdFunction(Function<V, K> idFunction) {
    this.idFunction = idFunction;
    return this;
  }

  public IndexedHeap<K, V> build() {
    if (idFunction == null) {
      throw new IllegalStateException();
    } else if (comparator == null) {
      throw new IllegalStateException();
    }
    return new SimpleIndexedHeap<K, V>(idFunction, comparator);
  }

}
