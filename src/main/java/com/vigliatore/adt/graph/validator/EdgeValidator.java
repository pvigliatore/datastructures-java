package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedGraph;

@FunctionalInterface
public interface EdgeValidator {

  boolean isValid(WeightedGraph graph, Edge edge, int weight);

}
