package com.vigliatore.adt.heap;

public interface Heap<T> {
  void clear();

  int size();

  boolean add(T value);

  boolean isEmpty();

  T pop();
}
