package com.turvo.objectpool;


import com.turvo.objectpool.exception.NoSuchObjectExistsException;
import com.turvo.objectpool.exception.ObjectPoolSizeOutOfBoundsException;

public interface ObjectPool<T> {

    /**
     * Life cycle method, creates an object in the pool
     *
     * @return
     * @throws ObjectPoolSizeOutOfBoundsException
     * @throws InterruptedException
     */
    T createObject() throws ObjectPoolSizeOutOfBoundsException, InterruptedException;

    /**
     * Life cycle method, destroys/removes an object from the pool
     *
     * @param t
     * @throws ObjectPoolSizeOutOfBoundsException
     * @throws NoSuchObjectExistsException
     */
    void destroyObject(T t) throws ObjectPoolSizeOutOfBoundsException, NoSuchObjectExistsException;

    /**
     * Used by the clients of the pool to take/borrow the object from the pool.
     *
     * @return
     * @throws ObjectPoolSizeOutOfBoundsException
     */
    T borrowObject() throws ObjectPoolSizeOutOfBoundsException;

    /**
     * Used by the clients to place the object back into the pool.
     *
     * @param object
     * @throws ObjectPoolSizeOutOfBoundsException
     * @throws InterruptedException
     */
    void releaseObject(T object) throws ObjectPoolSizeOutOfBoundsException, InterruptedException;

}