package com.innowise.arraywrapper.comparator;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;
import com.innowise.arraywrapper.service.WrapperSortService;
import com.innowise.arraywrapper.service.impl.WrapperSortServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WrapperComparatorTest {

    private WrapperSortService<Integer> sortService;

    // [3, 1, 2]  id наименьший из трёх (создаётся первым)
    private IntegerArrayWrapper w1;
    // [10, 20]   id средний
    private IntegerArrayWrapper w2;
    // [5, 5, 5, 5] id наибольший
    private IntegerArrayWrapper w3;

    private List<AbstractArrayWrapper<Integer>> wrappers;

    @BeforeEach
    void setUp() {
        sortService = new WrapperSortServiceImpl<>();
        w1 = new IntegerArrayWrapper(new Integer[]{3, 1, 2});   // sum=6,  size=3, first=3
        w2 = new IntegerArrayWrapper(new Integer[]{10, 20});    // sum=30, size=2, first=10
        w3 = new IntegerArrayWrapper(new Integer[]{5, 5, 5, 5}); // sum=20, size=4, first=5
        wrappers = List.of(w3, w1, w2); // intentionally shuffled
    }

    // ── sort does not mutate the original list ────────────────────────────────

    @Test
    void sort_shouldNotModifyOriginalList() {
        List<AbstractArrayWrapper<Integer>> original = List.of(w3, w1, w2);
        sortService.sort(original, new WrapperIdComparator<>());
        assertEquals(w3.getId(), original.get(0).getId());
    }

    // ── WrapperIdComparator ───────────────────────────────────────────────────

    @Test
    void sortById_shouldOrderByIdAscending() {
        List<AbstractArrayWrapper<Integer>> sorted =
                sortService.sort(wrappers, new WrapperIdComparator<>());

        assertTrue(sorted.get(0).getId() < sorted.get(1).getId());
        assertTrue(sorted.get(1).getId() < sorted.get(2).getId());
    }

    @Test
    void sortById_reversed_shouldOrderByIdDescending() {
        List<AbstractArrayWrapper<Integer>> sorted =
                sortService.sort(wrappers, new WrapperIdComparator<Integer>().reversed());

        assertTrue(sorted.get(0).getId() > sorted.get(1).getId());
        assertTrue(sorted.get(1).getId() > sorted.get(2).getId());
    }

    // ── WrapperSizeComparator ─────────────────────────────────────────────────

    @Test
    void sortBySize_shouldOrderBySizeAscending() {
        // w2(size=2), w1(size=3), w3(size=4)
        List<AbstractArrayWrapper<Integer>> sorted =
                sortService.sort(wrappers, new WrapperSizeComparator<>());

        assertEquals(2, sorted.get(0).size());
        assertEquals(3, sorted.get(1).size());
        assertEquals(4, sorted.get(2).size());
    }

    @Test
    void sortBySize_reversed_shouldOrderBySizeDescending() {
        List<AbstractArrayWrapper<Integer>> sorted =
                sortService.sort(wrappers, new WrapperSizeComparator<Integer>().reversed());

        assertEquals(4, sorted.get(0).size());
        assertEquals(3, sorted.get(1).size());
        assertEquals(2, sorted.get(2).size());
    }

    // ── WrapperFirstElementComparator ─────────────────────────────────────────

    @Test
    void sortByFirstElement_shouldOrderByFirstElementAscending() {
        // w1.first=3, w2.first=10, w3.first=5  →  w1(3), w3(5), w2(10)
        List<AbstractArrayWrapper<Integer>> sorted =
                sortService.sort(wrappers, new WrapperFirstElementComparator<>());

        assertEquals(3,  sorted.get(0).get(0));
        assertEquals(5,  sorted.get(1).get(0));
        assertEquals(10, sorted.get(2).get(0));
    }

    @Test
    void sortByFirstElement_shouldPlaceEmptyWrappersFirst() {
        IntegerArrayWrapper empty = new IntegerArrayWrapper(new Integer[]{});
        List<AbstractArrayWrapper<Integer>> withEmpty = List.of(w1, empty, w2);

        List<AbstractArrayWrapper<Integer>> sorted =
                sortService.sort(withEmpty, new WrapperFirstElementComparator<>());

        assertTrue(sorted.get(0).isEmpty());
    }

    // ── WrapperSumComparator ──────────────────────────────────────────────────

    @Test
    void sortBySum_shouldOrderBySumAscending() {
        // w1 sum=6, w3 sum=20, w2 sum=30
        List<AbstractArrayWrapper<Integer>> sorted =
                sortService.sort(wrappers, new WrapperSumComparator<>());

        assertEquals(w1.getId(), sorted.get(0).getId());
        assertEquals(w3.getId(), sorted.get(1).getId());
        assertEquals(w2.getId(), sorted.get(2).getId());
    }

    @Test
    void sortBySum_reversed_shouldOrderBySumDescending() {
        List<AbstractArrayWrapper<Integer>> sorted =
                sortService.sort(wrappers, new WrapperSumComparator<Integer>().reversed());

        assertEquals(w2.getId(), sorted.get(0).getId());
        assertEquals(w3.getId(), sorted.get(1).getId());
        assertEquals(w1.getId(), sorted.get(2).getId());
    }

    // ── Chained comparators ───────────────────────────────────────────────────

    @Test
    void sortBySizeThenById_shouldApplySecondaryOrderWhenSizesAreEqual() {
        // w1 and w3 have different sizes; w1(size=3) and a clone with size=3 tie on size → break by id
        IntegerArrayWrapper twin = new IntegerArrayWrapper(new Integer[]{99, 99, 99}); // size=3, id > w1
        List<AbstractArrayWrapper<Integer>> list = List.of(twin, w1, w2);

        List<AbstractArrayWrapper<Integer>> sorted = sortService.sort(list,
                new WrapperSizeComparator<Integer>()
                        .thenComparing(new WrapperIdComparator<>()));

        // w2(size=2) first, then w1(size=3, smaller id) before twin(size=3, larger id)
        assertEquals(w2.getId(),   sorted.get(0).getId());
        assertEquals(w1.getId(),   sorted.get(1).getId());
        assertEquals(twin.getId(), sorted.get(2).getId());
    }
}
