package com.innowise.arraywrapper.repository.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.repository.ArrayWrapperRepository;
import com.innowise.arraywrapper.specification.ArrayWrapperSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Singleton implementation of {@link ArrayWrapperRepository} for
 * {@link Integer} element wrappers.
 *
 * <p>Uses a classic (non-thread-safe) Singleton: the sole instance is created
 * lazily on first access via {@link #getInstance()}.
 *
 * <p>All wrappers are stored internally in an {@link ArrayList}; the list is
 * never exposed directly — {@link #findBySpecification} returns a new
 * unmodifiable list of matching wrappers.
 */
public class IntegerArrayWrapperRepository
        implements ArrayWrapperRepository<Integer> {

    private static final Logger logger = LogManager.getLogger(IntegerArrayWrapperRepository.class);

    /** The single instance of this repository. */
    private static IntegerArrayWrapperRepository instance;

    /** Internal storage of all registered wrappers. */
    private final List<AbstractArrayWrapper<Integer>> storage = new ArrayList<>();

    /** Private constructor prevents external instantiation. */
    private IntegerArrayWrapperRepository() {
    }

    /**
     * Returns the sole instance of this repository.
     *
     * <p>The instance is created on first call (lazy initialization).
     * This implementation is intentionally <b>not</b> thread-safe.
     *
     * @return the singleton {@code IntegerArrayWrapperRepository}
     */
    public static IntegerArrayWrapperRepository getInstance() {
        if (instance == null) {
            instance = new IntegerArrayWrapperRepository();
            logger.debug("IntegerArrayWrapperRepository instance created");
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(AbstractArrayWrapper<Integer> wrapper) {
        boolean exists = storage.stream().anyMatch(w -> w.getId() == wrapper.getId());
        if (!exists) {
            storage.add(wrapper);
        }
        logger.debug("Add wrapper id={}: added={}, total={}", wrapper.getId(), !exists, storage.size());
        return !exists;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Removal is performed by scanning the internal list for a wrapper
     * whose {@code id} matches the supplied value.
     */
    @Override
    public boolean remove(long id) {
        boolean removed = storage.removeIf(w -> w.getId() == id);
        logger.debug("Remove wrapper id={}: removed={}, total={}", id, removed, storage.size());
        return removed;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Performs a linear scan over the internal list.
     */
    @Override
    public Optional<AbstractArrayWrapper<Integer>> findById(long id) {
        return storage.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
    }


    /**
     * {@inheritDoc}
     *
     * <p>Filters the internal list using the supplied specification and returns
     * a new unmodifiable list containing only the matching wrappers.
     */
    @Override
    public List<AbstractArrayWrapper<Integer>> findBySpecification(ArrayWrapperSpecification<Integer> specification) {
        List<AbstractArrayWrapper<Integer>> result = new ArrayList<>();
        for (AbstractArrayWrapper<Integer> wrapper : storage) {
            if (specification.test(wrapper)) {
                result.add(wrapper);
            }
        }
        logger.debug("findBySpecification returned {} wrappers", result.size());
        return Collections.unmodifiableList(result);
    }
}
