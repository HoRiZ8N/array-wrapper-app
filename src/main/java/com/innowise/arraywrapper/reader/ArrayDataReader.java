package com.innowise.arraywrapper.reader;

import com.innowise.arraywrapper.exception.ArrayWrapperException;

import java.util.List;

/**
 * Reads raw string data intended for array construction from an external source.
 */
public interface ArrayDataReader {

    /**
     * Reads all lines from the source identified by the given path.
     *
     * @param path path to the data source
     * @return list of raw lines; never {@code null}, may be empty
     * @throws ArrayWrapperException if the source cannot be read
     */
    List<String> readLines(String path) throws ArrayWrapperException;
}