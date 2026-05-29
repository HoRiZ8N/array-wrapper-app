package com.innowise.arraywrapper.service.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.service.ArrayMinMaxService;

import java.util.Optional;

/**
 * Default implementation of {@link ArrayMinMaxService} that performs a single
 * linear scan over the wrapped array to determine the minimum or maximum element.
 *
 * @param <T> the comparable type of elements
 */
public class ArrayMinMaxServiceImpl<T extends Comparable<? super T>>
        implements ArrayMinMaxService<T> {

    /**
     * {@inheritDoc}
     *
     * <p>Performs a linear scan and returns the smallest element according to
     * the natural ordering of {@code T}.
     */
    @Override
    public Optional<T> findMin(AbstractArrayWrapper<T> wrapper) {

        if (wrapper.isEmpty()) {
            return Optional.empty();
        }

        T min = wrapper.get(0);

        for (int i = 1; i < wrapper.size(); i++) {
            T current = wrapper.get(i);

            if (current.compareTo(min) < 0) {
                min = current;
            }
        }

        return Optional.of(min);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Performs a linear scan and returns the largest element according to
     * the natural ordering of {@code T}.
     */
    @Override
    public Optional<T> findMax(AbstractArrayWrapper<T> wrapper) {

        if (wrapper.isEmpty()) {
            return Optional.empty();
        }

        T max = wrapper.get(0);

        for (int i = 1; i < wrapper.size(); i++) {
            T current = wrapper.get(i);

            if (current.compareTo(max) > 0) {
                max = current;
            }
        }

        return Optional.of(max);
    }

}