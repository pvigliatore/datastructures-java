package com.vigliatore.adt.graph.validator;

import com.vigliatore.adt.graph.Edge;
import com.vigliatore.adt.graph.WeightedDigraph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AllowLoopsTest {

  private AllowLoops validator;
  private Edge edge;

  @Before
  public void setup() {
    edge = Edge.instance(1, 1);
  }

  @Mock
  private WeightedDigraph graph;

  @Test
  public void loopsAreAllowed() {
    validator = new AllowLoops(true);
    boolean valid = validator.isValid(graph, edge, 0);
    assertTrue(valid);
  }

  @Test
  public void loopsAreNotAllowed() {
    validator = new AllowLoops(false);
    boolean valid = validator.isValid(graph, edge, 0);
    assertFalse(valid);
  }

}
