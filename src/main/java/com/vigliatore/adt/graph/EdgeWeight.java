package com.vigliatore.adt.graph;

import java.util.Comparator;

public class EdgeWeight {

  private final Edge edge;
  private final int weight;

  public static EdgeWeight instance(int from, int to, int weight) {
    Edge edge = Edge.instance(from, to);
    return new EdgeWeight(edge, weight);
  }

  public static EdgeWeight instance(Edge edge, int weight) {
    return new EdgeWeight(edge, weight);
  }

  public static Comparator<EdgeWeight> minComparator() {
    return (o1, o2) -> Integer.compare(o1.weight(), o2.weight());
  }

  private EdgeWeight(Edge edge, int weight) {
    this.edge = edge;
    this.weight = weight;
  }

  public Edge edge() {
    return edge;
  }

  private int from() {
    return edge.from;
  }

  private int to() {
    return edge.to;
  }

  public int weight() {
    return weight;
  }

}
