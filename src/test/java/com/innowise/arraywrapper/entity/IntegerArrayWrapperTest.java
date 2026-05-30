package com.innowise.arraywrapper.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerArrayWrapperTest {

    @Test
    void constructor_shouldStoreCorrectElements_whenArrayIsProvided() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertEquals(1, wrapper.get(0));
        assertEquals(2, wrapper.get(1));
        assertEquals(3, wrapper.get(2));
    }

    @Test
    void constructor_shouldCreateDefensiveCopy_whenArrayIsModifiedAfterCreation() {
        Integer[] array = {1, 2, 3};
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(array);
        array[0] = 99;
        assertEquals(1, wrapper.get(0));
    }

    @Test
    void size_shouldReturnCorrectSize_whenWrapperIsNotEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertEquals(3, wrapper.size());
    }

    @Test
    void size_shouldReturnZero_whenWrapperIsEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{});
        assertEquals(0, wrapper.size());
    }

    @Test
    void isEmpty_shouldReturnTrue_whenWrapperIsEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{});
        assertTrue(wrapper.isEmpty());
    }

    @Test
    void isEmpty_shouldReturnFalse_whenWrapperIsNotEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1});
        assertFalse(wrapper.isEmpty());
    }

    @Test
    void get_shouldReturnCorrectElement_whenIndexIsValid() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{10, 20, 30});
        assertEquals(20, wrapper.get(1));
    }

    @Test
    void get_shouldThrowException_whenIndexIsOutOfBounds() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> wrapper.get(5));
    }

    @Test
    void set_shouldUpdateElement_whenIndexIsValid() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        wrapper.set(1, 99);
        assertEquals(99, wrapper.get(1));
    }

    @Test
    void set_shouldThrowException_whenIndexIsOutOfBounds() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> wrapper.set(5, 99));
    }
}