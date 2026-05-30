package com.innowise.arraywrapper.service.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;
import com.innowise.arraywrapper.factory.impl.IntegerWrapperFactory;
import com.innowise.arraywrapper.service.ArraySortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArraySortServiceImplTest {

    private ArraySortService<Integer> service;

    @BeforeEach
    void setUp() {
        service = new ArraySortServiceImpl<>(new IntegerWrapperFactory());
    }

    // bubbleSort

    @Test
    void bubbleSort_shouldReturnSortedWrapper_whenWrapperIsUnsorted() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{5, 3, 1, 4, 2});
        AbstractArrayWrapper<Integer> sorted = service.bubbleSort(wrapper);

        assertArrayContent(sorted, 1, 2, 3, 4, 5);
    }

    @Test
    void bubbleSort_shouldReturnSortedWrapper_whenWrapperIsAlreadySorted() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3, 4, 5});
        AbstractArrayWrapper<Integer> sorted = service.bubbleSort(wrapper);

        assertArrayContent(sorted, 1, 2, 3, 4, 5);
    }

    @Test
    void bubbleSort_shouldReturnSortedWrapper_whenWrapperIsSortedInReverse() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{5, 4, 3, 2, 1});
        AbstractArrayWrapper<Integer> sorted = service.bubbleSort(wrapper);

        assertArrayContent(sorted, 1, 2, 3, 4, 5);
    }

    @Test
    void bubbleSort_shouldReturnSortedWrapper_whenWrapperHasDuplicates() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{3, 1, 2, 1, 3});
        AbstractArrayWrapper<Integer> sorted = service.bubbleSort(wrapper);

        assertArrayContent(sorted, 1, 1, 2, 3, 3);
    }

    @Test
    void bubbleSort_shouldReturnSortedWrapper_whenWrapperHasSingleElement() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{42});
        AbstractArrayWrapper<Integer> sorted = service.bubbleSort(wrapper);

        assertArrayContent(sorted, 42);
    }

    @Test
    void bubbleSort_shouldReturnEmptyWrapper_whenWrapperIsEmpty() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{});
        AbstractArrayWrapper<Integer> sorted = service.bubbleSort(wrapper);

        assertTrue(sorted.isEmpty());
    }

    @Test
    void bubbleSort_shouldNotModifySourceWrapper_whenCalled() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{3, 1, 2});
        service.bubbleSort(wrapper);

        assertArrayContent(wrapper, 3, 1, 2);
    }

    // quickSort

    @Test
    void quickSort_shouldReturnSortedWrapper_whenWrapperIsUnsorted() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{5, 3, 1, 4, 2});
        AbstractArrayWrapper<Integer> sorted = service.quickSort(wrapper);

        assertArrayContent(sorted, 1, 2, 3, 4, 5);
    }

    @Test
    void quickSort_shouldReturnSortedWrapper_whenWrapperIsAlreadySorted() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3, 4, 5});
        AbstractArrayWrapper<Integer> sorted = service.quickSort(wrapper);

        assertArrayContent(sorted, 1, 2, 3, 4, 5);
    }

    @Test
    void quickSort_shouldReturnSortedWrapper_whenWrapperIsSortedInReverse() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{5, 4, 3, 2, 1});
        AbstractArrayWrapper<Integer> sorted = service.quickSort(wrapper);

        assertArrayContent(sorted, 1, 2, 3, 4, 5);
    }

    @Test
    void quickSort_shouldReturnSortedWrapper_whenWrapperHasDuplicates() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{3, 1, 2, 1, 3});
        AbstractArrayWrapper<Integer> sorted = service.quickSort(wrapper);

        assertArrayContent(sorted, 1, 1, 2, 3, 3);
    }

    @Test
    void quickSort_shouldReturnSortedWrapper_whenWrapperHasSingleElement() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{42});
        AbstractArrayWrapper<Integer> sorted = service.quickSort(wrapper);

        assertArrayContent(sorted, 42);
    }

    @Test
    void quickSort_shouldReturnEmptyWrapper_whenWrapperIsEmpty() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{});
        AbstractArrayWrapper<Integer> sorted = service.quickSort(wrapper);

        assertTrue(sorted.isEmpty());
    }

    @Test
    void quickSort_shouldNotModifySourceWrapper_whenCalled() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{3, 1, 2});
        service.quickSort(wrapper);

        assertArrayContent(wrapper, 3, 1, 2);
    }

    // util

    private void assertArrayContent(AbstractArrayWrapper<Integer> wrapper, Integer... expected) {
        assertEquals(expected.length, wrapper.size());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], wrapper.get(i));
        }
    }
}