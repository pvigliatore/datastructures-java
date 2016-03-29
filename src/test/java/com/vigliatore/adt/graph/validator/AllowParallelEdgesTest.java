package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.GraphBuilder;
import com.vigliatore.adt.graph.WeightedGraph;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AllowParallelEdgesTest {

  private WeightedGraph graph;
  private EdgeValidator validator;

  @Before
  public void setup() {
    graph = new GraphBuilder().setSize(2).build();
    graph.add(Edge.instance(1, 2), 2);
  }

  @Test
  public void allowParallelEdges() {
    createValidator(true);
    boolean valid = validator.isValid(graph, Edge.instance(1, 2), 100);
    assertTrue(valid);
  }

  private void createValidator(boolean allowed) {
    validator = new AllowParallelEdges(allowed);
  }

  @Test
  public void disallowParallelEdges() {
    createValidator(false);
    boolean valid = validator.isValid(graph, Edge.instance(1, 2), 100);
    assertFalse(valid);
  }

}
