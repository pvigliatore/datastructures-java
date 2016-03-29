package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.GraphBuilder;
import com.vigliatore.adt.graph.WeightedGraph;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AllowOutOfBoundsEdgeTest {

  private WeightedGraph graph;
  private EdgeValidator validator;
  private Edge edge;

  @Before
  public void setup() {
    edge = Edge.instance(1, 10);
  }

  @Test
  public void verticesAreBounded() {
    int size = 1;
    graph = buildGraph(size);
    validator = new AllowOutOfBoundsEdge(false);
    boolean valid = validator.isValid(graph, edge, 100);
    assertFalse(valid);
  }

  public WeightedGraph buildGraph(int size) {
    return new GraphBuilder().setSize(size).build();
  }

  @Test
  public void verticesAreUnbounded() {
    graph = buildGraph(1);
    validator = new AllowOutOfBoundsEdge(true);
    boolean valid = validator.isValid(graph, edge, 100);
    assertTrue(valid);
  }

}
