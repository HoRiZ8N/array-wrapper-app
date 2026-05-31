package com.innowise.arraywrapper.parser.impl;

import com.innowise.arraywrapper.parser.ArrayParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIntegerParserTest {

    private ArrayParser<Integer> parser;

    @BeforeEach
    void setUp() {
        parser = new ArrayIntegerParser();
    }

    @Test
    void parse_shouldReturnEmptyArray_whenLineIsBlank() {
        assertArrayEquals(new Integer[]{}, parser.parse(""));
    }

    @Test
    void parse_shouldReturnEmptyArray_whenLineIsWhitespace() {
        assertArrayEquals(new Integer[]{}, parser.parse("   "));
    }

    @Test
    void parse_shouldParseCommaSeparatedIntegers() {
        assertArrayEquals(new Integer[]{1, 2, 3}, parser.parse("1, 2, 3"));
    }

    @Test
    void parse_shouldParseSemicolonSeparatedIntegers() {
        assertArrayEquals(new Integer[]{1, 2, 3}, parser.parse("1; 2; 3"));
    }

    @Test
    void parse_shouldParseSpaceSeparatedIntegers() {
        assertArrayEquals(new Integer[]{3, 4, 7}, parser.parse("3 4 7"));
    }

    @Test
    void parse_shouldParseSingleInteger() {
        assertArrayEquals(new Integer[]{42}, parser.parse("42"));
    }

    @Test
    void parse_shouldParseNegativeIntegers() {
        assertArrayEquals(new Integer[]{-1, -2, -3}, parser.parse("-1, -2, -3"));
    }

    @Test
    void parse_shouldReturnNonNullArray_whenLineIsBlank() {
        assertNotNull(parser.parse(""));
    }
}