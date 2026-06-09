package com.innowise.arraywrapper.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerArrayWrapperTest {

    // ── constructor / basic access ─────────────────────────────────────────────

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
    void constructor_shouldAssignUniqueId_whenMultipleWrappersAreCreated() {
        IntegerArrayWrapper a = new IntegerArrayWrapper(new Integer[]{1});
        IntegerArrayWrapper b = new IntegerArrayWrapper(new Integer[]{1});
        assertNotEquals(a.getId(), b.getId());
    }

    // ── id ────────────────────────────────────────────────────────────────────

    @Test
    void getId_shouldReturnPositiveValue_whenWrapperIsCreated() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertTrue(wrapper.getId() > 0);
    }

    // ── size / isEmpty ────────────────────────────────────────────────────────

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

    // ── get / set ─────────────────────────────────────────────────────────────

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

    // ── equals ────────────────────────────────────────────────────────────────

    @Test
    void equals_shouldReturnTrue_whenSameInstance() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertEquals(wrapper, wrapper);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentInstancesHaveSameElements() {
        // Each wrapper gets its own unique id, so two distinct instances are never equal
        IntegerArrayWrapper a = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        IntegerArrayWrapper b = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertNotEquals(a, b);
    }

    @Test
    void equals_shouldReturnFalse_whenWrappersHaveDifferentElements() {
        IntegerArrayWrapper a = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        IntegerArrayWrapper b = new IntegerArrayWrapper(new Integer[]{1, 2, 4});
        assertNotEquals(a, b);
    }

    @Test
    void equals_shouldReturnFalse_whenWrappersHaveDifferentSizes() {
        IntegerArrayWrapper a = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        IntegerArrayWrapper b = new IntegerArrayWrapper(new Integer[]{1, 2});
        assertNotEquals(a, b);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedToNull() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertNotEquals(null, wrapper);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedToObjectOfDifferentType() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertNotEquals(wrapper, "not a wrapper");
    }

    // ── hashCode ──────────────────────────────────────────────────────────────

    @Test
    void hashCode_shouldBeConsistent_whenCalledMultipleTimes() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        int first = wrapper.hashCode();
        assertEquals(first, wrapper.hashCode());
        assertEquals(first, wrapper.hashCode());
    }

    @Test
    void hashCode_shouldBeEqual_whenSameInstance() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertEquals(wrapper.hashCode(), wrapper.hashCode());
    }

    @Test
    void hashCode_shouldBeDifferent_whenDistinctInstances() {
        // Different ids → different hashCodes (virtually always)
        IntegerArrayWrapper a = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        IntegerArrayWrapper b = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    // ── toString ──────────────────────────────────────────────────────────────

    @Test
    void toString_shouldContainIdAndArray_whenWrapperIsNotEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        String s = wrapper.toString();
        assertTrue(s.contains("id=" + wrapper.getId()));
        assertTrue(s.contains("[1, 2, 3]"));
    }

    @Test
    void toString_shouldContainEmptyArray_whenWrapperIsEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{});
        assertTrue(wrapper.toString().contains("[]"));
    }

    @Test
    void toString_shouldMatchExpectedFormat_whenWrapperHasOneElement() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{42});
        assertEquals("IntegerArrayWrapper{id=" + wrapper.getId() + ", array=[42]}", wrapper.toString());
    }
}
