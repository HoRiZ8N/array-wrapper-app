package com.innowise.arraywrapper.observer;

/**
 * Observer notified when an element of an array wrapper is changed.
 *
 * <p>Implementations react to the change by performing whatever update is
 * needed — for example, recalculating statistics in {@code Warehouse}.
 */
public interface ArrayWrapperObserver {

  /**
   * Called after an element of the wrapper identified by {@code wrapperId}
   * has been modified.
   *
   * @param wrapperId the id of the wrapper whose element changed
   */
  void onElementChanged(long wrapperId);
}