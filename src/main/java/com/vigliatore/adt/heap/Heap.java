package com.vigliatore.adt.heap;

import java.util.Collection;

public interface Heap<T> extends Iterable<T> {

  int size();

  void add(T value);

  void addAll(Collection<T> values);

  boolean isEmpty();

  T pop();

}
