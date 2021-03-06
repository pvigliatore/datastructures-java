package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedGraph;

public class AllowParallelEdges implements EdgeValidator {

  private final boolean allowParallelEdges;

  public AllowParallelEdges(boolean allowParallelEdges) {
    this.allowParallelEdges = allowParallelEdges;
  }

  @Override
  public boolean isValid(WeightedGraph graph, Edge edge, int weight) {
    return allowParallelEdges || !graph.contains(edge);
  }

}
