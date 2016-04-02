package com.vigliatore.adt.graph;

public class Edge {

  public final int from;
  public final int to;

  public static Edge instance(int from, int to) {
    return new Edge(from, to);
  }

  protected Edge(int from, int to) {
    this.from = from;
    this.to = to;
  }

  public Edge reverse() {
    return new Edge(to, from);
  }

  public boolean isLoop() {
    return from == to;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Edge edge = (Edge) o;

    return from == edge.from
        && to == edge.to;
  }

  @Override
  public int hashCode() {
    int result = from;
    result = 31 * result + to;
    return result;
  }
}
