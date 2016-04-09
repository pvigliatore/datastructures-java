package com.vigliatore.adt.heap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleHeapTest {

  private Heap<Integer> heap;

  @Before
  public void setup() {
    heap = SimpleHeap.minHeap();
  }

  @Test
  public void heapMaintainsInvariantDuringPop() {
    int size = 100000;
    for (int x : randomizedList(size)) {
      heap.add(x);
    }

    int expectedValue = 0;
    for (int actualValue : heap) {
      Assert.assertEquals(expectedValue++, actualValue);
    }
  }

  private List<Integer> randomizedList(int size) {
    List<Integer> randomized = new ArrayList<Integer>();
    for (int i = 0; i < size; i++) {
      randomized.add(i);
    }
    Collections.shuffle(randomized);
    return randomized;
  }

}
