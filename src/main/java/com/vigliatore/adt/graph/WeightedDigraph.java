package com.vigliatore.adt.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class WeightedDigraph {

  private final int vertices;
  private final Map<Integer, Set<Integer>> edges;
  private final Map<Edge, Integer> weights;

  public WeightedDigraph(int vertices) {
    this.vertices = vertices;
    this.edges = new HashMap<>();
    this.weights = new HashMap<>();
  }

  @Deprecated
  public void add(int from, int to, int weight) {
    Edge edge = Edge.get(from, to);
    add(edge, weight);
  }

  public void add(Edge edge, int weight) {
    if (edge.isLoop()
        || !contains(edge.to)
        || !contains(edge.from)
        || weight < 0) {
      throw new IllegalArgumentException();
    }
    addEdge(edge, weight);
  }

  private void addEdge(Edge edge, int weight) {
    edges.putIfAbsent(edge.from, new HashSet<>());
    edges.get(edge.from).add(edge.to);
    weights.put(edge, weight);
  }

  public boolean contains(int vertex) {
    return vertex > 0 && vertex <= vertices;
  }

  public boolean contains(Edge edge) {
    return weights.containsKey(edge);
  }

  public Map<Integer, Integer> get(int node) {
    return edges.getOrDefault(node, Collections.<Integer> emptySet())
        .stream()
        .collect(Collectors.toMap(
            UnaryOperator.identity(),
            x -> get(Edge.get(node, x))
        ));
  }

  public int get(Edge edge) {
    return weights.get(edge);
  }

  public int vertices() {
    return vertices;
  }

  public int size() {
    return edges.keySet().size();
  }

}
