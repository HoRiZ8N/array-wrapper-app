package com.innowise.arraywrapper.specification;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;
import com.innowise.arraywrapper.repository.impl.IntegerArrayWrapperRepository;
import com.innowise.arraywrapper.specification.impl.StatSpecification;
import com.innowise.arraywrapper.warehouse.Warehouse;
import com.innowise.arraywrapper.warehouse.impl.IntegerWarehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.innowise.arraywrapper.specification.impl.StatSpecification.CompareMode.*;
import static com.innowise.arraywrapper.specification.impl.StatSpecification.StatType.*;
import static org.junit.jupiter.api.Assertions.*;

class SpecificationTest {

    private IntegerArrayWrapperRepository repository;
    private Warehouse<Integer> warehouse;

    // wrappers reused across tests
    private IntegerArrayWrapper small;   // [1, 2, 3]       sum=6,  avg=2,  min=1, max=3,  size=3
    private IntegerArrayWrapper medium;  // [10, 20, 30]    sum=60, avg=20, min=10, max=30, size=3
    private IntegerArrayWrapper large;   // [100, 200, 300] sum=600,avg=200,min=100,max=300,size=3
    private IntegerArrayWrapper single;  // [42]            sum=42, avg=42, min=42, max=42, size=1

    @BeforeEach
    void setUp() {
        repository = IntegerArrayWrapperRepository.getInstance();
        warehouse  = IntegerWarehouse.getInstance();

        repository.findBySpecification(w -> true).stream()
                .map(AbstractArrayWrapper::getId)
                .toList()
                .forEach(id -> {
                    warehouse.remove(id);
                    repository.remove(id);
                });

        small  = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        medium = new IntegerArrayWrapper(new Integer[]{10, 20, 30});
        large  = new IntegerArrayWrapper(new Integer[]{100, 200, 300});
        single = new IntegerArrayWrapper(new Integer[]{42});

        repository.add(small);
        repository.add(medium);
        repository.add(large);
        repository.add(single);

        warehouse.recalculate(small);
        warehouse.recalculate(medium);
        warehouse.recalculate(large);
        warehouse.recalculate(single);
    }

    // ── StatSpecification — SUM ───────────────────────────────────────────────

    @Test
    void sum_greaterThan_shouldReturnMatchingWrappers() {
        var spec = new StatSpecification<Integer>(SUM, GREATER_THAN, 50.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        // medium(60), large(600) match; small(6) and single(42) do not
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(w -> w.getId() == medium.getId()));
        assertTrue(result.stream().anyMatch(w -> w.getId() == large.getId()));
    }

    @Test
    void sum_lessThan_shouldReturnMatchingWrappers() {
        var spec = new StatSpecification<Integer>(SUM, LESS_THAN, 50.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        // small(6) and single(42) match
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(w -> w.getId() == small.getId()));
        assertTrue(result.stream().anyMatch(w -> w.getId() == single.getId()));
    }

    @Test
    void sum_equalTo_shouldReturnMatchingWrapper() {
        var spec = new StatSpecification<Integer>(SUM, EQUAL_TO, 6.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        assertEquals(1, result.size());
        assertEquals(small.getId(), result.get(0).getId());
    }

    // ── StatSpecification — AVERAGE ───────────────────────────────────────────

    @Test
    void average_greaterThan_shouldReturnMatchingWrappers() {
        var spec = new StatSpecification<Integer>(AVERAGE, GREATER_THAN, 20.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        // large(200) and single(42) match
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(w -> w.getId() == large.getId()));
        assertTrue(result.stream().anyMatch(w -> w.getId() == single.getId()));
    }

    @Test
    void average_equalTo_shouldReturnMatchingWrapper() {
        var spec = new StatSpecification<Integer>(AVERAGE, EQUAL_TO, 42.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        assertEquals(1, result.size());
        assertEquals(single.getId(), result.get(0).getId());
    }

    // ── StatSpecification — MIN ───────────────────────────────────────────────

    @Test
    void min_greaterThan_shouldReturnMatchingWrappers() {
        var spec = new StatSpecification<Integer>(MIN, GREATER_THAN, 9.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        // medium(min=10), large(min=100), single(min=42)
        assertEquals(3, result.size());
    }

    @Test
    void min_lessThan_shouldReturnMatchingWrapper() {
        var spec = new StatSpecification<Integer>(MIN, LESS_THAN, 5.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        // only small(min=1)
        assertEquals(1, result.size());
        assertEquals(small.getId(), result.get(0).getId());
    }

    // ── StatSpecification — MAX ───────────────────────────────────────────────

    @Test
    void max_greaterThan_shouldReturnMatchingWrappers() {
        var spec = new StatSpecification<Integer>(MAX, GREATER_THAN, 30.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        // large(max=300) and single(max=42)
        assertEquals(2, result.size());
    }

    // ── StatSpecification — COUNT ─────────────────────────────────────────────

    @Test
    void count_equalTo_shouldReturnMatchingWrappers() {
        var spec = new StatSpecification<Integer>(COUNT, EQUAL_TO, 3.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        // small, medium, large all have 3 elements
        assertEquals(3, result.size());
    }

    @Test
    void count_lessThan_shouldReturnMatchingWrapper() {
        var spec = new StatSpecification<Integer>(COUNT, LESS_THAN, 2.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        // only single has 1 element
        assertEquals(1, result.size());
        assertEquals(single.getId(), result.get(0).getId());
    }

    // ── Composed specifications (and / or) ────────────────────────────────────

    @Test
    void and_shouldMatchOnlyWhenBothSpecificationsSatisfied() {
        // sum > 50 AND max < 100  →  only medium (sum=60, max=30)
        var spec = new StatSpecification<Integer>(SUM, GREATER_THAN, 50.0)
                .and(new StatSpecification<>(MAX, LESS_THAN, 100.0));
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        assertEquals(1, result.size());
        assertEquals(medium.getId(), result.get(0).getId());
    }

    @Test
    void or_shouldMatchWhenEitherSpecificationSatisfied() {
        // sum < 10 OR sum > 500  →  small(6) and large(600)
        var spec = new StatSpecification<Integer>(SUM, LESS_THAN, 10.0)
                .or(new StatSpecification<>(SUM, GREATER_THAN, 500.0));
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(w -> w.getId() == small.getId()));
        assertTrue(result.stream().anyMatch(w -> w.getId() == large.getId()));
    }

    // ── Edge cases ────────────────────────────────────────────────────────────

    @Test
    void stat_shouldReturnEmpty_whenNoWrappersMatch() {
        var spec = new StatSpecification<Integer>(SUM, GREATER_THAN, 1_000_000.0);
        assertTrue(repository.findBySpecification(spec).isEmpty());
    }

    @Test
    void stat_shouldReturnFalse_whenWrapperNotInWarehouse() {
        IntegerArrayWrapper empty = new IntegerArrayWrapper(new Integer[]{});
        repository.add(empty);
        var spec = new StatSpecification<Integer>(SUM, EQUAL_TO, 0.0);
        List<AbstractArrayWrapper<Integer>> result = repository.findBySpecification(spec);
        assertTrue(result.stream().noneMatch(w -> w.getId() == empty.getId()));
    }
}