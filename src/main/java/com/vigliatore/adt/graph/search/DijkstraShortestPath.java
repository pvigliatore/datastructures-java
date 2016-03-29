package com.vigliatore.adt.graph.search;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedDigraph;
import com.vigliatore.adt.heap.IndexedHeap;
import com.vigliatore.adt.heap.IndexedHeapBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class DijkstraShortestPath {

  private final WeightedDigraph graph;
  private final IndexedHeap<Integer, PathWeight> priorityQueue;
  private final Map<Integer, Integer> shortestDistances;
  private final int startNode;

  public DijkstraShortestPath(WeightedDigraph graph, int startNode) {
    this.graph = graph;
    this.startNode = startNode;
    this.priorityQueue = createPriorityQueue();
    this. shortestDistances = new HashMap<>();
    initialize(graph);
  }

  public void initialize(WeightedDigraph graph) {
    IntStream.rangeClosed(1, graph.vertices()).forEach(vertex -> {
      shortestDistances.put(vertex, Integer.MAX_VALUE);
      priorityQueue.add(vertex, PathWeight.get(vertex, Integer.MAX_VALUE));
    });
  }

  public IndexedHeap<Integer, PathWeight> createPriorityQueue() {
    Comparator<PathWeight> comparator = (o1, o2) -> Integer.compare(o1.weight(), o2.weight());
    return new IndexedHeapBuilder<Integer, PathWeight>()
        .setComparator(comparator)
        .build();
  }

  public Map<Integer, Integer> solve() {
    priorityQueue.update(startNode, PathWeight.get(startNode, 0));
    while (isUnsolved()) {
      PathWeight nearestVertex = priorityQueue.pop().value();
      int vertex = nearestVertex.to();
      int shortestDistanceToNode = Math.min(shortestDistance(vertex), nearestVertex.weight());
      shortestDistances.put(vertex, shortestDistanceToNode);

      // update the priority queue with the new estimated shortest paths
      graph.getAdjecentVertices(vertex)
          .stream()
          .forEach(adjacentVertex -> {
            Edge edge = Edge.instance(vertex, adjacentVertex);
            int weight = getMinWeight(edge);
            int shortestDistanceToNeighbor = Math.min(shortestDistanceToNode + weight, shortestDistance(adjacentVertex));
            shortestDistances.put(adjacentVertex, shortestDistanceToNeighbor);
            priorityQueue.update(adjacentVertex, PathWeight.get(adjacentVertex, shortestDistanceToNeighbor));
          });
    }

    return Collections.unmodifiableMap(shortestDistances);
  }

  private int getMinWeight(Edge edge) {
    return graph.getMinWeight(edge).orElseThrow(IllegalArgumentException::new);
  }

  private boolean isUnsolved() {
    return !priorityQueue.isEmpty();
  }

  private int shortestDistance(int nodeId) {
    return shortestDistances.get(nodeId);
  }

}
