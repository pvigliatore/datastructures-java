package com.vigliatore.adt.graph;

import com.vigliatore.adt.graph.validator.EdgeValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WeightedDigraph {

  private final Edges edges;
  private final List<EdgeValidator> validators;
  private int vertices;

  public WeightedDigraph(int vertices, EdgeValidator... validators) {
    this.vertices = vertices;
    this.edges = new Edges();
    this.validators = Collections.unmodifiableList(Arrays.asList(validators));
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
    edges.add(edge, weight);
  }

  public boolean contains(int vertex) {
    return vertex > 0 && vertex <= vertices;
  }

  public boolean contains(Edge edge) {
    return edges.contains(edge);
  }

  public Map<Integer, Integer> get(int node) {
    return edges.getNeighbors(node);
  }

  public int get(Edge edge) {
    return edges.getWeight(edge);
  }

  public int vertices() {
    return vertices;
  }

  public int size() {
    return edges.size();
  }

}
