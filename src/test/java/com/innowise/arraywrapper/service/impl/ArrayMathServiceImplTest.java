package com.innowise.arraywrapper.service.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;

import com.innowise.arraywrapper.service.ArrayMathService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMathServiceImplTest {

    private ArrayMathService<Integer> service;

    @BeforeEach
    void setUp() {
        service = new ArrayMathServiceImpl<>();
    }

    // sum

    @Test
    void sum_shouldReturnSum_whenWrapperHasMultipleElements() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3, 4});
        assertEquals(10.0, service.sum(wrapper).orElseThrow());
    }

    @Test
    void sum_shouldReturnElement_whenWrapperHasSingleElement() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{5});
        assertEquals(5.0, service.sum(wrapper).orElseThrow());
    }

    @Test
    void sum_shouldReturnEmpty_whenWrapperIsEmpty() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{});
        assertTrue(service.sum(wrapper).isEmpty());
    }

    @Test
    void sum_shouldReturnCorrectSum_whenArrayContainsNegativeNumbers() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{-1, -2, 3});
        assertEquals(0.0, service.sum(wrapper).orElseThrow());
    }

    // average

    @Test
    void average_shouldReturnAverage_whenWrapperHasMultipleElements() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3, 4});
        assertEquals(2.5, service.average(wrapper).orElseThrow());
    }

    @Test
    void average_shouldReturnElement_whenWrapperHasSingleElement() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{5});
        assertEquals(5.0, service.average(wrapper).orElseThrow());
    }

    @Test
    void average_shouldReturnEmpty_whenWrapperIsEmpty() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{});
        assertTrue(service.average(wrapper).isEmpty());
    }

    @Test
    void average_shouldReturnCorrectAverage_whenAllElementsAreEqual() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{4, 4, 4});
        assertEquals(4.0, service.average(wrapper).orElseThrow());
    }

    @Test
    void average_shouldReturnCorrectAverage_whenArrayContainsNegativeNumbers() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{-3, -1, 4});
        assertEquals(0.0, service.average(wrapper).orElseThrow());
    }
}