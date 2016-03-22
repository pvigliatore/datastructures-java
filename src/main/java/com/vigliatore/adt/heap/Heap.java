package com.vigliatore.adt.heap;

public interface Heap<T> {

  int size();

  void add(T value);

  boolean isEmpty();

  T pop();

}
