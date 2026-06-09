package com.innowise.arraywrapper.comparator;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.service.ArrayMathService;
import com.innowise.arraywrapper.service.impl.ArrayMathServiceImpl;

import java.util.Comparator;

/**
 * Compares two {@link AbstractArrayWrapper} instances by the sum of their
 * elements in ascending order.
 *
 * <p>Delegates sum computation to {@link ArrayMathService}.
 * Empty wrappers have a sum of {@code 0.0}.
 *
 * @param <T> the numeric element type of the wrappers
 */
public class WrapperSumComparator<T extends Number> implements Comparator<AbstractArrayWrapper<T>> {

    private final ArrayMathService<T> mathService = new ArrayMathServiceImpl<>();

    @Override
    public int compare(AbstractArrayWrapper<T> a, AbstractArrayWrapper<T> b) {
        return Double.compare(
                mathService.sum(a).orElse(0.0),
                mathService.sum(b).orElse(0.0));
    }
}
