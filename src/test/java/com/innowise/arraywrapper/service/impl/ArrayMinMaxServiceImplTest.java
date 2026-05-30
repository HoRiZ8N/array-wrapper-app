package com.innowise.arraywrapper.service.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;

import com.innowise.arraywrapper.service.ArrayMinMaxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMinMaxServiceImplTest {

    private ArrayMinMaxService<Integer> service;

    @BeforeEach
    void setUp() {
        service = new ArrayMinMaxServiceImpl<>();
    }

    @Test
    void findMin_shouldReturnMin_whenWrapperHasMultipleElements() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{3, 1, 4, 1, 5, 9});
        assertEquals(1, service.findMin(wrapper).orElseThrow());
    }

    @Test
    void findMin_shouldReturnElement_whenWrapperHasSingleElement() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{42});
        assertEquals(42, service.findMin(wrapper).orElseThrow());
    }

    @Test
    void findMin_shouldReturnEmpty_whenWrapperIsEmpty() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{});
        assertTrue(service.findMin(wrapper).isEmpty());
    }

    @Test
    void findMin_shouldReturnMin_whenAllElementsAreEqual() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{7, 7, 7});
        assertEquals(7, service.findMin(wrapper).orElseThrow());
    }

    @Test
    void findMin_shouldReturnMin_whenArrayContainsNegativeNumbers() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{-3, -1, -7, 0});
        assertEquals(-7, service.findMin(wrapper).orElseThrow());
    }

    @Test
    void findMax_shouldReturnMax_whenWrapperHasMultipleElements() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{3, 1, 4, 1, 5, 9});
        assertEquals(9, service.findMax(wrapper).orElseThrow());
    }

    @Test
    void findMax_shouldReturnElement_whenWrapperHasSingleElement() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{42});
        assertEquals(42, service.findMax(wrapper).orElseThrow());
    }

    @Test
    void findMax_shouldReturnEmpty_whenWrapperIsEmpty() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{});
        assertTrue(service.findMax(wrapper).isEmpty());
    }

    @Test
    void findMax_shouldReturnMax_whenAllElementsAreEqual() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{7, 7, 7});
        assertEquals(7, service.findMax(wrapper).orElseThrow());
    }

    @Test
    void findMax_shouldReturnMax_whenArrayContainsNegativeNumbers() {
        AbstractArrayWrapper<Integer> wrapper = new IntegerArrayWrapper(new Integer[]{-3, -1, -7, 0});
        assertEquals(0, service.findMax(wrapper).orElseThrow());
    }
}