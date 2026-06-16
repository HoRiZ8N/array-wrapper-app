package com.innowise.arraywrapper.warehouse.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;
import com.innowise.arraywrapper.observer.ArrayWrapperObserver;
import com.innowise.arraywrapper.observer.impl.IntegerWarehouseObserver;
import com.innowise.arraywrapper.repository.impl.IntegerArrayWrapperRepository;
import com.innowise.arraywrapper.warehouse.ArrayStatistics;
import com.innowise.arraywrapper.warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IntegerWarehouseTest {

    private Warehouse<Integer> warehouse;
    private IntegerArrayWrapperRepository repository;

    @BeforeEach
    void setUp() {
        warehouse  = IntegerWarehouse.getInstance();
        repository = IntegerArrayWrapperRepository.getInstance();

        repository.findBySpecification(w -> true).stream()
                .map(AbstractArrayWrapper::getId)
                .toList()
                .forEach(id -> {
                    warehouse.remove(id);
                    repository.remove(id);
                });
    }

    @Test
    void getInstance_shouldReturnSameInstance_whenCalledMultipleTimes() {
        assertSame(IntegerWarehouse.getInstance(), IntegerWarehouse.getInstance());
    }

    @Test
    void recalculate_shouldStoreCorrectSum_whenWrapperIsNotEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3, 4, 5});
        warehouse.recalculate(wrapper);

        double sum = warehouse.getStatistics(wrapper.getId())
                .map(ArrayStatistics::getSum)
                .orElseThrow();
        assertEquals(15.0, sum);
    }

    @Test
    void recalculate_shouldStoreCorrectAverage_whenWrapperIsNotEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{10, 20, 30});
        warehouse.recalculate(wrapper);

        double avg = warehouse.getStatistics(wrapper.getId())
                .map(ArrayStatistics::getAverage)
                .orElseThrow();
        assertEquals(20.0, avg);
    }

    @Test
    void recalculate_shouldStoreCorrectMin_whenWrapperIsNotEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{5, 1, 8, 3});
        warehouse.recalculate(wrapper);

        double min = warehouse.getStatistics(wrapper.getId())
                .map(ArrayStatistics::getMin)
                .orElseThrow();
        assertEquals(1.0, min);
    }

    @Test
    void recalculate_shouldStoreCorrectMax_whenWrapperIsNotEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{5, 1, 8, 3});
        warehouse.recalculate(wrapper);

        double max = warehouse.getStatistics(wrapper.getId())
                .map(ArrayStatistics::getMax)
                .orElseThrow();
        assertEquals(8.0, max);
    }

    @Test
    void recalculate_shouldRemoveEntry_whenWrapperIsEmpty() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{});
        warehouse.recalculate(wrapper);
        assertFalse(warehouse.getStatistics(wrapper.getId()).isPresent());
    }

    @Test
    void recalculate_shouldUpdateStatistics_whenCalledAgainAfterChange() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        warehouse.recalculate(wrapper);
        wrapper.set(0, 10);
        warehouse.recalculate(wrapper);

        double sum = warehouse.getStatistics(wrapper.getId())
                .map(ArrayStatistics::getSum)
                .orElseThrow();
        assertEquals(15.0, sum);
    }

    @Test
    void getStatistics_shouldReturnEmpty_whenIdNotRegistered() {
        Optional<ArrayStatistics> result = warehouse.getStatistics(-999L);
        assertFalse(result.isPresent());
    }

    @Test
    void remove_shouldDeleteStatistics_whenIdExists() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        warehouse.recalculate(wrapper);
        warehouse.remove(wrapper.getId());
        assertFalse(warehouse.getStatistics(wrapper.getId()).isPresent());
    }

    @Test
    void observer_shouldTriggerRecalculation_whenElementIsChanged() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        repository.add(wrapper);
        warehouse.recalculate(wrapper);

        ArrayWrapperObserver observer = new IntegerWarehouseObserver();
        wrapper.addObserver(observer);
        wrapper.set(0, 10);

        double sum = warehouse.getStatistics(wrapper.getId())
                .map(ArrayStatistics::getSum)
                .orElseThrow();
        assertEquals(15.0, sum);
    }

    @Test
    void observer_shouldReflectMultipleChanges_whenSetCalledSeveralTimes() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        repository.add(wrapper);
        warehouse.recalculate(wrapper);
        wrapper.addObserver(new IntegerWarehouseObserver());

        wrapper.set(0, 10);
        wrapper.set(1, 20);
        wrapper.set(2, 30);

        double sum = warehouse.getStatistics(wrapper.getId())
                .map(ArrayStatistics::getSum)
                .orElseThrow();
        assertEquals(60.0, sum);
    }
}