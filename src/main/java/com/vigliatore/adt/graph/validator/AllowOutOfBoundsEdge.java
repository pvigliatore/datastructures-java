package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedGraph;

public class AllowOutOfBoundsEdge implements EdgeValidator {

  private final boolean allowOutOfBoundsEdge;

  public AllowOutOfBoundsEdge(boolean allowOutOfBoundsEdge) {
    this.allowOutOfBoundsEdge = allowOutOfBoundsEdge;
  }

  @Override
  public boolean isValid(WeightedGraph graph, Edge edge, int weight) {
    return allowOutOfBoundsEdge || (graph.contains(edge.to) && graph.contains(edge.from));
  }

}
