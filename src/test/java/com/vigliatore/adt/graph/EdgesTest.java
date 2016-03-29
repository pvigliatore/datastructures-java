package com.vigliatore.adt.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EdgesTest {

  private Edges edges;

  @Before
  public void setup() {
    edges = new Edges();
    assertTrue(edges.isEmpty());
  }

  @Test
  public void countParallelEdgesAsDistinctEntries() {
    Edge edge1 = Edge.instance(1, 2);
    Edge edge2 = Edge.instance(1, 2);

    edges.add(edge1, 1);
    edges.add(edge2, 2);

    assertEquals(2, edges.size());
  }

  @Test
  public void getWeightsWithUndefinedEdge() {
    Collection<Integer> weights = edges.getWeights(Edge.instance(100, 1000));
    assertTrue(weights.isEmpty());
  }

  @Test
  public void getMininimumWeight() {
    Edge edge = Edge.instance(1, 2);
    edges.add(edge, 100);
    edges.add(edge, 101);
    edges.add(Edge.instance(1, 3), 1);

    assertEquals(100, edges.getMinWeight(edge).get().intValue());
  }

  @Test
  public void getMinimimumWeightWithNoEdges() {
    boolean hasMinEdge = edges.getMinWeight(Edge.instance(1, 2)).isPresent();
    assertFalse(hasMinEdge);
  }

  @Test
  public void doNotFailWhenThereAreNoAdjacentVertices() {
    assertTrue(edges.getAdjacentVertices(0).isEmpty());
  }

}
