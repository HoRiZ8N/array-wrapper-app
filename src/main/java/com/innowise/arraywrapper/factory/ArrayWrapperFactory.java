package com.innowise.arraywrapper.factory;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

/**
 * Factory interface for creating instances of {@link AbstractArrayWrapper}.
 *
 * <p>Defines a contract for creating array wrapper objects without exposing
 * their concrete implementations.
 *
 * @param <T> type of elements stored in the array wrapper
 */
public interface ArrayWrapperFactory<T> {

    /**
     * Creates a new {@link AbstractArrayWrapper} instance
     * from the provided array.
     *
     * @param array source array used to initialize the wrapper
     * @return new instance of {@link AbstractArrayWrapper}
     */
    AbstractArrayWrapper<T> create(T[] array);
}