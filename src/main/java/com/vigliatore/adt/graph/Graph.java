package com.vigliatore.adt.graph;

import java.util.Set;

public interface Graph {

  boolean contains(int vertex);

  boolean contains(Edge edge);

  int vertices();

  Set<Integer> getAdjacentVertices(int vertex);

  int size();
}
