package com.vigliatore.adt.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class Edges {

  private final Map<Integer, Set<Integer>> neighbors;
  private final Map<Edge, List<Integer>> weights;

  public Edges() {
    this.neighbors = new HashMap<>();
    this.weights = new HashMap<>();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public int size() {
    int size = 0;
    for (Collection<Integer> edgeWeights : weights.values()) {
      size += edgeWeights.stream().count();
    }
    return size;
  }

  public Collection<Integer> getWeights(Edge edge) {
    List<Integer> edgeWeights = weights.getOrDefault(edge, Collections.emptyList());
    return Collections.unmodifiableCollection(edgeWeights);
  }

  public Optional<Integer> getMinWeight(Edge edge) {
    return getWeights(edge).stream().min(Comparator.naturalOrder());
  }

  public Set<Integer> getAdjacentVertices(int vertex) {
    return neighbors.getOrDefault(vertex, Collections.emptySet());
  }

  public void add(Edge edge, int weight) {
    neighbors.putIfAbsent(edge.from, new HashSet<>());
    neighbors.get(edge.from).add(edge.to);

    weights.putIfAbsent(edge, new ArrayList<>());
    weights.get(edge).add(weight);
  }

  public boolean contains(Edge edge) {
    return weights.containsKey(edge);
  }

  public int weight() {
    return weights.values()
        .stream()
        .mapToInt(list -> list.stream().mapToInt(x -> (int) x).sum())
        .sum();
  }

  public Collection<EdgeWeight> getAllEdges() {
    return weights.keySet()
        .stream()
        .flatMap(edge -> weights.get(edge)
            .stream()
            .map(weight -> EdgeWeight.instance(edge, weight))
        ).collect(Collectors.toList());
  }

}
