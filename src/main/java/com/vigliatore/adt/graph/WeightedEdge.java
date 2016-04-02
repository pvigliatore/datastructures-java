package com.vigliatore.adt.graph;

import java.util.Comparator;

public class WeightedEdge extends Edge {

  private final int weight;

  public static WeightedEdge instance(int from, int to, int weight) {
    return new WeightedEdge(from, to, weight);
  }

  public static Comparator<WeightedEdge> minComparator() {
    return (o1, o2) -> Integer.compare(o1.weight(), o2.weight());
  }

  private WeightedEdge(int from, int to, int weight) {
    super(from, to);
    this.weight = weight;
  }

  public int weight() {
    return weight;
  }

}
