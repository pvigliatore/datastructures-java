package com.vigliatore.adt.graph.search;

import com.vigliatore.adt.UnionFind;
import com.vigliatore.adt.graph.EdgeWeight;
import com.vigliatore.adt.graph.GraphBuilder;
import com.vigliatore.adt.graph.WeightedGraph;
import com.vigliatore.adt.heap.Heap;
import com.vigliatore.adt.heap.SimpleHeap;

public class KruskalsMinimumSpanningTree implements MinimumSpanningTree {

  private final WeightedGraph graph;
  private final WeightedGraph minimumSpanningTree;
  private final Heap<EdgeWeight> queue;
  private final UnionFind components;

  public KruskalsMinimumSpanningTree(WeightedGraph graph) {
    this.graph = graph;
    minimumSpanningTree = new GraphBuilder()
        .setSize(graph.vertices())
        .disallowLoops()
        .disallowNegativeWeightEdges()
        .disallowParallelEdges()
        .build();
    queue = SimpleHeap.instance(EdgeWeight.minComparator());
    components = new UnionFind(graph.vertices());

    prioritizeEdges(graph);
  }

  private void prioritizeEdges(WeightedGraph graph) {
    graph.edges().forEach(queue::add);
  }

  @Override
  public void solve() {
    queue.forEach(edgeWeight -> {
      int from = edgeWeight.from();
      int to = edgeWeight.to();
      if (!isSameComponent(from, to)) {
        components.union(from, to);
        minimumSpanningTree.addDirectedEdge(edgeWeight.edge(), edgeWeight.weight());
      }
    });
  }

  private boolean isSameComponent(int from, int to) {
    return getComponent(from) == getComponent(to);
  }

  private int getComponent(int vertex) {
    return components.find(vertex);
  }

  @Override
  public WeightedGraph getMinimumSpanningTree() {
    return minimumSpanningTree;
  }
}
