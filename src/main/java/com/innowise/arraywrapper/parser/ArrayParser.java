package com.innowise.arraywrapper.parser;

/**
 * Parses a raw string line into an array of elements.
 *
 * @param <T> the type of elements in the resulting array
 */
public interface ArrayParser<T> {

    /**
     * Parses the given line into an array of elements.
     *
     * <p>A blank line produces an empty array.
     *
     * @param line the raw line to parse
     * @return array of parsed elements; never {@code null}
     */
    T[] parse(String line);
}