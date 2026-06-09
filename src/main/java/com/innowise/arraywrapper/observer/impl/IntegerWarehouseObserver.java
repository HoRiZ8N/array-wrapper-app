package com.innowise.arraywrapper.observer.impl;

import com.innowise.arraywrapper.entity.AbstractArrayWrapper;
import com.innowise.arraywrapper.observer.ArrayWrapperObserver;
import com.innowise.arraywrapper.repository.impl.IntegerArrayWrapperRepository;
import com.innowise.arraywrapper.warehouse.impl.IntegerWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Concrete {@link ArrayWrapperObserver} for {@link Integer} element wrappers.
 *
 * <p>On each notification looks up the changed wrapper in
 * {@link IntegerArrayWrapperRepository} and delegates recalculation to
 * {@link IntegerWarehouse}.
 */
public class IntegerWarehouseObserver implements ArrayWrapperObserver {

    private static final Logger logger = LogManager.getLogger(IntegerWarehouseObserver.class);

    /**
     * {@inheritDoc}
     *
     * <p>Looks up the changed wrapper in the repository by {@code wrapperId}
     * and triggers a statistics recalculation in {@link IntegerWarehouse}.
     * If the wrapper is no longer in the repository the notification is silently ignored.
     */
    @Override
    public void onElementChanged(long wrapperId) {
        logger.debug("Element changed in wrapper id={}, triggering recalculation", wrapperId);

        IntegerArrayWrapperRepository.getInstance()
                .findById(wrapperId)
                .ifPresentOrElse(
                        wrapper -> IntegerWarehouse.getInstance().recalculate((AbstractArrayWrapper<Integer>) wrapper),
                        () -> logger.warn("Wrapper id={} not found in repository, skipping recalculation", wrapperId));
    }
}
