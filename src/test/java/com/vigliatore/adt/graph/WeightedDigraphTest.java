package com.vigliatore.adt.graph;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WeightedDigraphTest {

  private WeightedDigraph graph;

  private void createGraph(int vertices) {
    graph = new GraphBuilder()
        .setSize(vertices)
        .allowNegativeWeightEdges()
        .build();
  }

  @Test
  public void emptyGraphHasZeroSize() {
    createGraph(0);
    assertEquals(0, graph.size());
  }

  @Test
  public void singleEdgeGraphHasSizeOne() {
    createGraph(100);
    addEdge(1, 2, 0);
    assertEquals(1, graph.size());
  }

  @Test
  public void graphWeightsAreAppliedToOneDirection() {
    Edge edge = Edge.get(1, 2);
    int weight = 10;

    createGraph(4);
    graph.add(edge, weight);
    graph.add(edge.reverse(), -weight);
    assertEquals(weight, graph.getMinWeight(edge).get().intValue());
    assertEquals(-weight, graph.getMinWeight(edge.reverse()).get().intValue());
  }

  @Test
  public void getAdjacentVertices() {
    int weight = 10;
    createGraph(4);

    graph.add(Edge.get(1, 2), weight);
    graph.add(Edge.get(3, 2), weight);

    verifyContents(graph.getAdjecentVertices(1), Collections.singletonList(2));
    assertTrue(graph.getAdjecentVertices(2).isEmpty());
  }

  public void verifyContents(Collection<Integer> neighbors, Collection<Integer> expected) {
    assertEquals(expected.size(), neighbors.size());
    assertTrue(neighbors.containsAll(expected));
  }

  @Test
  public void getAllNeighbors() {
    createGraph(4);
    Edge edge1 = Edge.get(1, 2);
    Edge edge2 = Edge.get(1, 3);
    Edge edge3 = Edge.get(2, 4);
    graph.add(edge1, 1);
    graph.add(edge2, 2);
    graph.add(edge3, 3);

    Set<Integer> actualNeighborIds = graph.getAdjecentVertices(1);
    Collection<Integer> expectedNeighborIds = Arrays.asList(2, 3);

    assertEquals(actualNeighborIds.size(), expectedNeighborIds.size());
    assertTrue(actualNeighborIds.containsAll(expectedNeighborIds));
  }

  @Test
  public void verticesAre1Indexed() {
    createGraph(2);
    assertTrue(graph.contains(1));
    assertTrue(graph.contains(2));
    assertFalse(graph.contains(3));
  }

  @Test
  public void zeroIsAnInvalidIndex() {
    createGraph(2);
    assertFalse(graph.contains(0));
  }

  @Test
  public void outOfBoundsIndexesAreInvalid() {
    createGraph(2);
    assertFalse(graph.contains(3));
  }

  @Test
  public void graphWithVerticesMayHaveZeroEdges() {
    createGraph(2);
    assertEquals(2, graph.vertices());
    assertEquals(0, graph.size());
  }

  private void addEdge(int from, int to, int weight) {
    graph.add(Edge.get(from, to), weight);
  }

}
