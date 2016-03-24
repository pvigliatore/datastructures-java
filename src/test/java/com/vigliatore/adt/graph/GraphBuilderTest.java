package com.vigliatore.adt.graph;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphBuilderTest {

  private GraphBuilder builder;
  private WeightedDigraph graph;

  @Before
  public void setup() {
    builder = new GraphBuilder();
  }

  @Test
  public void buildGraphAllowingLoops() {
    int weight = 10;
    graph = builder.setSize(weight)
        .allowLoops()
        .build();
    Edge loopingEdge = Edge.get(1, 1);
    graph.add(loopingEdge, weight);

    assertTrue(graph.contains(loopingEdge));
    assertEquals(weight, graph.get(loopingEdge));
  }

  @Test (expected = IllegalArgumentException.class)
  public void buildGraphDisallowingLoops() {
    int weight = 10;
    graph = builder.setSize(weight)
        .disallowLoops()
        .build();
    graph.add(Edge.get(1, 1), weight);
  }

  @Test(expected = IllegalArgumentException.class)
  public void defaultGraphDisallowsLoops() {
    int weight = 10;
    graph = builder
        .setSize(weight)
        .build();
    Edge loopingEdge = Edge.get(1, 1);
    graph.add(loopingEdge, weight);

    assertTrue(graph.contains(loopingEdge));
    assertEquals(weight, graph.get(loopingEdge));
  }

  @Test
  public void buildGrowableGraph() {
    graph = builder
        .setGrowable()
        .setSize(0)
        .build();
    assertEquals(0, graph.vertices());

    graph.add(Edge.get(10, 11), 0);

    assertEquals(11, graph.vertices());
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildFixedSizeGraph() {
    graph = builder
        .setSize(2)
        .build();

    graph.add(Edge.get(1, 3), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void defaultGraphIsFixedSize() {
    graph = builder
        .setSize(2)
        .build();

    graph.add(Edge.get(1, 3), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void defaultGraphAllowsOnlyPositiveWeights() {
    graph = builder
        .setSize(10)
        .build();

    graph.add(Edge.get(1, 3), -10);
  }

  @Test
  public void buildGraphWithNegativeEdges() {
    graph = builder
        .setSize(10)
        .allowNegativeWeightEdges()
        .build();

    Edge edge = Edge.get(1, 5);
    int weight = -1;
    graph.add(edge, weight);

    assertEquals(weight, graph.get(edge));
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildGraphAllowingOnlyPositiveEdges() {
    graph = builder
        .setSize(10)
        .disallowNegativeWeightEdges()
        .build();

    graph.add(Edge.get(1, 3), -10);
  }

}