package com.innowise.arraywrapper.util;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

/**
 * Utility class that provides helper methods for working with arrays
 * wrapped in {@link AbstractArrayWrapper}.
 *
 * <p>Includes common operations such as copying wrapper elements into
 * a raw array and swapping elements within an array.
 */
public final class ArrayUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private ArrayUtil() {}

    /**
     * Swaps two elements in the given array.
     *
     * @param arr the array in which elements are swapped
     * @param i   index of the first element
     * @param j   index of the second element
     * @param <T> element type
     */
    public static <T> void swap(T[] arr, int i, int j) {

        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Creates a defensive copy of elements from the given wrapper into a new array.
     *
     * @param wrapper source array wrapper
     * @param <T>     element type
     * @return new array containing a copy of the wrapper's elements
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> T[] copyToArray(AbstractArrayWrapper<T> wrapper) {

        if (wrapper.isEmpty()) {
            return (T[]) new Comparable[0];
        }

        T[] result = (T[]) java.lang.reflect.Array.newInstance(
                wrapper.get(0).getClass(), wrapper.size());

        for (int i = 0; i < wrapper.size(); i++) {
            result[i] = wrapper.get(i);
        }

        return result;
    }
}
