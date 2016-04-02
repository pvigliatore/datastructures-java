package com.vigliatore.adt.heap;

import java.util.Iterator;

class HeapIterator<T> implements Iterator<T> {

  private final Heap<T> heap;

  public HeapIterator(Heap<T> heap) {
    this.heap = heap;
  }

  @Override
  public boolean hasNext() {
    return !heap.isEmpty();
  }

  @Override
  public T next() {
    return heap.pop();
  }

}
