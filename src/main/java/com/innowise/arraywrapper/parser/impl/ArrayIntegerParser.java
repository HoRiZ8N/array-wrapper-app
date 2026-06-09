package com.innowise.arraywrapper.parser.impl;

import com.innowise.arraywrapper.parser.ArrayParser;

import java.util.Arrays;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of {@link ArrayParser} for {@code int} values.
 *
 * <p>Supported delimiters: comma ({@code ,}), semicolon ({@code ;})
 * and whitespace.
 */
public class ArrayIntegerParser implements ArrayParser<Integer> {

    private static final Logger logger = LogManager.getLogger(ArrayIntegerParser.class);

    private static final Pattern DELIMITER_REG_EXP = Pattern.compile("[,;\\s]+");

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer[] parse(String line) {
        if (line.isBlank()) {
            return new Integer[0];
        }

        String[] tokens = DELIMITER_REG_EXP.split(line.trim());

        Integer[] result = Arrays.stream(tokens)
                .filter(token -> !token.isBlank())
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        logger.debug("Parsed line '{}' into {} elements", line, result.length);

        return result;
    }
}