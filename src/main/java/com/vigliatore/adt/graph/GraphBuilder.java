package com.vigliatore.adt.graph;

import com.vigliatore.adt.graph.validator.AllowLoops;
import com.vigliatore.adt.graph.validator.AllowNegativeWeightEdge;
import com.vigliatore.adt.graph.validator.AllowOutOfBoundsEdge;
import com.vigliatore.adt.graph.validator.AllowParallelEdges;

public class GraphBuilder {

  private int size;
  private AllowLoops allowLoops;
  private AllowNegativeWeightEdge allowNegativeWeightedEdge;
  private AllowOutOfBoundsEdge allowOutOfBoundsEdge;
  private AllowParallelEdges allowParallelEdges;

  public GraphBuilder() {
    allowLoops(false);
    allowNegativeWeights(false);
    allowOutOfBoundsEdge = new AllowOutOfBoundsEdge(false);
    allowParallelEdges(false);
  }

  public GraphBuilder setSize(int size) {
    this.size = size;
    return this;
  }

  public GraphBuilder allowLoops() {
    allowLoops(true);
    return this;
  }

  private void allowLoops(boolean allowed) {
    allowLoops = new AllowLoops(allowed);
  }

  public GraphBuilder disallowLoops() {
    allowLoops(false);
    return this;
  }

  public GraphBuilder setGrowable() {
    allowOutOfBoundsEdge = new AllowOutOfBoundsEdge(true);
    return this;
  }

  public WeightedGraph build() {
    return new WeightedDigraph(
        size,
        allowLoops,
        allowNegativeWeightedEdge,
        allowOutOfBoundsEdge,
        allowParallelEdges);
  }

  public GraphBuilder allowNegativeWeightEdges() {
    allowNegativeWeights(true);
    return this;
  }

  public GraphBuilder disallowNegativeWeightEdges() {
    allowNegativeWeights(false);
    return this;
  }

  public GraphBuilder allowParallelEdges() {
    allowParallelEdges(true);
    return this;
  }

  public GraphBuilder disallowParallelEdges() {
    allowParallelEdges(false);
    return this;
  }

  private void allowParallelEdges(boolean allowed) {
    this.allowParallelEdges = new AllowParallelEdges(allowed);
  }

  private void allowNegativeWeights(boolean allowed) {
    allowNegativeWeightedEdge = new AllowNegativeWeightEdge(allowed);
  }

}
