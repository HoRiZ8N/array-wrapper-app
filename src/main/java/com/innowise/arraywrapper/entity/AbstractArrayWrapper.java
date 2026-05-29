package com.innowise.arraywrapper.entity;

/**
 * Abstract base class for type-safe array wrappers.
 *
 * <p>Stores a defensive copy of the supplied array and provides basic
 * read/write access to its elements together with size queries. Subclasses
 * specialize the wrapper for a concrete element type.
 *
 * @param <T> the type of elements stored in the wrapped array;
 *            must be mutually comparable
 */
public abstract class AbstractArrayWrapper<T> {

    /** The wrapped array (a defensive copy of the array passed to the constructor). */
    protected final T[] array;

    /** The number of elements in the wrapped array. */
    protected final int size;

    /**
     * Constructs a new array wrapper around a defensive copy of the given array.
     *
     * <p>The provided array is cloned to protect the internal state from
     * external modifications.
     *
     * @param array the array to wrap; must not be {@code null}
     * @throws NullPointerException if {@code array} is {@code null}
     *                              (thrown by the JVM during cloning)
     */
    protected AbstractArrayWrapper(T[] array) {
        this.array = array.clone();
        this.size = this.array.length;
    }

    /**
     * Returns the element located at the specified index.
     *
     * @param index the zero-based index of the element to return
     * @return the element at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index >= size()})
     */
    public T get(int index) {
        return array[index];
    }

    /**
     * Replaces the element at the specified index with the given value.
     *
     * @param index the zero-based index of the element to replace
     * @param value the new value to store at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *                                        ({@code index < 0 || index >= size()})
     */
    public void set(int index, T value) {
        array[index] = value;
    }

    /**
     * Returns the number of elements in the wrapped array.
     *
     * @return the size of the wrapped array
     */
    public int size() {
        return size;
    }

    /**
     * Indicates whether the wrapped array contains no elements.
     *
     * @return {@code true} if the wrapped array is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
}