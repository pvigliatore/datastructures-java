package com.vigliatore.adt.heap;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SimpleHeap<T extends Comparable<T>> implements Heap<T> {

    private final ArrayList<T> values;

    public SimpleHeap() {
        values = new ArrayList<T>();
    }

    public void clear() {
        values.clear();
    }

    public boolean addAll(Collection<? extends T> items) {
        boolean updated = false;
        for (T item : items) {
            add(item);
            updated = true;
        }
        return updated;
    }

    public int size() {
        return values.size();
    }

    public boolean add(T value) {
        values.add(value);
        swim(size() - 1);
        return true;
    }

    private void swim(int index) {
        if (index == 0) {
            return;
        }

        int parentIndex = getParentIndex(index);
        if (hasPriority(index, parentIndex)) {
            swap(index, parentIndex);
            swim(parentIndex);
        }
    }

    private boolean hasPriority(int index1, int index2) {
        return get(index1).compareTo(get(index2)) < 0;
    }

    private T get(int index) {
        return values.get(index);
    }

    protected void swap(int index1, int index2) {
        T temp = get(index1);
        values.set(index1, get(index2));
        values.set(index2, temp);
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    protected void delete(int index) {
        int lastIndex = values.size() - 1;
        swap(index, lastIndex);
        values.remove(lastIndex);
        sink(index);
    }

    private void sink(int index) {
        int highestPriorityChild;
        switch (getNumberOfChildren(index)) {
            case 0:
                return;
            case 1:
                highestPriorityChild = getLeftChildIndex(index);
                break;
            case 2:
                int leftChild = getLeftChildIndex(index);
                int rightChild = getRightChildIndex(index);
                highestPriorityChild = hasPriority(leftChild, rightChild) ? leftChild : rightChild;
                break;
            default:
                throw new IllegalStateException("node must have 0, 1, or 2 children");
        }

        if (hasPriority(highestPriorityChild, index)) {
            swap(highestPriorityChild, index);
            sink(highestPriorityChild);
        }
    }

    private int getNumberOfChildren(int index) {
        int leftChildIndex = getLeftChildIndex(index);
        int lastIndex = size() - 1;
        return Integer.signum(lastIndex - leftChildIndex) + 1;
    }

    private int getLeftChildIndex(int index) {
        return index * 2 + 1;
    }

    private int getRightChildIndex(int index) {
        return getLeftChildIndex(index) + 1;
    }

    private T peek() {
        return values.get(0);
    }

    public T pop() {
        T head = peek();
        delete(0);
        return head;
    }

    public List<T> drain() {
        ArrayList<T> orderedValues = new ArrayList<T>();
        while (!isEmpty()) {
            orderedValues.add(pop());
        }
        return orderedValues;
    }

    public boolean isEmpty() {
        return size() != 0;
    }

}
