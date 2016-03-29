package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedDigraph;

public class AllowLoops implements EdgeValidator {

  private final boolean allowLoops;

  public AllowLoops(boolean allowLoops) {
    this.allowLoops = allowLoops;
  }

  @Override
  public boolean isValid(WeightedDigraph graph, Edge edge, int weight) {
    return allowLoops || !edge.isLoop();
  }
}
