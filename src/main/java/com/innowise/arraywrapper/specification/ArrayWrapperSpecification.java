package com.innowise.arraywrapper.specification;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;

/**
 * Specification that tests whether a given {@link AbstractArrayWrapper}
 * satisfies a particular condition.
 *
 * <p>Implementations encode a single search criterion. Specifications can be
 * composed with {@link #and(ArrayWrapperSpecification)} and
 * {@link #or(ArrayWrapperSpecification)} to build compound predicates.
 *
 * @param <T> the element type of the wrappers to be tested
 */
public interface ArrayWrapperSpecification<T> {

    /**
     * Returns {@code true} if the given wrapper satisfies this specification.
     *
     * @param wrapper the wrapper to test; must not be {@code null}
     * @return {@code true} if the wrapper matches, {@code false} otherwise
     */
    boolean test(AbstractArrayWrapper<T> wrapper);

    /**
     * Returns a composed specification that represents a logical AND of this
     * specification and the {@code other}.
     *
     * @param other the other specification; must not be {@code null}
     * @return a new specification that is satisfied only when both are satisfied
     */
    default ArrayWrapperSpecification<T> and(ArrayWrapperSpecification<T> other) {
        return wrapper -> this.test(wrapper) && other.test(wrapper);
    }

    /**
     * Returns a composed specification that represents a logical OR of this
     * specification and the {@code other}.
     *
     * @param other the other specification; must not be {@code null}
     * @return a new specification that is satisfied when at least one is satisfied
     */
    default ArrayWrapperSpecification<T> or(ArrayWrapperSpecification<T> other) {
        return wrapper -> this.test(wrapper) || other.test(wrapper);
    }
}
