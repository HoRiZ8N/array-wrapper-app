package com.innowise.arraywrapper.warehouse.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.service.ArrayMathService;
import com.innowise.arraywrapper.service.ArrayMinMaxService;
import com.innowise.arraywrapper.service.impl.ArrayMathServiceImpl;
import com.innowise.arraywrapper.service.impl.ArrayMinMaxServiceImpl;
import com.innowise.arraywrapper.warehouse.ArrayStatistics;
import com.innowise.arraywrapper.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Singleton implementation of {@link Warehouse} for {@link Integer} element wrappers.
 *
 * <p>Delegates sum and average computation to {@link ArrayMathService}, and
 * min/max lookup to {@link ArrayMinMaxService}; no arithmetic is performed
 * directly in this class.
 *
 * <p>Uses classic (non-thread-safe) lazy Singleton initialization.
 */
public class IntegerWarehouse implements Warehouse<Integer> {

    private static final Logger logger = LogManager.getLogger(IntegerWarehouse.class);

    /** The single instance. */
    private static IntegerWarehouse instance;

    /** Statistics storage: wrapper id → computed statistics. */
    private final Map<Long, ArrayStatistics> storage = new HashMap<>();

    private final ArrayMathService<Integer>   mathService;
    private final ArrayMinMaxService<Integer> minMaxService;

    /** Private constructor prevents external instantiation. */
    private IntegerWarehouse() {
        this.mathService   = new ArrayMathServiceImpl<>();
        this.minMaxService = new ArrayMinMaxServiceImpl<>();
    }

    /**
     * Returns the sole instance of {@code IntegerWarehouse}.
     * Created lazily on first call; intentionally not thread-safe.
     *
     * @return the singleton {@code IntegerWarehouse}
     */
    public static IntegerWarehouse getInstance() {
        if (instance == null) {
            instance = new IntegerWarehouse();
            logger.debug("IntegerWarehouse instance created");
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void recalculate(AbstractArrayWrapper<Integer> wrapper) {
        if (wrapper.isEmpty()) {
            storage.remove(wrapper.getId());
            logger.debug("Wrapper id={} is empty, statistics removed", wrapper.getId());
            return;
        }

        double sum     = mathService.sum(wrapper).orElseThrow();
        double average = mathService.average(wrapper).orElseThrow();
        double min     = minMaxService.findMin(wrapper).orElseThrow().doubleValue();
        double max     = minMaxService.findMax(wrapper).orElseThrow().doubleValue();

        storage.put(wrapper.getId(), new ArrayStatistics(sum, average, min, max));
        logger.debug("Recalculated statistics for id={}: sum={}, avg={}, min={}, max={}", wrapper.getId(), sum, average, min, max);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ArrayStatistics> getStatistics(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(long id) {
        storage.remove(id);
        logger.debug("Statistics removed for wrapper id={}", id);
    }
}
