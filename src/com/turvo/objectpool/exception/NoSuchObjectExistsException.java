package com.turvo.objectpool.exception;

public class NoSuchObjectExistsException extends Exception {
    public NoSuchObjectExistsException() {
        super("There is no such object in the pool..");
    }
}
