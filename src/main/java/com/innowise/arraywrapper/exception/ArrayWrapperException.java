package com.innowise.arraywrapper.exception;

/**
 * Custom exception for array wrapper operations.
 */
public class ArrayWrapperException extends Exception {

    public ArrayWrapperException() {
        super();
    }

    public ArrayWrapperException(String message) {
        super(message);
    }

    public ArrayWrapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArrayWrapperException(Throwable cause) {
        super(cause);
    }
}