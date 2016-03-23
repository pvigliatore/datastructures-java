package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedDigraph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AllowOutOfBoundsEdgeTest {

  private WeightedDigraph graph;
  private EdgeValidator validator;
  private Edge edge;

  @Before
  public void setup() {
    edge = Edge.get(1, 10);
  }

  @Test
  public void verticesAreBounded() {
    graph = new WeightedDigraph(1);
    validator = new AllowOutOfBoundsEdge(false);
    boolean valid = validator.isValid(graph, edge, 100);
    assertFalse(valid);
  }

  @Test
  public void verticesAreUnbounded() {
    graph = new WeightedDigraph(1);
    validator = new AllowOutOfBoundsEdge(true);
    boolean valid = validator.isValid(graph, edge, 100);
    assertTrue(valid);
  }

}
