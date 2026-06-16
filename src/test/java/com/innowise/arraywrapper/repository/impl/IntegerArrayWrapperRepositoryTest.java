package com.innowise.arraywrapper.repository.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IntegerArrayWrapperRepositoryTest {

    private IntegerArrayWrapperRepository repository;

    /**
     * Clear all entries before each test.
     * Because the repository is a Singleton its internal state would otherwise
     * leak between tests — we remove every stored wrapper by id.
     */
    @BeforeEach
    void setUp() {
        repository = IntegerArrayWrapperRepository.getInstance();
        repository.findBySpecification(w -> true).stream()
                .map(AbstractArrayWrapper::getId)
                .toList()
                .forEach(repository::remove);
    }

    // ── getInstance ───────────────────────────────────────────────────────────

    @Test
    void getInstance_shouldReturnSameInstance_whenCalledMultipleTimes() {
        IntegerArrayWrapperRepository first  = IntegerArrayWrapperRepository.getInstance();
        IntegerArrayWrapperRepository second = IntegerArrayWrapperRepository.getInstance();
        assertSame(first, second);
    }

    // ── add ───────────────────────────────────────────────────────────────────

    @Test
    void add_shouldIncreaseSize_whenWrapperIsAdded() {
        repository.add(new IntegerArrayWrapper(new Integer[]{1, 2, 3}));
        assertEquals(1, repository.findBySpecification(w -> true).size());
    }

    @Test
    void add_shouldStoreMultipleWrappers_whenAddedSequentially() {
        repository.add(new IntegerArrayWrapper(new Integer[]{1}));
        repository.add(new IntegerArrayWrapper(new Integer[]{2}));
        repository.add(new IntegerArrayWrapper(new Integer[]{3}));
        assertEquals(3, repository.findBySpecification(w -> true).size());
    }

    // ── remove ────────────────────────────────────────────────────────────────

    @Test
    void remove_shouldReturnTrue_whenWrapperWithGivenIdExists() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{10, 20});
        repository.add(wrapper);
        assertTrue(repository.remove(wrapper.getId()));
    }

    @Test
    void remove_shouldDecreaseSize_whenWrapperIsRemoved() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        repository.add(wrapper);
        repository.remove(wrapper.getId());
        assertEquals(0, repository.findBySpecification(w -> true).size());
    }

    @Test
    void remove_shouldReturnFalse_whenNoWrapperWithGivenIdExists() {
        assertFalse(repository.remove(-999L));
    }

    @Test
    void remove_shouldNotAffectOtherWrappers_whenOnlyOneIsRemoved() {
        IntegerArrayWrapper a = new IntegerArrayWrapper(new Integer[]{1});
        IntegerArrayWrapper b = new IntegerArrayWrapper(new Integer[]{2});
        repository.add(a);
        repository.add(b);

        repository.remove(a.getId());

        List<AbstractArrayWrapper<Integer>> remaining = repository.findBySpecification(w -> true);
        assertEquals(1, remaining.size());
        assertEquals(b.getId(), remaining.get(0).getId());
    }

    // ── findById ──────────────────────────────────────────────────────────────

    @Test
    void findById_shouldReturnWrapper_whenIdExists() {
        IntegerArrayWrapper wrapper = new IntegerArrayWrapper(new Integer[]{5, 6, 7});
        repository.add(wrapper);

        Optional<AbstractArrayWrapper<Integer>> result = repository.findById(wrapper.getId());

        assertTrue(result.isPresent());
        assertSame(wrapper, result.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenIdDoesNotExist() {
        Optional<AbstractArrayWrapper<Integer>> result = repository.findById(-1L);
        assertFalse(result.isPresent());
    }
}
