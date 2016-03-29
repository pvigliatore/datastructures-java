package com.vigliatore.adt.graph;

import java.util.Collection;
import java.util.Optional;

public interface WeightedGraph extends Graph {

  void add(Edge edge, int weight);

  Collection<Integer> getWeights(Edge edge);

  Optional<Integer> getMinWeight(Edge edge);

}
