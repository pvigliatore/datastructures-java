package com.vigliatore.adt.heap;

import com.vigliatore.adt.graph.Tuple;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    queue = new SimpleIndexedHeap<>(Comparator.<Integer>naturalOrder());
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
    int size = 1000;
    IntStream.range(0, size).forEach(x -> queue.add(x, x));
    verifyQueueIsSorted(Function.identity());
  }

  private void verifyQueueIsSorted(Function<Integer, Integer> valueFunction) {
    for (int i = 0; !queue.isEmpty(); i++) {
      Tuple<Integer, Integer> tuple = queue.pop();
      int key = tuple.key();
      int value = tuple.value();
      assertEquals(i, key);
      assertEquals(value, valueFunction.apply(key).intValue());
    }
  }

  @Test
  public void addRandomValues() {
    int size = 20;
    List<Integer> vertices = createRandomizedSequentialList(size);
    vertices.stream().forEach(x -> queue.add(x, x));
    verifyQueueIsSorted(Function.identity());
  }

  private List<Integer> createRandomizedSequentialList(int size) {
    List<Integer> vertices = createSequentialList(size);
    randomize(vertices);
    return vertices;
  }

  private void randomize(List<Integer> vertices) {
    Collections.shuffle(vertices);
  }

  private List<Integer> createSequentialList(int size) {
    List<Integer> list = new ArrayList<>();
    IntStream.range(0, size).forEach(list::add);
    return list;
  }

  @Test
  public void updateBySinkingAllValues() {
    int size = 100;
    createRandomizedSequentialList(size).forEach(x -> queue.add(x, x));
    createRandomizedSequentialList(size).forEach(x -> queue.update(x, x * 2));
    verifyQueueIsSorted(x -> x * 2);
  }

  @Test
  public void updateBySwimingAllValues() {
    int size = 100;
    createRandomizedSequentialList(size).forEach(x -> queue.add(x, x * 2));
    createRandomizedSequentialList(size).forEach(x -> queue.update(x, x));
    verifyQueueIsSorted(Function.identity());
  }

  @Test
  public void randomlyUpdateAllValues() {
    int size = 100;
    createRandomizedSequentialList(size).forEach(x -> queue.add(x, x));
    createRandomizedSequentialList(size).forEach(x -> queue.update(x, x * 2));
    createRandomizedSequentialList(size).forEach(x -> queue.update(x, x));
    verifyQueueIsSorted(Function.identity());
  }

}
