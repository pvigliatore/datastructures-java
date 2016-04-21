package com.vigliatore.adt.graph.search;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.EdgeWeight;
import com.vigliatore.adt.graph.GraphBuilder;
import com.vigliatore.adt.graph.WeightedGraph;
import com.vigliatore.adt.heap.Heap;
import com.vigliatore.adt.heap.SimpleHeap;

import java.util.BitSet;
import java.util.stream.Stream;

public class PrimsMinimumSpanningTree implements MinimumSpanningTree {

  private final int start;
  private final WeightedGraph minimumSpanningTree;
  private final WeightedGraph graph;
  private final Heap<EdgeWeight> edgeWeights;
  private final BitSet connected;

  public PrimsMinimumSpanningTree(WeightedGraph graph, int start) {
    int vertices = graph.vertices();
    this.graph = graph;
    minimumSpanningTree = new GraphBuilder()
        .setSize(graph.vertices())
        .disallowLoops()
        .disallowNegativeWeightEdges()
        .disallowParallelEdges()
        .build();
    edgeWeights = SimpleHeap.instance(EdgeWeight.minComparator());
    connected = new BitSet(vertices + 1);
    connected.set(0);
    this.start = start;
  }

  @Override
  public void solve() {
    // initialize the solver
    connected.set(start);
    for (int adjacentVertex : graph.getAdjacentVertices(start)) {
      Edge edge = Edge.instance(start, adjacentVertex);
      int weight = graph.getMinWeight(edge).orElseThrow(IllegalStateException::new);
      edgeWeights.add(EdgeWeight.instance(edge.to, edge.from, weight));
    }

    // proceed
    while (!edgeWeights.isEmpty()) {
      EdgeWeight edgeWeight = edgeWeights.pop();
      Edge edge = edgeWeight.edge();
      if (!isConnected(edge)) {
        // TODO reconcile Edge & EdgeWeight, is WeightedEdge an Edge? probably not
        minimumSpanningTree.addDirectedEdge(edge, edgeWeight.weight());

        if (isVertexConnected(edge.from) && isVertexConnected(edge.to)) {
          throw new IllegalStateException();
        } else {
          // extract to fetch an edge from connected to unconnected vertices
          Edge edgeFacingUnconnectedVertex = redirectEdgeTowardUnconnectedVertex(edge);
          connectEdge(edgeFacingUnconnectedVertex);
        }
      }
    }
  }

  @Override
  public WeightedGraph getMinimumSpanningTree() {
    return minimumSpanningTree;
  }

  private boolean isConnected(Edge edge) {
    return isVertexConnected(edge.from) && isVertexConnected(edge.to);
  }

  private boolean isVertexConnected(int vertex) {
    return connected.get(vertex);
  }

  private Edge redirectEdgeTowardUnconnectedVertex(Edge edge) {
    if (isConnected(edge)) {
      throw new IllegalArgumentException("edge must have one unconnected vertex");
    }

    return isVertexConnected(edge.from) ? edge : edge.reverse();
  }

  private void connectEdge(Edge edge) {
    if (isVertexConnected(edge.to) || !isVertexConnected(edge.from)) {
      throw new IllegalArgumentException();
    }

    int unconnectedVertex = edge.to;
    connected.set(unconnectedVertex);
    getUnconnectedAdjacentVertices(unconnectedVertex)
        .forEach(adjacentEdge -> {
          int weight = graph.getMinWeight(adjacentEdge).orElseThrow(IllegalStateException::new);
          edgeWeights.add(EdgeWeight.instance(adjacentEdge.to, adjacentEdge.from, weight));
        });
  }

  private Stream<Edge> getUnconnectedAdjacentVertices(int vertex) {
    return graph.getAdjacentVertices(vertex)
        .stream()
        .map(neighbor -> Edge.instance(vertex, neighbor))
        .filter(adjacentEdge -> !isConnected(adjacentEdge));
  }

}
