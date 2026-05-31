package com.innowise.arraywrapper.validator.impl;

import com.innowise.arraywrapper.validator.ArrayDataValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIntegerDataValidatorTest {

    private ArrayDataValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ArrayIntegerDataValidator();
    }

    @Test
    void isValid_shouldReturnTrue_whenLineIsBlank() {
        assertTrue(validator.isValid(""));
    }

    @Test
    void isValid_shouldReturnTrue_whenLineIsWhitespace() {
        assertTrue(validator.isValid("   "));
    }

    @Test
    void isValid_shouldReturnTrue_whenLineHasCommaSeparatedIntegers() {
        assertTrue(validator.isValid("1, 2, 3"));
    }

    @Test
    void isValid_shouldReturnTrue_whenLineHasSemicolonSeparatedIntegers() {
        assertTrue(validator.isValid("1; 2; 3"));
    }

    @Test
    void isValid_shouldReturnTrue_whenLineHasSpaceSeparatedIntegers() {
        assertTrue(validator.isValid("3 4 7"));
    }

    @Test
    void isValid_shouldReturnTrue_whenLineHasSingleInteger() {
        assertTrue(validator.isValid("42"));
    }

    @Test
    void isValid_shouldReturnTrue_whenLineHasNegativeIntegers() {
        assertTrue(validator.isValid("-1, -2, -3"));
    }

    @Test
    void isValid_shouldReturnFalse_whenLineHasLetterInToken() {
        assertFalse(validator.isValid("1y1 21 32"));
    }

    @Test
    void isValid_shouldReturnFalse_whenLineHasFloatToken() {
        assertFalse(validator.isValid("1, 2, 6..5, 77"));
    }

    @Test
    void isValid_shouldReturnFalse_whenLineHasMixedValidAndInvalidTokens() {
        assertFalse(validator.isValid("1, 2, x3, 6"));
    }
}