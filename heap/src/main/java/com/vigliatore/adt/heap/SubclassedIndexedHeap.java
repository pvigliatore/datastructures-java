package com.vigliatore.adt.heap;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubclassedIndexedHeap<K, V extends Comparable<V>> extends SimpleHeap<V> implements IndexedHeap<K,V> {

  private List<K> ids = new ArrayList<>();
  private Map<K, Integer> indexById = new HashMap<>();

  @Override
  public boolean addAll(Collection<? extends V> items) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean add(V value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(K key, V value) {
    if (indexById.containsKey(key)) {
      throw new IllegalArgumentException();
    }

    ids.add(key);
    indexById.put(key, ids.size() - 1);
    super.add(value);
  }

  @Override
  protected void swap(int index1, int index2) {
    super.swap(index1, index2);

    Collections.swap(ids, index1, index2);
    swapIdPosition(index1, index2);
  }

  private void swapIdPosition(int index1, int index2) {
    K fromId = ids.get(index1);
    K toId = ids.get(index2);
    int fromIndex = indexById.get(fromId);
    indexById.put(fromId, indexById.get(toId));
    indexById.put(toId, fromIndex);
  }

  @Override
  protected void delete(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteKey(K key) {
    if (!indexById.containsKey(key)) {
      throw new IllegalArgumentException();
    }

    int index = indexById.remove(key);
    ids.remove(index);
    super.delete(index);
  }

}
