package com.vigliatore.adt.heap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeapTest {

    private Heap<Integer> heap;

    @Before
    public void setup() {
        heap = new Heap<Integer>();
    }

    @Test
    public void heapMaintainsInvariantDuringPop() {
        int size = 100000;
        for (int x : randomizedList(size)) {
            heap.add(x);
        }

        for (int i = 0; i < size; i++) {
            Assert.assertEquals(i, heap.pop().intValue());
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
