package com.innowise.arraywrapper.reader.impl;

import com.innowise.arraywrapper.exception.ArrayWrapperException;
import com.innowise.arraywrapper.reader.ArrayDataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayDataReaderImplTest {

    private ArrayDataReader reader;

    @BeforeEach
    void setUp() {
        reader = new ArrayDataReaderImpl();
    }

    @Test
    void readLines_shouldReturnAllLines_whenFileHasMultipleLines() throws IOException {
        Path file = Files.createTempFile("test", ".txt");
        Files.writeString(file, "1, 2, 3\n4, 5, 6\n7, 8, 9");

        List<String> lines = reader.readLines(file.toString());

        assertEquals(3, lines.size());
        assertEquals("1, 2, 3", lines.get(0));
        assertEquals("4, 5, 6", lines.get(1));
        assertEquals("7, 8, 9", lines.get(2));

        Files.deleteIfExists(file);
    }

    @Test
    void readLines_shouldReturnEmptyList_whenFileIsEmpty() throws IOException {
        Path file = Files.createTempFile("test", ".txt");

        List<String> lines = reader.readLines(file.toString());

        assertTrue(lines.isEmpty());

        Files.deleteIfExists(file);
    }

    @Test
    void readLines_shouldReturnEmptyLines_whenFileHasBlankLines() throws IOException {
        Path file = Files.createTempFile("test", ".txt");
        Files.writeString(file, "1, 2, 3\n\n4, 5, 6");

        List<String> lines = reader.readLines(file.toString());

        assertEquals(3, lines.size());
        assertEquals("", lines.get(1));

        Files.deleteIfExists(file);
    }

    @Test
    void readLines_shouldThrowArrayWrapperException_whenFileDoesNotExist() {
        assertThrows(ArrayWrapperException.class,
                () -> reader.readLines("non/existent/path/file.txt"));
    }
}