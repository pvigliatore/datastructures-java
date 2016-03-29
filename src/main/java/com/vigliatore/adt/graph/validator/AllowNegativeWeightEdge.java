package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedGraph;

public class AllowNegativeWeightEdge implements EdgeValidator {

  private final boolean allowNegateWeight;

  public AllowNegativeWeightEdge(boolean allowNegateWeight) {
    this.allowNegateWeight = allowNegateWeight;
  }

  @Override
  public boolean isValid(WeightedGraph graph, Edge edge, int weight) {
    return allowNegateWeight || weight >= 0;
  }

}
