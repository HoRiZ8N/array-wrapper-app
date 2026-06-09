package com.innowise.arraywrapper.warehouse;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

import java.util.Optional;

/**
 * Storage for pre-computed statistics of {@link AbstractArrayWrapper} instances.
 *
 * <p>Statistics are indexed by the wrapper's {@code id} and are recomputed
 * whenever {@link #recalculate(AbstractArrayWrapper)} is called — typically
 * triggered by an Observer when an array element changes.
 *
 * <p>Implementations must not implement any Observer interface directly;
 * recalculation must be initiated by a dedicated mediator.
 *
 * @param <T> the element type of the wrappers whose statistics are stored
 */
public interface Warehouse<T> {

    /**
     * Computes and stores fresh statistics for the given wrapper.
     *
     * <p>If the wrapper is empty the entry is removed from storage,
     * because there are no meaningful statistics to keep.
     *
     * @param wrapper the wrapper whose statistics should be (re)calculated;
     *                must not be {@code null}
     */
    void recalculate(AbstractArrayWrapper<T> wrapper);

    /**
     * Returns the statistics previously computed for the wrapper with the given id.
     *
     * @param id the wrapper id to look up
     * @return an {@link Optional} containing the statistics, or empty if not found
     */
    Optional<ArrayStatistics> getStatistics(long id);

    /**
     * Removes the statistics entry for the given wrapper id.
     * Called when a wrapper is removed from the repository.
     *
     * @param id the wrapper id whose statistics should be deleted
     */
    void remove(long id);
}
