package com.turvo.objectpool;


import com.turvo.objectpool.exception.NoSuchObjectExistsException;

public interface ObjectPool<T> {

    /**
     * Life cycle method, creates an object in the pool
     *
     * @return
     * @throws InterruptedException
     */
    T createObject() throws InterruptedException;

    /**
     * Life cycle method, destroys/removes an object from the pool
     *
     * @param t
     * @throws NoSuchObjectExistsException
     */
    void destroyObject(T t) throws NoSuchObjectExistsException;

    /**
     * Used by the clients of the pool to take/borrow the object from the pool.
     *
     * @return
     */
    T borrowObject();

    /**
     * Used by the clients to place the object back into the pool.
     *
     * @param object
     * @throws InterruptedException
     */
    void releaseObject(T object) throws InterruptedException;

}