package com.vigliatore.adt.heap;

public interface Heap<T> extends Iterable<T> {

  int size();

  void add(T value);

  boolean isEmpty();

  T pop();

}
