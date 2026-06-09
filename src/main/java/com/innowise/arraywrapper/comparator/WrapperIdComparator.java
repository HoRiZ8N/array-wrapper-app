package com.innowise.arraywrapper.comparator;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

import java.util.Comparator;

/**
 * Compares two {@link AbstractArrayWrapper} instances by their {@code id}
 * in ascending order.
 *
 * @param <T> the element type of the wrappers
 */
public class WrapperIdComparator<T> implements Comparator<AbstractArrayWrapper<T>> {

    @Override
    public int compare(AbstractArrayWrapper<T> a, AbstractArrayWrapper<T> b) {
        return Long.compare(a.getId(), b.getId());
    }
}
