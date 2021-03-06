package com.vigliatore.adt.graph;

import com.vigliatore.adt.graph.validator.EdgeValidator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WeightedGraphImpl implements WeightedGraph {

  private final Edges edges;
  private final List<EdgeValidator> validators;
  private final int defaultWeight;
  private int vertices;

  WeightedGraphImpl(int vertices, int defaultWeight, EdgeValidator... validators) {
    this.vertices = vertices;
    this.defaultWeight = defaultWeight;
    this.edges = new Edges();
    this.validators = Collections.unmodifiableList(Arrays.asList(validators));
  }

  @Override
  public void addDirectedEdge(Edge edge, int weight) {
    validate(edge, weight);
    vertices = Arrays.asList(edge.from, edge.to, vertices).stream().max(Comparator.naturalOrder()).get();
    edges.add(edge, weight);
  }

  @Override
  public void addUndirectedEdge(Edge edge, int weight) {
    addDirectedEdge(edge, weight);
    addDirectedEdge(edge.reverse(), weight);
  }

  private void validate(Edge edge, int weight) {
    boolean valid = validators.stream().allMatch(validator -> validator.isValid(this, edge, weight));
    if (!valid) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public boolean contains(int vertex) {
    return vertex > 0 && vertex <= vertices;
  }

  @Override
  public boolean contains(Edge edge) {
    return edges.contains(edge);
  }

  @Override
  public int vertices() {
    return vertices;
  }

  @Override
  public Set<Integer> getAdjacentVertices(int vertex) {
    return edges.getAdjacentVertices(vertex);
  }

  @Override
  public Collection<Integer> getWeights(Edge edge) {
    return edges.getWeights(edge);
  }

  @Override
  public Optional<Integer> getMinWeight(Edge edge) {
    return edges.getMinWeight(edge);
  }

  @Override
  public int size() {
    return edges.size();
  }

  @Override
  public int getWeight() {
    return edges.weight();
  }

  @Override
  public Collection<EdgeWeight> edges() {
    return edges.getAllEdges();
  }
}
