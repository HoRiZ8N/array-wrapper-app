package com.innowise.arraywrapper.warehouse;

/**
 * Immutable snapshot of statistical values computed for one array wrapper.
 *
 * <p>Holds sum, average, minimum, and maximum — all as {@code double} so that
 * the same record can describe wrappers of any numeric type.
 */
public class ArrayStatistics {

    private final double sum;
    private final double average;
    private final double min;
    private final double max;

    /**
     * Constructs a new statistics snapshot.
     *
     * @param sum     sum of all elements
     * @param average arithmetic mean of all elements
     * @param min     smallest element
     * @param max     largest element
     */
    public ArrayStatistics(double sum, double average, double min, double max) {
        this.sum     = sum;
        this.average = average;
        this.min     = min;
        this.max     = max;
    }

    public double getSum()     { return sum; }
    public double getAverage() { return average; }
    public double getMin()     { return min; }
    public double getMax()     { return max; }

    @Override
    public String toString() {
        return "ArrayStatistics{sum=" + sum
                + ", average=" + average
                + ", min=" + min
                + ", max=" + max + "}";
    }
}
