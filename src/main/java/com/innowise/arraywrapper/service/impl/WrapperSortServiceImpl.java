package com.innowise.arraywrapper.service.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.service.WrapperSortService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Default implementation of {@link WrapperSortService}.
 *
 * <p>Copies the input list into a new {@link ArrayList} and sorts it in-place
 * using {@link List#sort(Comparator)}, which delegates to
 * {@link java.util.Arrays#sort} (a stable merge sort). The original list
 * is never modified.
 *
 * @param <T> the element type of the wrappers
 */
public class WrapperSortServiceImpl<T> implements WrapperSortService<T> {

    /**
     * {@inheritDoc}
     *
     * <p>The returned list is a fresh {@link ArrayList} — it is mutable and
     * independent of the input list.
     */
    @Override
    public List<AbstractArrayWrapper<T>> sort(
            List<AbstractArrayWrapper<T>> wrappers,
            Comparator<AbstractArrayWrapper<T>> comparator) {

        List<AbstractArrayWrapper<T>> result = new ArrayList<>(wrappers);
        result.sort(comparator);
        return result;
    }
}
