package com.innowise.arraywrapper.factory.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;
import com.innowise.arraywrapper.factory.ArrayWrapperFactory;

/**
 * Factory implementation for creating {@link IntegerArrayWrapper} instances.
 *
 * <p>Encapsulates the creation logic of integer array wrappers.
 */
public class IntegerWrapperFactory implements ArrayWrapperFactory<Integer> {

    /**
     * Creates a new {@link IntegerArrayWrapper} from the provided array.
     *
     * @param array source array used to initialize the wrapper
     * @return new instance of {@link IntegerArrayWrapper}
     */
    @Override
    public AbstractArrayWrapper<Integer> create(Integer[] array) {
        return new IntegerArrayWrapper(array);
    }
}
