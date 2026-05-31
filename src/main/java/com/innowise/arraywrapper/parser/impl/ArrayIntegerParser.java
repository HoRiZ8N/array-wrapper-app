package com.innowise.arraywrapper.parser.impl;

import com.innowise.arraywrapper.parser.ArrayParser;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Implementation of {@link ArrayParser} for {@code int} values.
 *
 * <p>Supported delimiters: comma ({@code ,}), semicolon ({@code ;}),
 * dash ({@code -}), and whitespace.
 */
public class ArrayIntegerParser implements ArrayParser<Integer> {

    private static final Pattern DELIMITER = Pattern.compile("[,;\\s]+");

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer[] parse(String line) {
        if (line.isBlank()) {
            return new Integer[0];
        }

        String[] tokens = DELIMITER.split(line.trim());

        return Arrays.stream(tokens)
                .filter(token -> !token.isBlank())
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }
}