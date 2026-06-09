package com.innowise.arraywrapper.comparator;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

import java.util.Comparator;

/**
 * Compares two {@link AbstractArrayWrapper} instances by their first element
 * in ascending order.
 *
 * <p>Empty wrappers are considered smaller than non-empty ones.
 *
 * @param <T> the element type; must be {@link Comparable}
 */
public class WrapperFirstElementComparator<T extends Comparable<? super T>> implements Comparator<AbstractArrayWrapper<T>> {

    @Override
    public int compare(AbstractArrayWrapper<T> a, AbstractArrayWrapper<T> b) {
        if (a.isEmpty() && b.isEmpty()) return 0;
        if (a.isEmpty()) return -1;
        if (b.isEmpty()) return  1;
        return a.get(0).compareTo(b.get(0));
    }
}
