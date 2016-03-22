package com.vigliatore.adt.graph;

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

    Comparator<PathWeight> comparator = (o1, o2) -> Integer.compare(o1.weight(), o2.weight());
    priorityQueue = new IndexedHeapBuilder<Integer, PathWeight>()
        .setComparator(comparator)
        .setIdFunction(PathWeight::to)
        .build();

    shortestDistances = new HashMap<>();

    IntStream.rangeClosed(1, graph.vertices()).forEach(vertex -> {
      shortestDistances.put(vertex, Integer.MAX_VALUE);
      priorityQueue.add(PathWeight.get(vertex, Integer.MAX_VALUE));
    });
  }

  public Map<Integer, Integer> solve() {
    priorityQueue.update(startNode, PathWeight.get(startNode, 0));
    while (isUnsolved()) {
      PathWeight edge = priorityQueue.pop();
      int shortestDistanceToNode = Math.min(shortestDistance(edge.to()), edge.weight());
      shortestDistances.put(edge.to(), shortestDistanceToNode);

      // update the priority queue with the new estimated shortest paths
      graph.get(edge.to()).forEach((neighbor, weight) -> {
        int shortestDistanceToNeighbor = Math.min(shortestDistanceToNode + weight, shortestDistance(neighbor));
        priorityQueue.update(neighbor, PathWeight.get(neighbor, shortestDistanceToNeighbor));
      });
    }

    return Collections.unmodifiableMap(shortestDistances);
  }

  private boolean isUnsolved() {
    return !priorityQueue.isEmpty();
  }

  private int shortestDistance(int nodeId) {
    return shortestDistances.get(nodeId);
  }

}
