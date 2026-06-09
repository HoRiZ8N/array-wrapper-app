package com.innowise.arraywrapper.comparator;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

import java.util.Comparator;

/**
 * Compares two {@link AbstractArrayWrapper} instances by the number of
 * elements they contain (ascending order).
 *
 * @param <T> the element type of the wrappers
 */
public class WrapperSizeComparator<T> implements Comparator<AbstractArrayWrapper<T>> {

    @Override
    public int compare(AbstractArrayWrapper<T> a, AbstractArrayWrapper<T> b) {
        return Integer.compare(a.size(), b.size());
    }
}
