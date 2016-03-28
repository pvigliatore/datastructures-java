package com.vigliatore.adt.heap;

import com.vigliatore.adt.graph.Tuple;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SimpleIndexedHeapTest {

  private IndexedHeap<Integer, Integer> queue;

  @Before
  public void setup() {
    queue = new SimpleIndexedHeap<>(Function.<Integer>identity(), Comparator.<Integer>naturalOrder());
  }

  @Test
  public void popEmptyQueue() {
    assertNull(queue.pop());
  }

  @Test
  public void reduceSizeAfterPop() {
    assertTrue(queue.isEmpty());

    queue.add(1, 1);
    assertFalse(queue.isEmpty());

    queue.pop();
    assertTrue(queue.isEmpty());
  }

  @Test
  public void popSingleValue() {
    int key = 1;
    int value = 2;
    queue.add(key, value);
    validateHead(key, value);
  }

  public void validateHead(int key, int value) {
    Tuple<Integer, Integer> keyValuePair = queue.pop();
    assertEquals(key, keyValuePair.key().intValue());
    assertEquals(value, keyValuePair.value().intValue());
  }

  @Test
  public void updateValue() {
    queue.add(1, 2);
    queue.update(1, 3);
    assertEquals(1, queue.size());
    validateHead(1, 3);
  }

  @Test
  public void sortedTest() {
    IntStream.range(0, 1000).forEach(x -> queue.add(x, x));
    boolean isSorted = IntStream.range(0, 1000).allMatch(x -> x == queue.pop().value());
    assertTrue(isSorted);
  }

}
