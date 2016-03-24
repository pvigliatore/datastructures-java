package com.vigliatore.adt.graph;

import com.vigliatore.adt.graph.validator.EdgeValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class WeightedDigraph {

  private final Map<Integer, Set<Integer>> edges;
  private final Map<Edge, Integer> weights;
  private final List<EdgeValidator> validators;
  private int vertices;

  public WeightedDigraph(int vertices, EdgeValidator... validators) {
    this.vertices = vertices;
    this.edges = new HashMap<>();
    this.weights = new HashMap<>();
    this.validators = Collections.unmodifiableList(Arrays.asList(validators));
  }

  @Deprecated
  public void add(int from, int to, int weight) {
    Edge edge = Edge.get(from, to);
    add(edge, weight);
  }

  public void add(Edge edge, int weight) {
    addEdge(edge, weight);
  }

  private void validate(Edge edge, int weight) {
    boolean valid = validators.stream().allMatch(validator -> validator.isValid(this, edge, weight));
    if (!valid) {
      throw new IllegalArgumentException();
    }
  }

  private void addEdge(Edge edge, int weight) {
    validate(edge, weight);
    vertices = Arrays.asList(edge.from, edge.to, vertices).stream().max(Comparator.naturalOrder()).get();
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
