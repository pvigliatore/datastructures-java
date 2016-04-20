package com.vigliatore.adt.graph.search;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class PrimsAcceptanceTest {

  @Test
  public void simpleTest() throws IOException {
    InputStream in = ClassLoader.getSystemResourceAsStream("com/vigliatore/adt/graph/search/PrimsSpecialSubtreeSimpleDataSet");
    int actualValue = new Solution(in).solve();
    assertEquals(15, actualValue);
  }

}
