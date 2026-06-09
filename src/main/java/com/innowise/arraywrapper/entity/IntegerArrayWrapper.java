package com.innowise.arraywrapper.entity;

import java.util.Arrays;

/**
 * Array wrapper specialized for arrays of {@link Integer} elements.
 *
 * <p>Provides a type-safe wrapper around an {@code Integer} array, inheriting
 * element access, modification, size operations, and a unique {@code id} from
 * {@link AbstractArrayWrapper}. Overrides {@link #equals(Object)},
 * {@link #hashCode()} and {@link #toString()} using the wrapper's id and the
 * underlying array contents.
 */
public class IntegerArrayWrapper extends AbstractArrayWrapper<Integer> {

    /**
     * Constructs a new integer array wrapper around a defensive copy of the given array.
     *
     * @param array the array to wrap; must not be {@code null}
     * @throws NullPointerException if {@code array} is {@code null}
     */
    public IntegerArrayWrapper(Integer[] array) {
        super(array);
    }

    /**
     * Two {@code IntegerArrayWrapper} instances are equal when they have the
     * same {@code id} and the same array contents.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerArrayWrapper other)) return false;
        return this.getId() == other.getId()
                && Arrays.equals(this.array, other.array);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(getId());
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    /**
     * Returns a string of the form {@code IntegerArrayWrapper{id=1, array=[1, 2, 3]}}.
     */
    @Override
    public String toString() {
        return "IntegerArrayWrapper{id=" + getId() + ", array=" + Arrays.toString(array) + "}";
    }
}
