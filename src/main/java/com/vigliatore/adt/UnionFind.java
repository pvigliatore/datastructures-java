package com.vigliatore.adt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UnionFind {

  private final List<Integer> parents;

  public UnionFind(int size) {
    this.parents = new ArrayList<>(size + 1);
    IntStream.range(0, size + 1).forEach(i -> parents.add(-1));
  }

  public int find(int index) {
    if (index <= 0 || index >= parents.size()) {
      System.out.println(index);
      throw new IllegalArgumentException();
    }

    int parent = parents.get(index);
    return parent == -1 ? index : find(parent);
  }

  public void union(int left, int right) {
    int leftRoot = find(left);
    int rightRoot = find(right);
    if (leftRoot != rightRoot) {
      parents.set(leftRoot, rightRoot);
    }
  }

}
