package com.innowise.arraywrapper.service.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.factory.ArrayWrapperFactory;
import com.innowise.arraywrapper.service.ArraySortService;
import com.innowise.arraywrapper.util.ArrayUtil;

/**
 * Default implementation of {@link ArraySortService}.
 *
 * <p>Provides sorting operations for {@link AbstractArrayWrapper}:
 * Bubble Sort and Quick Sort.
 *
 * <p>Sorting is performed on a copied array to preserve immutability
 * of the source wrapper. The result is returned as a new wrapper
 * created via {@link ArrayWrapperFactory}.
 *
 * @param <T> type of elements that support comparison with themselves
 */
public class ArraySortServiceImpl<T extends Comparable<? super T>>
        implements ArraySortService<T> {

    private final ArrayWrapperFactory<T> factory;

    /**
     * Creates a new sorting service with the given factory.
     *
     * @param factory factory used to create result wrappers
     */
    public ArraySortServiceImpl(ArrayWrapperFactory<T> factory) {
        this.factory = factory;
    }

    /**
     * Sorts elements using Bubble Sort algorithm.
     *
     * <p>Bubble Sort repeatedly compares adjacent elements and swaps them
     * if they are in the wrong order. The algorithm stops early if no swaps
     * occur in a full iteration.
     *
     * <p>Time complexity:
     * <ul>
     *     <li>Best case: O(n)</li>
     *     <li>Worst case: O(n²)</li>
     * </ul>
     *
     * @param wrapper source array wrapper
     * @return new sorted {@link AbstractArrayWrapper} in ascending order
     */
    @Override
    public AbstractArrayWrapper<T> bubbleSort(AbstractArrayWrapper<T> wrapper) {

        T[] arr = ArrayUtil.copyToArray(wrapper);
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    ArrayUtil.swap(arr, j, j + 1);
                    swapped = true;
                }
            }

            if (!swapped) break;
        }

        return factory.create(arr);
    }

    /**
     * Sorts elements using Quick Sort algorithm.
     *
     * <p>Quick Sort is a divide-and-conquer algorithm that partitions
     * the array around a pivot element and recursively sorts partitions.
     *
     * <p>Time complexity:
     * <ul>
     *     <li>Average case: O(n log n)</li>
     *     <li>Worst case: O(n²)</li>
     * </ul>
     *
     * @param wrapper source array wrapper
     * @return new sorted {@link AbstractArrayWrapper} in ascending order
     */
    @Override
    public AbstractArrayWrapper<T> quickSort(AbstractArrayWrapper<T> wrapper) {

        T[] arr = ArrayUtil.copyToArray(wrapper);
        quickSort(arr, 0, arr.length - 1);

        return factory.create(arr);
    }

    /**
     * Recursive Quick Sort implementation.
     */
    private void quickSort(T[] arr, int low, int high) {

        if (low >= high) return;

        int pivotIndex = partition(arr, low, high);

        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }

    /**
     * Partitions array around pivot element.
     */
    private int partition(T[] arr, int low, int high) {

        T pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                ArrayUtil.swap(arr, i, j);
            }
        }

        ArrayUtil.swap(arr, i + 1, high);

        return i + 1;
    }
}
