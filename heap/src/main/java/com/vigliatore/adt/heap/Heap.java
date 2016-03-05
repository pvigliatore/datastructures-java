package com.vigliatore.adt.heap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Heap<T extends Comparable<T>> {
  void clear();

  boolean addAll(Collection<? extends T> c);

  int size();

  boolean add(T value);

  boolean isEmpty();

  T pop();
}
