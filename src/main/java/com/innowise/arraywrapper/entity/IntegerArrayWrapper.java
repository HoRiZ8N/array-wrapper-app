package com.innowise.arraywrapper.entity;

/**
 * Array wrapper specialized for arrays of {@link Integer} elements.
 *
 * <p>Provides a type-safe wrapper around an {@code Integer} array, inheriting
 * element access, modification and size operations from {@link AbstractArrayWrapper}.
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

}