package com.vigliatore.adt.graph;

import com.vigliatore.adt.graph.search.DijkstraShortestPath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class DijkstraShortestPathTest {

  @Test
  public void testShortestPath() {
    // given
    WeightedGraph graph = new GraphBuilder()
        .setSize(4)
        .build();
    addEdge(graph, Edge.instance(1, 2), 24);
    addEdge(graph, Edge.instance(1, 4), 20);
    addEdge(graph, Edge.instance(3, 1), 3);
    addEdge(graph, Edge.instance(4, 3), 12);

    // when
    List<Integer> results = solve(graph, 1);

    assertEquals(Arrays.asList(0, 24, 3, 15), results);
  }

  private List<Integer> solve(WeightedGraph graph, int start) {
    Map<Integer, Integer> paths = new DijkstraShortestPath(graph, start).solve();
    List<Integer> results = new ArrayList<>();

    // then
    for (int i = 1; i <= graph.vertices(); i++) {
      int distance = Optional.ofNullable(paths.get(i)).orElse(-1);
      results.add(distance);
    }
    return results;
  }

  private void addEdge(WeightedGraph graph, Edge edge, int weight) {
    graph.add(edge, weight);
    graph.add(edge.reverse(), weight);
  }

  private void addParallelEdges(WeightedGraph graph, Edge edge, int... weights) {
    IntStream.of(weights).forEach(weight -> {
      graph.add(edge, weight);
      graph.add(edge.reverse(), weight);
    });
  }

  @Test
  public void evaluateShortestPathWithParallelEdges() {
    WeightedGraph graph = createGraph(3);

    addParallelEdges(graph, Edge.instance(1, 2), 1, 2);
    addParallelEdges(graph, Edge.instance(2, 3), 3, 1, 4);

    List<Integer> results = solve(graph, 1);
    results.remove(0);

    assertEquals(Arrays.asList(1, 2), results);
  }

  @Test
  public void edgesDefinedOutOfSequence() {
    WeightedGraph graph = createGraph(3);

    addParallelEdges(graph, Edge.instance(1, 2), 1, 2);
    addEdge(graph, Edge.instance(2, 3), 3);
    addEdge(graph, Edge.instance(3, 2), 1);
    addEdge(graph, Edge.instance(3, 2), 4);

    List<Integer> results = solve(graph, 1);
    assertEquals(Arrays.asList(0, 1, 2), results);
  }

  @Test
  public void allNodesAreUnreachable() {
    WeightedGraph graph = createGraph(3);
    List<Integer> results = solve(graph, 1);
    assertEquals(Arrays.asList(0, -1, -1), results);
  }

  @Test
  public void shortestPathOfDisconnectedGraph() {
    WeightedGraph graph = createGraph(4);
    graph.add(Edge.instance(1, 2), 1);
    graph.add(Edge.instance(3, 4), 1);

    List<Integer> results = solve(graph, 1);
    assertEquals(Arrays.asList(0, 1, -1, -1), results);
  }

  private WeightedGraph createGraph(int size) {
    return new GraphBuilder()
          .setSize(size)
          .allowParallelEdges()
          .build();
  }

}
