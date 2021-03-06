package com.vigliatore.adt.graph;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WeightedGraphTest {

  private WeightedGraph graph;

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
    Edge edge = Edge.instance(1, 2);
    int weight = 10;

    createGraph(4);
    graph.addDirectedEdge(edge, weight);
    graph.addDirectedEdge(edge.reverse(), -weight);
    assertEquals(weight, graph.getMinWeight(edge).get().intValue());
    assertEquals(-weight, graph.getMinWeight(edge.reverse()).get().intValue());
  }

  @Test
  public void getAdjacentVertices() {
    int weight = 10;
    createGraph(4);

    graph.addDirectedEdge(Edge.instance(1, 2), weight);
    graph.addDirectedEdge(Edge.instance(3, 2), weight);

    verifyContents(graph.getAdjacentVertices(1), Collections.singletonList(2));
    assertTrue(graph.getAdjacentVertices(2).isEmpty());
  }

  public void verifyContents(Collection<Integer> neighbors, Collection<Integer> expected) {
    assertEquals(expected.size(), neighbors.size());
    assertTrue(neighbors.containsAll(expected));
  }

  @Test
  public void getAllNeighbors() {
    createGraph(4);
    Edge edge1 = Edge.instance(1, 2);
    Edge edge2 = Edge.instance(1, 3);
    Edge edge3 = Edge.instance(2, 4);
    graph.addDirectedEdge(edge1, 1);
    graph.addDirectedEdge(edge2, 2);
    graph.addDirectedEdge(edge3, 3);

    Set<Integer> actualNeighborIds = graph.getAdjacentVertices(1);
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

  @Test
  public void totalGraphWeight() {
    createGraph(4);
    addEdge(1, 2, 2);
    addEdge(2, 1, 2);
    addEdge(2, 3, 3);
    addEdge(4, 1, 1);

    int expectedWeight = 8;
    assertEquals(expectedWeight, graph.getWeight());
  }

  private void addEdge(int from, int to, int weight) {
    graph.addDirectedEdge(Edge.instance(from, to), weight);
  }

}
