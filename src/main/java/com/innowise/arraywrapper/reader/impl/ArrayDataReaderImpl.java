package com.innowise.arraywrapper.reader.impl;

import com.innowise.arraywrapper.exception.ArrayWrapperException;
import com.innowise.arraywrapper.reader.ArrayDataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reads raw string data from a text file using {@link Files#lines} (Java 8+).
 */
public class ArrayDataReaderImpl implements ArrayDataReader {

    /**
     * {@inheritDoc}
     *
     * <p>Reads all lines from the file at the given path using
     * {@link Files#lines}.
     *
     * @throws ArrayWrapperException if the file does not exist or cannot be read
     */
    @Override
    public List<String> readLines(String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new ArrayWrapperException("Failed to read file: " + path);
        }
    }
}