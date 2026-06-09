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
 * never exposed directly — {@link #findAll()} returns an unmodifiable view.
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
     *
     * @throws IllegalArgumentException if {@code wrapper} is {@code null}
     */
    @Override
    public void add(AbstractArrayWrapper<Integer> wrapper) {
        if (wrapper == null) {
            logger.error("Attempted to add null wrapper to repository");
            throw new IllegalArgumentException("Wrapper must not be null");
        }
        storage.add(wrapper);
        logger.debug("Added wrapper id={} to repository, total={}", wrapper.getId(), storage.size());
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
        if (removed) {
            logger.debug("Removed wrapper id={} from repository, total={}", id, storage.size());
        } else {
            logger.warn("Wrapper id={} not found in repository, nothing removed", id);
        }
        return removed;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Performs a linear scan over the internal list.
     */
    @Override
    public Optional<AbstractArrayWrapper<Integer>> findById(long id) {
        Optional<AbstractArrayWrapper<Integer>> result = storage.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if (result.isPresent()) {
            logger.debug("Found wrapper id={}", id);
        } else {
            logger.debug("Wrapper id={} not found", id);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns an unmodifiable view; the underlying list order reflects
     * insertion order.
     */
    @Override
    public List<AbstractArrayWrapper<Integer>> findAll() {
        logger.debug("findAll called, returning {} wrappers", storage.size());
        return Collections.unmodifiableList(storage);
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
