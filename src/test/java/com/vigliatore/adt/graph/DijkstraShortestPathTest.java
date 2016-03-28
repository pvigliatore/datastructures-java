package com.vigliatore.adt.graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DijkstraShortestPathTest {

  @Test
  public void testShortestPath() {
    // given
    WeightedDigraph graph = new WeightedDigraph(4);
    addEdge(graph, Edge.get(1, 2), 24);
    addEdge(graph, Edge.get(1, 4), 20);
    addEdge(graph, Edge.get(3, 1), 3);
    addEdge(graph, Edge.get(4, 3), 12);

    // when
    Map<Integer, Integer> paths = new DijkstraShortestPath(graph, 1).solve();

    List<Integer> results = new ArrayList<>();

    // then
    for (int i = 1; i <= graph.vertices(); i++) {
      if (i == 1) {
        continue;
      }

      int distance = paths.get(i);
      if (distance == Integer.MAX_VALUE) {
        distance = -1;
      }

      results.add(distance);
    }

    String output = results.stream().map(i -> Integer.toString(i)).collect(Collectors.joining(" "));
    assertEquals("24 3 15", output);
  }

  public void addEdge(WeightedDigraph graph, Edge edge, int weight) {
    graph.add(edge, weight);
    graph.add(edge.reverse(), weight);
  }

}
