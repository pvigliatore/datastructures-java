package com.vigliatore.adt.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

  @Test
  public void getAllEdges() {
    List<Edge> allEdges = Arrays.asList(
        Edge.instance(1, 2),
        Edge.instance(2, 3),
        Edge.instance(2, 4),
        Edge.instance(2, 1)
    );

    Collection<EdgeWeight> edgeWeights = allEdges
        .stream()
        .map(edge -> EdgeWeight.instance(edge, edge.to))
        .collect(Collectors.toList());

    edgeWeights.stream()
        .forEach(edgeWeight -> edges.add(edgeWeight.edge(), edgeWeight.to()));

    Collection<EdgeWeight> actualEdgeWeights = edges.getAllEdges();
    assertEquals(edgeWeights.size(), actualEdgeWeights.size());
    assertTrue(edgeWeights.containsAll(actualEdgeWeights));
  }

}
