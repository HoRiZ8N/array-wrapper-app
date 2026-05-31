package com.innowise.arraywrapper.reader.impl;

import com.innowise.arraywrapper.exception.ArrayWrapperException;
import com.innowise.arraywrapper.reader.ArrayDataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reads raw string data from a text file using {@link Files#lines} (Java 8+).
 */
public class ArrayDataReaderImpl implements ArrayDataReader {

    private static final Logger logger = LogManager.getLogger(ArrayDataReaderImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> readLines(String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            logger.info("Reading file: {}", path);
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Failed to read file: {}", path, e);
            throw new ArrayWrapperException("Failed to read file: " + path);
        }
    }
}