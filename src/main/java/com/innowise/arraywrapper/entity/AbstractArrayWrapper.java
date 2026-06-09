package com.innowise.arraywrapper.entity;

import com.innowise.arraywrapper.observer.ArrayWrapperObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for type-safe array wrappers.
 *
 * <p>Stores a defensive copy of the supplied array and provides basic
 * read/write access to its elements together with size queries. Each instance
 * receives a unique numeric {@code id} assigned automatically via a static
 * counter.
 *
 * <p>Implements the <b>Observable</b> side of the Observer pattern: observers
 * can be registered via {@link #addObserver(ArrayWrapperObserver)} and are
 * notified automatically every time {@link #set(int, Object)} is called.
 *
 * @param <T> the type of elements stored in the wrapped array
 */
public abstract class AbstractArrayWrapper<T> {

    private static final Logger logger = LogManager.getLogger(AbstractArrayWrapper.class);

    /** Global counter used to assign unique ids to every new instance. */
    private static long idCounter = 0;

    /** Unique identifier of this wrapper instance. */
    private final long id;

    /** The wrapped array (a defensive copy of the array passed to the constructor). */
    protected final T[] array;

    /** The number of elements in the wrapped array. */
    protected final int size;

    /** Registered observers notified on every element change. */
    private final List<ArrayWrapperObserver> observers = new ArrayList<>();

    /**
     * Constructs a new array wrapper around a defensive copy of the given array.
     *
     * @param array the array to wrap; must not be {@code null}
     */
    protected AbstractArrayWrapper(T[] array) {
        this.id = ++idCounter;
        this.array = array.clone();
        this.size = this.array.length;
        logger.debug("Created wrapper id={}, size={}", this.id, this.size);
    }

    /**
     * Returns the unique identifier of this wrapper instance.
     *
     * @return the id assigned at construction time
     */
    public long getId() {
        return id;
    }

    /**
     * Registers an observer that will be notified on every element change.
     *
     * @param observer the observer to register; must not be {@code null}
     */
    public void addObserver(ArrayWrapperObserver observer) {
        observers.add(observer);
        logger.debug("Observer {} added to wrapper id={}", observer.getClass().getSimpleName(), id);
    }

    /**
     * Unregisters a previously added observer.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(ArrayWrapperObserver observer) {
        observers.remove(observer);
        logger.debug("Observer {} removed from wrapper id={}", observer.getClass().getSimpleName(), id);
    }

    /**
     * Notifies all registered observers that an element of this wrapper changed.
     */
    private void notifyObservers() {
        for (ArrayWrapperObserver observer : observers) {
            observer.onElementChanged(id);
        }
    }

    /**
     * Returns the element located at the specified index.
     *
     * @param index the zero-based index of the element to return
     * @return the element at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    public T get(int index) {
        return array[index];
    }

    /**
     * Replaces the element at the specified index with the given value
     * and notifies all registered observers.
     *
     * @param index the zero-based index of the element to replace
     * @param value the new value to store at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    public void set(int index, T value) {
        logger.debug("Wrapper id={}: set index={} value={}", id, index, value);
        array[index] = value;
        notifyObservers();
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
