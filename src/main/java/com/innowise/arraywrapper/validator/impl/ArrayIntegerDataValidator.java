package com.innowise.arraywrapper.validator.impl;

import com.innowise.arraywrapper.validator.ArrayDataValidator;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of {@link ArrayDataValidator} for {@code int} values.
 *
 * <p>Supported delimiters: comma ({@code ,}), semicolon ({@code ;})
 * and whitespace.
 */
public class ArrayIntegerDataValidator implements ArrayDataValidator {

    private static final Logger logger = LogManager.getLogger(ArrayIntegerDataValidator.class);

    private static final Pattern DELIMITER_REG_EXP = Pattern.compile("[,;\\s]+");
    private static final Pattern INTEGER_REG_EXP = Pattern.compile("-?\\d+");

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String line) {
        if (line.isBlank()) {
            return true;
        }

        String[] tokens = DELIMITER_REG_EXP.split(line.trim());

        for (String token : tokens) {
            if (!token.isBlank() && !INTEGER_REG_EXP.matcher(token).matches()) {
                logger.warn("Invalid token found: '{}'", token);
                return false;
            }
        }

        return true;
    }
}