package com.innowise.arraywrapper.specification.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.specification.ArrayWrapperSpecification;
import com.innowise.arraywrapper.warehouse.ArrayStatistics;
import com.innowise.arraywrapper.warehouse.impl.IntegerWarehouse;

/**
 * Specification that filters wrappers by a statistical value stored in
 * {@link IntegerWarehouse}.
 *
 * <p>Combines a {@link StatType} (which value to read) with a
 * {@link CompareMode} (how to compare it against a threshold) into one
 * reusable predicate.
 *
 * <p>Requires that statistics for the tested wrapper have already been
 * computed and stored in {@link IntegerWarehouse} via
 * {@link IntegerWarehouse#recalculate}. If no statistics are found for a
 * wrapper, {@link #test} returns {@code false}.
 *
 * <p>Example — find all arrays whose sum is greater than 100:
 * <pre>{@code
 * var spec = new StatSpecification<Integer>(
 *         StatSpecification.StatType.SUM,
 *         StatSpecification.CompareMode.GREATER_THAN,
 *         100.0);
 * }</pre>
 *
 * @param <T> the element type of the wrappers to be tested
 */
public class StatSpecification<T>
        implements ArrayWrapperSpecification<T> {

    /**
     * Which statistical value to read from {@link IntegerWarehouse}.
     */
    public enum StatType {
        SUM,
        AVERAGE,
        MIN,
        MAX,
        COUNT
    }

    /**
     * How the value is compared against the threshold.
     */
    public enum CompareMode {
        GREATER_THAN,
        LESS_THAN,
        EQUAL_TO
    }

    private final StatType    statType;
    private final CompareMode compareMode;
    private final double      threshold;

    /**
     * Constructs a new statistical specification.
     *
     * @param statType    which value to read from the warehouse
     * @param compareMode how to compare the value against {@code threshold}
     * @param threshold   the value to compare against
     */
    public StatSpecification(StatType statType, CompareMode compareMode, double threshold) {
        this.statType    = statType;
        this.compareMode = compareMode;
        this.threshold   = threshold;
    }

    @Override
    public boolean test(AbstractArrayWrapper<T> wrapper) {
        if (statType == StatType.COUNT) {
            return compare(wrapper.size());
        }

        return IntegerWarehouse.getInstance()
                .getStatistics(wrapper.getId())
                .map(this::extract)
                .map(this::compare)
                .orElse(false);
    }

    private double extract(ArrayStatistics stats) {
        return switch (statType) {
            case SUM     -> stats.sum();
            case AVERAGE -> stats.average();
            case MIN     -> stats.min();
            case MAX     -> stats.max();
            case COUNT   -> 0.0; // handled separately above
        };
    }

    private boolean compare(double value) {
        return switch (compareMode) {
            case GREATER_THAN -> value >  threshold;
            case LESS_THAN    -> value <  threshold;
            case EQUAL_TO     -> Double.compare(value, threshold) == 0;
        };
    }
}
