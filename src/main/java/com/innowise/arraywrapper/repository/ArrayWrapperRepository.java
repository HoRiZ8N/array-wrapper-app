package com.innowise.arraywrapper.repository;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.specification.ArrayWrapperSpecification;

import java.util.List;
import java.util.Optional;

/**
 * Repository for storing and retrieving {@link AbstractArrayWrapper} instances.
 *
 * <p>Provides basic CRUD-like operations: add, remove, find by id,
 * and retrieve all stored wrappers.
 *
 * @param <T> the element type of the stored wrappers
 */
public interface ArrayWrapperRepository<T> {

    /**
     * Adds a wrapper to the repository.
     *
     * @param wrapper the wrapper to add; must not be {@code null}
     */
    void add(AbstractArrayWrapper<T> wrapper);

    /**
     * Removes a wrapper from the repository by its id.
     *
     * @param id the id of the wrapper to remove
     * @return {@code true} if a wrapper with the given id was found and removed,
     *         {@code false} otherwise
     */
    boolean remove(long id);

    /**
     * Finds a wrapper by its id.
     *
     * @param id the id to look up
     * @return an {@link Optional} containing the wrapper if found,
     *         or an empty {@link Optional} otherwise
     */
    Optional<AbstractArrayWrapper<T>> findById(long id);

    /**
     * Returns all wrappers that satisfy the given specification.
     *
     * @param specification the predicate to apply; must not be {@code null}
     * @return list of matching wrappers; empty list if none match
     */
    List<AbstractArrayWrapper<T>> findBySpecification(ArrayWrapperSpecification<T> specification);
}
