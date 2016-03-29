package com.vigliatore.adt.graph;

import java.util.Set;

public interface Graph {
  void add(Edge edge);

  boolean contains(int vertex);

  boolean contains(Edge edge);

  int vertices();

  Set<Integer> getAdjecentVertices(int vertex);

  int size();
}
