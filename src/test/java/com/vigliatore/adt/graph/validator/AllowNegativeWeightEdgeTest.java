package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedDigraph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AllowNegativeWeightEdgeTest {

  private static final int NEGATIVE_WEIGHT = -100;

  @Mock
  private WeightedDigraph graph;

  @Mock
  private Edge edge;

  private EdgeValidator validator;

  @Test
  public void allowOnlyPositiveWeights() {
    validator = new AllowNegativeWeightEdge(false);
    boolean valid = validator.isValid(graph, edge, NEGATIVE_WEIGHT);
    assertFalse(valid);
  }

  @Test
  public void zeroIsAPositiveWeight() {
    validator = new AllowNegativeWeightEdge(false);
    boolean valid = validator.isValid(graph, edge, 0);
    assertTrue(valid);
  }

  @Test
  public void allowNegativeWeights() {
    validator = new AllowNegativeWeightEdge(true);
    boolean valid = validator.isValid(graph, edge, NEGATIVE_WEIGHT);
    assertTrue(valid);
  }

}
