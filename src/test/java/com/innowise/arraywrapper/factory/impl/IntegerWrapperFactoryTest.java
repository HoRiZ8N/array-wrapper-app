package com.innowise.arraywrapper.factory.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerWrapperFactoryTest {

    private IntegerWrapperFactory factory;

    @BeforeEach
    void setUp() {
        factory = new IntegerWrapperFactory();
    }

    @Test
    void create_shouldReturnIntegerArrayWrapper_whenArrayIsProvided() {
        AbstractArrayWrapper<Integer> wrapper = factory.create(new Integer[]{1, 2, 3});
        assertInstanceOf(IntegerArrayWrapper.class, wrapper);
    }

    @Test
    void create_shouldReturnWrapperWithCorrectSize_whenArrayIsProvided() {
        AbstractArrayWrapper<Integer> wrapper = factory.create(new Integer[]{1, 2, 3});
        assertEquals(3, wrapper.size());
    }

    @Test
    void create_shouldReturnWrapperWithCorrectElements_whenArrayIsProvided() {
        AbstractArrayWrapper<Integer> wrapper = factory.create(new Integer[]{1, 2, 3});
        assertEquals(1, wrapper.get(0));
        assertEquals(2, wrapper.get(1));
        assertEquals(3, wrapper.get(2));
    }

    @Test
    void create_shouldReturnEmptyWrapper_whenEmptyArrayIsProvided() {
        AbstractArrayWrapper<Integer> wrapper = factory.create(new Integer[]{});
        assertTrue(wrapper.isEmpty());
    }

    @Test
    void create_shouldReturnDefensiveCopy_whenArrayIsModifiedAfterCreation() {
        Integer[] array = {1, 2, 3};
        AbstractArrayWrapper<Integer> wrapper = factory.create(array);
        array[0] = 99;
        assertEquals(1, wrapper.get(0));
    }
}