package com.innowise.arraywrapper.service.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.service.ArrayMathService;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Default implementation of {@link ArrayMathService}.
 *
 * <p>Computes sum and average using Stream API.
 *
 * @param <T> the numeric type of elements
 */
public class ArrayMathServiceImpl<T extends Number>
        implements ArrayMathService<T> {

    /**
     * {@inheritDoc}
     *
     * <p>Uses Stream API and converts each element to {@code double}.
     */
    @Override
    public double sum(AbstractArrayWrapper<T> wrapper) {
        return IntStream.range(0, wrapper.size())
                .mapToDouble(i -> wrapper.get(i).doubleValue())
                .sum();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Average is calculated using Stream API.
     */
    @Override
    public Optional<Double> average(AbstractArrayWrapper<T> wrapper) {

        if (wrapper.isEmpty()) {
            return Optional.empty();
        }

        double avg = IntStream.range(0, wrapper.size())
                .mapToDouble(i -> wrapper.get(i).doubleValue())
                .average()
                .orElse(0.0);

        return Optional.of(avg);
    }
}