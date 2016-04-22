package com.vigliatore.adt.graph.search;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.GraphBuilder;
import com.vigliatore.adt.graph.WeightedGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;

public class KruskalTestHarness {

  private final WeightedGraph graph;

  public KruskalTestHarness(InputStream in) throws IOException {

    final int N;
    final int M;
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    try {
      String[] meta = reader.readLine().split(" ");
      N = Integer.parseInt(meta[0]);
      M = Integer.parseInt(meta[1]);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    graph = new GraphBuilder()
        .setSize(N)
        .disallowNegativeWeightEdges()
        .allowLoops()
        .allowParallelEdges()
        .build();
    for (int i = 0; i < M; i++) {
      Iterator<Integer> edgeTokens = readTokens(reader);
      int from = edgeTokens.next();
      int to = edgeTokens.next();
      int weight = edgeTokens.next();
      graph.addUndirectedEdge(Edge.instance(from, to), weight);
    }
  }

  private Iterator<Integer> readTokens(BufferedReader reader) {
    try {
      return Arrays.asList(reader.readLine().split(" "))
          .stream()
          .map(Integer::parseInt)
          .iterator();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  public int solve() {
    KruskalsMinimumSpanningTree mst = new KruskalsMinimumSpanningTree(graph);
    mst.solve();
    return mst.getMinimumSpanningTree().getWeight();
  }

}
