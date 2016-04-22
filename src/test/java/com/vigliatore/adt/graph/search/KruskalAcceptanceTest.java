package com.vigliatore.adt.graph.search;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class KruskalAcceptanceTest {

  @Test
  public void simpleTest() throws IOException {
    InputStream in = ClassLoader.getSystemResourceAsStream("com/vigliatore/adt/graph/search/KruskalReallySpecialSubtreeSimpleDataSet");
    int actualValue = new KruskalTestHarness(in).solve();
    assertEquals(15, actualValue);
  }

}
