package com.innowise.arraywrapper.validator;

/**
 * Validates raw string lines intended for array construction.
 */
public interface ArrayDataValidator {

    /**
     * Checks whether the given line contains valid data for array construction.
     *
     * <p>A line is considered valid if it is blank (produces an empty array)
     * or every token separated by a delimiter is a valid value.
     *
     * @param line the raw line to validate
     * @return {@code true} if the line is valid, {@code false} otherwise
     */
    boolean isValid(String line);
}