package com.innowise.arraywrapper.app;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.factory.ArrayWrapperFactory;
import com.innowise.arraywrapper.factory.impl.IntegerWrapperFactory;
import com.innowise.arraywrapper.parser.ArrayParser;
import com.innowise.arraywrapper.parser.impl.ArrayIntegerParser;
import com.innowise.arraywrapper.reader.ArrayDataReader;
import com.innowise.arraywrapper.reader.impl.ArrayDataReaderImpl;
import com.innowise.arraywrapper.service.ArrayMathService;
import com.innowise.arraywrapper.service.ArrayMinMaxService;
import com.innowise.arraywrapper.service.ArraySortService;
import com.innowise.arraywrapper.service.impl.ArrayMathServiceImpl;
import com.innowise.arraywrapper.service.impl.ArrayMinMaxServiceImpl;
import com.innowise.arraywrapper.service.impl.ArraySortServiceImpl;
import com.innowise.arraywrapper.validator.ArrayDataValidator;
import com.innowise.arraywrapper.validator.impl.ArrayIntegerDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ArrayWrapperApplication {

    private static final Logger logger = LogManager.getLogger(ArrayWrapperApplication.class);
    private static final String DATA_FILE_PATH = "data/arrays.txt";

    public static void main(String[] args) {
        logger.info("Application started");

        ArrayDataReader reader = new ArrayDataReaderImpl();
        ArrayDataValidator validator = new ArrayIntegerDataValidator();
        ArrayParser<Integer> parser = new ArrayIntegerParser();
        ArrayWrapperFactory<Integer> factory = new IntegerWrapperFactory();
        ArrayMinMaxService<Integer> minMaxService = new ArrayMinMaxServiceImpl<>();
        ArrayMathService<Integer> mathService = new ArrayMathServiceImpl<>();
        ArraySortService<Integer> sortService = new ArraySortServiceImpl<>(factory);

        List<String> lines = reader.readLines(DATA_FILE_PATH);
        logger.info("Read {} lines from file", lines.size());

        for (String line : lines) {
            if (!validator.isValid(line)) {
                logger.warn("Skipping invalid line: '{}'", line);
                continue;
            }

            Integer[] elements = parser.parse(line);
            AbstractArrayWrapper<Integer> wrapper = factory.create(elements);

            logger.info("Processing wrapper with {} elements", wrapper.size());

            minMaxService.findMin(wrapper).ifPresent(min -> logger.info("Min: {}", min));
            minMaxService.findMax(wrapper).ifPresent(max -> logger.info("Max: {}", max));
            mathService.sum(wrapper).ifPresent(sum -> logger.info("Sum: {}", sum));
            mathService.average(wrapper).ifPresent(avg -> logger.info("Average: {}", avg));

            AbstractArrayWrapper<Integer> bubbleSorted = sortService.bubbleSort(wrapper);
            logger.info("Bubble sorted: {}", bubbleSorted);

            AbstractArrayWrapper<Integer> quickSorted = sortService.quickSort(wrapper);
            logger.info("Quick sorted: {}", quickSorted);
        }

        logger.info("Application finished");
    }
}