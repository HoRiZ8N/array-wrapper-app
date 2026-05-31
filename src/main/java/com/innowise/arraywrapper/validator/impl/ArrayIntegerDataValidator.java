package com.innowise.arraywrapper.validator.impl;

import com.innowise.arraywrapper.validator.ArrayDataValidator;

import java.util.regex.Pattern;

/**
 * {@inheritDoc}
 *
 * <p>This implementation is specifically designed for integer data validation.
 *
 * <p>Supported delimiters: comma ({@code ,}), semicolon ({@code ;}),
 * dash ({@code -}), and whitespace.
 */
public class ArrayIntegerDataValidator implements ArrayDataValidator {

    private static final Pattern DELIMITER = Pattern.compile("[,;\\s]+");
    private static final Pattern INTEGER = Pattern.compile("-?\\d+");

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String line) {
        if (line.isBlank()) {
            return true;
        }

        String[] tokens = DELIMITER.split(line.trim());

        for (String token : tokens) {
            if (!token.isBlank() && !INTEGER.matcher(token).matches()) {
                return false;
            }
        }

        return true;
    }
}