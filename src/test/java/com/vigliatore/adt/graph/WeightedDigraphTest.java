package com.vigliatore.adt.graph;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class WeightedDigraphTest {

  private WeightedDigraph graph;

  private void createGraph(int vertices) {
    graph = new WeightedDigraph(vertices);
  }

  @Test
  public void emptyGraphHasZeroSize() {
    createGraph(0);
    assertEquals(0, graph.size());
  }

  @Test
  public void singleEdgeGraphHasSizeOne() {
    createGraph(2);
    addEdge(1, 2, 0);
    assertEquals(1, graph.size());
  }

  @Test
  public void graphWeightsAreAppliedToOneDirection() {
    Edge edge = Edge.get(1, 2);
    int weight = 10;

    createGraph(4);
    graph.add(edge, weight);
    graph.add(edge.reverse(), 0);
    assertEquals(weight, graph.get(edge));
    assertEquals(0, graph.get(edge.reverse()));
  }

  @Test
  public void getNeighborsHasCorrectWeights() {
    // given
    Edge edge = Edge.get(1, 2);
    int weight = 10;

    createGraph(4);
    graph.add(edge, weight);

    // when
    Map<Integer, Integer> neighbors = graph.get(1);

    // then
    assertEquals(weight, neighbors.get(2).intValue());
  }

  @Test
  public void nonExistantNodeHasNoNeighbors() {
    createGraph(1);
    Map<Integer, Integer> neighbors = graph.get(0);
    assertTrue(neighbors.isEmpty());
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

    Map<Integer, Integer> neighbors = graph.get(1);
    Set<Integer> actualNeighborIds = neighbors.keySet();
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

  @Test (expected = IllegalArgumentException.class)
  public void graphEdgeReferencesInvalidVertex() {
    createGraph(2);
    addEdge(1, 3, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void addEdgeToZeroSizeGraph() {
    createGraph(0);
    addEdge(0, 0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void edgesMustHaveUniqueVertices() {
    createGraph(2);
    addEdge(1, 1, 10);
  }

  private void addEdge(int from, int to, int weight) {
    graph.add(Edge.get(from, to), weight);
  }

  @Test (expected = IllegalArgumentException.class)
  public void disallowNegativeWeightEdge() {
    createGraph(2);
    addEdge(1, 2, -1);
  }

}
