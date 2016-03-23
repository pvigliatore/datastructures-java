package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedDigraph;

@FunctionalInterface
public interface EdgeValidator {

  boolean isValid(WeightedDigraph graph, Edge edge, int weight);

}
