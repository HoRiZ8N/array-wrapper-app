package com.innowise.arraywrapper.app;

import com.innowise.arraywrapper.comparator.WrapperFirstElementComparator;
import com.innowise.arraywrapper.comparator.WrapperIdComparator;
import com.innowise.arraywrapper.comparator.WrapperSizeComparator;
import com.innowise.arraywrapper.comparator.WrapperSumComparator;
import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.entity.IntegerArrayWrapper;
import com.innowise.arraywrapper.observer.ArrayWrapperObserver;
import com.innowise.arraywrapper.observer.impl.IntegerWarehouseObserver;
import com.innowise.arraywrapper.repository.impl.IntegerArrayWrapperRepository;
import com.innowise.arraywrapper.service.WrapperSortService;
import com.innowise.arraywrapper.service.impl.WrapperSortServiceImpl;
import com.innowise.arraywrapper.specification.impl.StatSpecification;
import com.innowise.arraywrapper.warehouse.ArrayStatistics;
import com.innowise.arraywrapper.warehouse.Warehouse;
import com.innowise.arraywrapper.warehouse.impl.IntegerWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.innowise.arraywrapper.specification.impl.StatSpecification.CompareMode.*;
import static com.innowise.arraywrapper.specification.impl.StatSpecification.StatType.*;

public class ArrayWrapperApplication {

    private static final Logger logger = LogManager.getLogger(ArrayWrapperApplication.class);

    public static void main(String[] args) {
        logger.info("=== ArrayWrapper Application started ===");

        IntegerArrayWrapperRepository repository = IntegerArrayWrapperRepository.getInstance();
        Warehouse<Integer> warehouse = IntegerWarehouse.getInstance();
        ArrayWrapperObserver observer = new IntegerWarehouseObserver();
        WrapperSortService<Integer> sortService = new WrapperSortServiceImpl<>();

        IntegerArrayWrapper w1 = new IntegerArrayWrapper(new Integer[]{1, 2, 3});
        IntegerArrayWrapper w2 = new IntegerArrayWrapper(new Integer[]{10, 20, 30, 40});
        IntegerArrayWrapper w3 = new IntegerArrayWrapper(new Integer[]{100, 200});

        for (IntegerArrayWrapper w : List.of(w1, w2, w3)) {
            repository.add(w);
            warehouse.recalculate(w);
            w.addObserver(observer);
        }

        logger.info("Registered {} wrappers", repository.findAll().size());
        repository.findAll().forEach(w -> logger.info("  {}", w));

        logger.info("--- Warehouse statistics ---");
        repository.findAll().forEach(w ->
                warehouse.getStatistics(w.getId()).ifPresent(s ->
                        logger.info("  id={} -> {}", w.getId(), s)));

        logger.info("--- Changing w1[0] from 1 to 99 ---");
        w1.set(0, 99);
        warehouse.getStatistics(w1.getId()).ifPresent(s ->
                logger.info("  w1 recalculated -> {}", s));

        logger.info("--- Find by id={} ---", w2.getId());
        repository.findById(w2.getId())
                .ifPresent(w -> logger.info("  found: {}", w));

        logger.info("--- Find all with sum > 50 ---");
        repository.findBySpecification(new StatSpecification<>(SUM, GREATER_THAN, 50.0))
                .forEach(w -> logger.info("  found: {}", w));

        logger.info("--- Find all with count == 2 ---");
        repository.findBySpecification(new StatSpecification<>(COUNT, EQUAL_TO, 2.0))
                .forEach(w -> logger.info("  found: {}", w));

        logger.info("--- Find with sum > 5 AND max < 50 ---");
        repository.findBySpecification(
                new StatSpecification<Integer>(SUM, GREATER_THAN, 5.0)
                        .and(new StatSpecification<>(MAX, LESS_THAN, 50.0)))
                .forEach(w -> logger.info("  found: {}", w));

        List<AbstractArrayWrapper<Integer>> all = repository.findAll();

        logger.info("--- Sort by id ---");
        sortService.sort(all, new WrapperIdComparator<>())
                .forEach(w -> logger.info("  id={}", w.getId()));

        logger.info("--- Sort by size descending ---");
        sortService.sort(all, new WrapperSizeComparator<Integer>().reversed())
                .forEach(w -> logger.info("  id={} size={}", w.getId(), w.size()));

        logger.info("--- Sort by first element ---");
        sortService.sort(all, new WrapperFirstElementComparator<>())
                .forEach(w -> logger.info("  id={} first={}", w.getId(), w.get(0)));

        logger.info("--- Sort by sum ---");
        sortService.sort(all, new WrapperSumComparator<>())
                .forEach(w -> {
                    double sum = warehouse.getStatistics(w.getId())
                            .map(ArrayStatistics::getSum)
                            .orElse(0.0);
                    logger.info("  id={} sum={}", w.getId(), sum);
                });

        logger.info("--- Remove w3 from repository ---");
        repository.remove(w3.getId());
        warehouse.remove(w3.getId());
        logger.info("  remaining: {} wrappers", repository.findAll().size());

        logger.info("=== Application finished ===");
    }
}
