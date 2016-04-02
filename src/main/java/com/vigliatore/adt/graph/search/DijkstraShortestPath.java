package com.vigliatore.adt.graph.search;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedGraph;
import com.vigliatore.adt.heap.IndexedHeap;
import com.vigliatore.adt.heap.IndexedHeapBuilder;

import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class DijkstraShortestPath implements Iterator<PathWeight> {

  private final WeightedGraph graph;
  private final IndexedHeap<Integer, PathWeight> priorityQueue;
  private final Map<Integer, Integer> estimates;
  private final BitSet visited;
  private final int startNode;

  public DijkstraShortestPath(WeightedGraph graph, int startNode) {
    this.graph = graph;
    this.startNode = startNode;
    this.priorityQueue = createPriorityQueue();
    this.estimates = new HashMap<>();
    this.visited = new BitSet(graph.vertices());
  }

  private IndexedHeap<Integer, PathWeight> createPriorityQueue() {
    Comparator<PathWeight> comparator = (o1, o2) -> Integer.compare(o1.weight(), o2.weight());
    return new IndexedHeapBuilder<Integer, PathWeight>()
        .setComparator(comparator)
        .build();
  }

  public Map<Integer, Integer> solve() {
    priorityQueue.add(startNode, PathWeight.get(startNode, 0));
    while (isUnsolved()) {
      solveForNearestVertex();
    }

    return Collections.unmodifiableMap(estimates);
  }

  @Override
  public void forEachRemaining(Consumer<? super PathWeight> action) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  @Override
  public PathWeight next() {
    return solveForNearestVertex();
  }

  @Override
  public boolean hasNext() {
    return isUnsolved();
  }

  private PathWeight solveForNearestVertex() {
    PathWeight nearest = priorityQueue.pop().value();
    int vertex = nearest.vertex();
    updateEstimate(nearest);
    visited.set(vertex - 1);

    // update the priority queue with the new estimated shortest paths
    graph.getAdjecentVertices(vertex)
        .stream()
        .filter(adjacentVertex -> !visited.get(adjacentVertex - 1))
        .forEach(adjacentVertex -> {
          Edge edge = Edge.instance(vertex, adjacentVertex);
          int weight = getMinWeight(edge);
          PathWeight estimate = PathWeight.get(adjacentVertex, estimates.get(vertex) + weight);
          updateEstimate(estimate);
          priorityQueue.set(adjacentVertex, PathWeight.get(adjacentVertex, shortestDistance(adjacentVertex)));
        });

    return nearest;
  }

  private void updateEstimate(PathWeight nearest) {
    int vertex = nearest.vertex();
    int estimate = nearest.weight();

    int shortestDistanceToNode;
    if(estimates.containsKey(vertex)) {
      int shortestPath = shortestDistance(vertex);
      shortestDistanceToNode = Math.min(shortestPath, estimate);
    } else {
      shortestDistanceToNode = estimate;
    }
    estimates.put(vertex, shortestDistanceToNode);
  }

  private int getMinWeight(Edge edge) {
    return graph.getMinWeight(edge).orElseThrow(IllegalArgumentException::new);
  }

  private boolean isUnsolved() {
    return !priorityQueue.isEmpty();
  }

  private int shortestDistance(int nodeId) {
    return estimates.get(nodeId);
  }

}
