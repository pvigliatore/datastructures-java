package com.vigliatore.adt.graph.search;

class PathWeight {

  private final int to;
  private final int weight;

  public static PathWeight get(int to, int weight) {
    return new PathWeight(to, weight);
  }

  private PathWeight(int to, int weight) {
    this.to = to;
    this.weight = weight;
  }

  public int vertex() {
    return to;
  }

  public int weight() {
    return weight;
  }

}
