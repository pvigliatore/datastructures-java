package com.vigliatore.adt.heap;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleIndexedHeap<K, V> implements IndexedHeap<K, V> {

  public static <K, V extends Comparable<V>> IndexedHeap<K, V> minHeap() {
    return new SimpleIndexedHeap<K, V>(Comparator.<V> naturalOrder());
  }

  public static <K, V extends Comparable<V>> IndexedHeap<K, V> maxHeap() {
    return new SimpleIndexedHeap<K, V>(Comparator.<V> naturalOrder().reversed());
  }

  public static <K, V> IndexedHeap<K, V> newInstance(Comparator<V> comparator) {
    return new SimpleIndexedHeap<K, V>(comparator);
  }

  private final List<V> values;

  private final Comparator<V> comparator;

  private final List<K> ids;
  private final Map<K, Integer> indexById;

  private SimpleIndexedHeap(Comparator<V> comparator) {
    this.values = new ArrayList<V>();
    this.comparator = comparator;

    this.ids = new ArrayList<>();
    this.indexById = new HashMap<>();
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public V pop() {
    return null;
  }

  @Override
  public void add(K key, V value) {
    if (indexById.containsKey(key)) {
      throw new IllegalArgumentException();
    }

    ids.add(key);
    indexById.put(key, ids.size() - 1);
  }

  private void swapIdPosition(int index1, int index2) {
    K fromId = ids.get(index1);
    K toId = ids.get(index2);
    int fromIndex = indexById.get(fromId);
    indexById.put(fromId, indexById.get(toId));
    indexById.put(toId, fromIndex);
  }

  @Override
  public void delete(K key) {
    if (!indexById.containsKey(key)) {
      throw new IllegalArgumentException();
    }

    int index = indexById.remove(key);
    ids.remove(index);
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

  private int getParentIndex(int index) {
    return (index - 1) / 2;
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

  public int size() {
    return values.size();
  }

  private int getLeftChildIndex(int index) {
    return index * 2 + 1;
  }

  private int getRightChildIndex(int index) {
    return getLeftChildIndex(index) + 1;
  }

  private void swap(int from, int to) {
    V temp = get(from);
    values.set(from, get(to));
    values.set(to, temp);

    Collections.swap(ids, from, to);
    swapIdPosition(from, to);
  }

  private V get(int index) {
    return values.get(index);
  }

}
