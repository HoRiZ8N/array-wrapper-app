package com.innowise.arraywrapper.warehouse;

/**
 * Immutable snapshot of statistical values computed for one array wrapper.
 *
 * <p>Holds sum, average, minimum, and maximum — all as {@code double} so that
 * the same record can describe wrappers of any numeric type.
 *
 * @param sum     sum of all elements
 * @param average arithmetic mean of all elements
 * @param min     smallest element
 * @param max     largest element
 */
public record ArrayStatistics(double sum, double average, double min, double max) {}