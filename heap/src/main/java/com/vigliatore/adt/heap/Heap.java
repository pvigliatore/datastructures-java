package com.vigliatore.adt.heap;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Heap<T extends Comparable<T>> implements Collection<T> {

    private final ArrayList<T> values;

    public Heap() {
        values = new ArrayList<T>();
    }

    public void clear() {
        values.clear();
    }

    public Stream<T> parallelStream() {
        throw new UnsupportedOperationException();
    }

    public Stream<T> stream() {
        throw new UnsupportedOperationException();
    }

    public Spliterator<T> spliterator() {
        throw new UnsupportedOperationException();
    }

    public boolean removeIf(Predicate<? super T> filter) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
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

    private void swap(int index1, int index2) {
        T temp = get(index1);
        values.set(index1, get(index2));
        values.set(index2, temp);
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private void delete(int index) {
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
