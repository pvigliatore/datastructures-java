package com.vigliatore.adt.heap;

import java.util.*;

public class SimpleHeap<T> implements Heap<T> {

  public static <T extends Comparable<T>> Heap<T> minHeap() {
    return new SimpleHeap<T>(Comparator.naturalOrder());
  }

  public static <T extends Comparable<T>> Heap<T> maxHeap() {
    return new SimpleHeap<T>(Comparator.<T> naturalOrder().reversed());
  }

  public static <T> Heap<T> instance(Comparator<T> comparator) {
    if (comparator == null) {
      throw new IllegalArgumentException();
    }
    return new SimpleHeap<>(comparator);
  }

  private final ArrayList<T> values;
  private final Comparator<T> comparator;

  private SimpleHeap(Comparator<T> comparator) {
    this.values = new ArrayList<>();
    this.comparator = comparator;
  }

  public void clear() {
    values.clear();
  }

  public int size() {
    return values.size();
  }

  public void add(T value) {
    values.add(value);
    swim(size() - 1);
  }

  @Override
  public void addAll(Collection<T> values) {
    values.forEach(this::add);
  }

  private void swim(int index) {
    if (index == 0) {
      return;
    }

    int parentIndex = getParentIndex(index);
    if (hasPriority(index, parentIndex)) {
      swap(index, parentIndex);
      swim(parentIndex);
    }
  }

  private boolean hasPriority(int index1, int index2) {
    return comparator.compare(get(index1), get(index2)) < 0;
  }

  private T get(int index) {
    return values.get(index);
  }

  private void swap(int from, int to) {
    T temp = get(from);
    values.set(from, get(to));
    values.set(to, temp);
  }

  private int getParentIndex(int index) {
    return (index - 1) / 2;
  }

  private void delete(int index) {
    int lastIndex = values.size() - 1;
    swap(index, lastIndex);
    values.remove(lastIndex);
    sink(index);
  }

  private void sink(int index) {
    int highestPriorityChild;
    switch (getNumberOfChildren(index)) {
      case 0:
        return;
      case 1:
        highestPriorityChild = getLeftChildIndex(index);
        break;
      case 2:
        int leftChild = getLeftChildIndex(index);
        int rightChild = getRightChildIndex(index);
        highestPriorityChild = hasPriority(leftChild, rightChild) ? leftChild : rightChild;
        break;
      default:
        throw new IllegalStateException("node must have 0, 1, or 2 children");
    }

    if (hasPriority(highestPriorityChild, index)) {
      swap(highestPriorityChild, index);
      sink(highestPriorityChild);
    }
  }

  private int getNumberOfChildren(int index) {
    int leftChildIndex = getLeftChildIndex(index);
    int lastIndex = size() - 1;
    return Integer.signum(lastIndex - leftChildIndex) + 1;
  }

  private int getLeftChildIndex(int index) {
    return index * 2 + 1;
  }

  private int getRightChildIndex(int index) {
    return getLeftChildIndex(index) + 1;
  }

  private T peek() {
    return values.get(0);
  }

  public T pop() {
    T head = peek();
    delete(0);
    return head;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public Iterator<T> iterator() {
    return new HeapIterator<>(this);
  }
}
