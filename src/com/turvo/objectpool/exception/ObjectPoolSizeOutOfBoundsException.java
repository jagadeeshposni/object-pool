package com.turvo.objectpool.exception;

public class ObjectPoolSizeOutOfBoundsException extends Exception {

    public ObjectPoolSizeOutOfBoundsException(String  attribute) {
        super(attribute + " is out of bounds..");
    }
}
