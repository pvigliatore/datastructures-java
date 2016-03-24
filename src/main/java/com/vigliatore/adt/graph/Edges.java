package com.vigliatore.adt.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Edges {

  private final Map<Integer, Set<Integer>> neighbors;
  private final Map<Edge, Integer> weights;

  public Edges() {
    this.neighbors = new HashMap<>();
    this.weights = new HashMap<>();
  }

  public int size() {
    return weights.keySet().size();
  }

  public Edge getEdge() {
    return null;
  }

  public int getWeight(Edge edge) {
    return weights.get(edge);
  }

  public Map<Integer, Integer> getNeighbors(int vertex) {
    return neighbors.getOrDefault(vertex, Collections.emptySet())
        .stream()
        .collect(Collectors.toMap(
            UnaryOperator.identity(),
            x -> getWeight(Edge.get(vertex, x))
        ));
  }

  public void add(Edge edge, int weight) {
    neighbors.putIfAbsent(edge.from, new HashSet<>());
    neighbors.get(edge.from).add(edge.to);
    weights.put(edge, weight);
  }

  public boolean contains(Edge edge) {
    return weights.containsKey(edge);
  }

}
