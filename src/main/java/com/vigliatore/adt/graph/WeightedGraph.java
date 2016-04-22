package com.vigliatore.adt.graph;

import java.util.Collection;
import java.util.Optional;

public interface WeightedGraph extends Graph {

  void addDirectedEdge(Edge edge, int weight);

  void addUndirectedEdge(Edge edge, int weight);

  Collection<Integer> getWeights(Edge edge);

  Optional<Integer> getMinWeight(Edge edge);

  int getWeight();

  Collection<EdgeWeight> edges();

}
