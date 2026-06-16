package com.innowise.arraywrapper.parser.impl;

import com.innowise.arraywrapper.parser.ArrayParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ArrayIntegerParserTest {

    private ArrayParser<Integer> parser;

    @BeforeEach
    void setUp() {
        parser = new ArrayIntegerParser();
    }

    static Stream<Arguments> parseCases() {
        return Stream.of(
                Arguments.of("blank line", "", new Integer[]{}),
                Arguments.of("whitespace line", "   ", new Integer[]{}),
                Arguments.of("comma separated", "1, 2, 3", new Integer[]{1, 2, 3}),
                Arguments.of("semicolon separated", "1; 2; 3", new Integer[]{1, 2, 3}),
                Arguments.of("space separated", "3 4 7", new Integer[]{3, 4, 7}),
                Arguments.of("single integer", "42", new Integer[]{42}),
                Arguments.of("negative integers", "-1, -2, -3", new Integer[]{-1, -2, -3})
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("parseCases")
    void parse_shouldReturnExpectedArray(String description, String line, Integer[] expected) {
        assertArrayEquals(expected, parser.parse(line));
    }

    @Test
    void parse_shouldReturnNonNullArray_whenLineIsBlank() {
        assertNotNull(parser.parse(""));
    }
}