package com.innowise.arraywrapper.service;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

import java.util.Comparator;
import java.util.List;

/**
 * Service for sorting a collection of {@link AbstractArrayWrapper} instances.
 *
 * <p>Unlike {@link ArraySortService} (which sorts elements <em>inside</em> one
 * wrapper), this service sorts a <em>list of wrappers</em> relative to each
 * other using a supplied {@link Comparator}.
 *
 * @param <T> the element type of the wrappers
 */
public interface WrapperSortService<T> {

    /**
     * Returns a new list containing all wrappers from {@code wrappers},
     * ordered according to the given {@code comparator}.
     *
     * <p>The original list is not modified.
     *
     * @param wrappers   the list of wrappers to sort; must not be {@code null}
     * @param comparator the ordering to apply; must not be {@code null}
     * @return a new sorted list
     */
    List<AbstractArrayWrapper<T>> sort(
            List<AbstractArrayWrapper<T>> wrappers,
            Comparator<AbstractArrayWrapper<T>> comparator);
}
