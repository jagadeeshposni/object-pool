package com.turvo.objectpool;


import com.turvo.objectpool.exception.NoSuchObjectExistsException;
import com.turvo.objectpool.exception.ObjectPoolSizeOutOfBoundsException;

public interface ObjectPool<T> {

    T createObject() throws ObjectPoolSizeOutOfBoundsException;

    void destroyObject(T t) throws ObjectPoolSizeOutOfBoundsException, NoSuchObjectExistsException;

    T borrowObject() throws ObjectPoolSizeOutOfBoundsException;

    void releaseObject(T object) throws ObjectPoolSizeOutOfBoundsException;

}