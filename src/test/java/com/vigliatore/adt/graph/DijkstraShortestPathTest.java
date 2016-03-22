package com.vigliatore.adt.graph;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DijkstraShortestPathTest {

  @Test @Ignore
  public void testShortestPath() {
    // given
    WeightedDigraph graph = new WeightedDigraph(4);
    graph.add(Edge.get(1, 2), 24);
    graph.add(Edge.get(1, 4), 20);
    graph.add(Edge.get(3, 1), 3);
    graph.add(Edge.get(4, 3), 12);

    // when
    Map<Integer, Integer> paths = new DijkstraShortestPath(graph, 1).solve();

    List<Integer> results = new ArrayList<>();

    // then
    for (int i = 1; i <= graph.size(); i++) {
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
    assertEquals("24 3 25", output);
  }

}
