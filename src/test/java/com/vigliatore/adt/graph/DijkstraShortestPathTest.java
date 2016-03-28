package com.vigliatore.adt.graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class DijkstraShortestPathTest {

  @Test
  public void testShortestPath() {
    // given
    WeightedDigraph graph = new GraphBuilder()
        .setSize(4)
        .build();
    addEdge(graph, Edge.get(1, 2), 24);
    addEdge(graph, Edge.get(1, 4), 20);
    addEdge(graph, Edge.get(3, 1), 3);
    addEdge(graph, Edge.get(4, 3), 12);

    // when
    List<Integer> results = solve(graph);
    results.remove(0);

    assertEquals(Arrays.asList(24, 3, 15), results);

//    String output = results.stream().map(i -> Integer.toString(i)).collect(Collectors.joining(" "));
//    assertEquals("24 3 15", output);
  }

  private List<Integer> solve(WeightedDigraph graph) {
    Map<Integer, Integer> paths = new DijkstraShortestPath(graph, 1).solve();
    List<Integer> results = new ArrayList<>();

    // then
    for (int i = 1; i <= graph.vertices(); i++) {
      int distance = paths.get(i);
      if (distance == Integer.MAX_VALUE) {
        distance = -1;
      }

      results.add(distance);
    }
    return results;
  }

  private void addEdge(WeightedDigraph graph, Edge edge, int weight) {
    graph.add(edge, weight);
    graph.add(edge.reverse(), weight);
  }

  private void addParallelEdges(WeightedDigraph graph, Edge edge, int... weights) {
    IntStream.of(weights).forEach(weight -> {
      graph.add(edge, weight);
      graph.add(edge.reverse(), weight);
    });
  }

  @Test
  public void evaluateShortestPathWithParallelEdges() {
    WeightedDigraph graph = new GraphBuilder()
        .setSize(3)
        .allowParallelEdges()
        .build();

    addParallelEdges(graph, Edge.get(1, 2), 1, 2);
    addParallelEdges(graph, Edge.get(2, 3), 1, 2);

    List<Integer> results = solve(graph);
    results.remove(0);

    assertEquals(Arrays.asList(1, 2), results);
  }

}
