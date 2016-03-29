package com.vigliatore.adt.graph;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface WeightedGraph {
  void add(Edge edge, int weight);

  boolean contains(int vertex);

  boolean contains(Edge edge);

  int vertices();

  Set<Integer> getAdjecentVertices(int vertex);

  Collection<Integer> getWeights(Edge edge);

  Optional<Integer> getMinWeight(Edge edge);

  int size();
}
