package com.innowise.arraywrapper.service;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

import java.util.Optional;

/**
 * Service for mathematical operations over the numeric elements of an array wrapper.
 *
 * @param <T> the numeric type of elements
 */
public interface ArrayMathService<T extends Number> {

    /**
     * Calculates the sum of all elements in the given wrapper.
     *
     * @param wrapper the array wrapper to process
     * @return an {@link Optional} containing the sum of all elements as a {@code double},
     *         or an empty {@code Optional} if the wrapper is empty
     */
    Optional<Double> sum(AbstractArrayWrapper<T> wrapper);

    /**
     * Calculates the arithmetic mean of the elements in the given wrapper.
     *
     * @param wrapper the array wrapper to process
     * @return an {@link Optional} containing the average value,
     *         or an empty {@code Optional} if the wrapper is empty
     */
    Optional<Double> average(AbstractArrayWrapper<T> wrapper);
}