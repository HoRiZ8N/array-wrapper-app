package com.innowise.arraywrapper.service;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

/**
 * Service for sorting the elements of an array wrapper using different algorithms.
 *
 * <p>Implementations sort according to the natural ordering of the elements.
 * The original wrapper is left untouched; the sorted result is returned as a
 * new {@link AbstractArrayWrapper}.
 *
 * @param <T> the type of elements, comparable to itself or a supertype
 */
public interface ArraySortService<T extends Comparable<? super T>> {

    /**
     * Sorts the elements of the given wrapper using the bubble sort algorithm.
     *
     * <p>Time complexity:
     * <ul>
     *     <li>Best case: O(n)</li>
     *     <li>Worst case: O(n²)</li>
     * </ul>
     *
     * @param wrapper the array wrapper whose elements are to be sorted
     * @return a new wrapper containing the elements in ascending order
     */
    AbstractArrayWrapper<T> bubbleSort(AbstractArrayWrapper<T> wrapper);

    /**
     * Sorts the elements of the given wrapper using the quick sort algorithm.
     *
     * <p>Time complexity:
     * <ul>
     *     <li>Average case: O(n log n)</li>
     *     <li>Worst case: O(n²)</li>
     * </ul>
     *
     * @param wrapper the array wrapper whose elements are to be sorted
     * @return a new wrapper containing the elements in ascending order
     */
    AbstractArrayWrapper<T> quickSort(AbstractArrayWrapper<T> wrapper);
}
