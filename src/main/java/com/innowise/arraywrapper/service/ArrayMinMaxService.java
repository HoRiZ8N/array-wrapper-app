package com.innowise.arraywrapper.service;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

import java.util.Optional;

/**
 * Service for finding the minimum and maximum elements of an array wrapper.
 *
 * @param <T> the comparable type of elements
 */
public interface ArrayMinMaxService<T extends Comparable<? super T>> {

    /**
     * Finds the minimum element in the given wrapper.
     *
     * @param wrapper the array wrapper to inspect
     * @return an {@link Optional} containing the minimum element,
     *         or an empty {@code Optional} if the wrapper is empty
     */
    Optional<T> findMin(AbstractArrayWrapper<T> wrapper);

    /**
     * Finds the maximum element in the given wrapper.
     *
     * @param wrapper the array wrapper to inspect
     * @return an {@link Optional} containing the maximum element,
     *         or an empty {@code Optional} if the wrapper is empty
     */
    Optional<T> findMax(AbstractArrayWrapper<T> wrapper);

}