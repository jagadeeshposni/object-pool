package com.turvo.objectpool;

import com.turvo.objectpool.exception.NoSuchObjectExistsException;

/**
 * Implementation of ObjectPoolService for Calculator objects.
 */
public class CalculatorPool extends ObjectPoolService<Calculator> {

    public CalculatorPool(int minIdle, int maxIdle) {
        super(minIdle, maxIdle);
    }

    /**
     * Creates a Calculator object and adds it to the pool.
     *
     * @throws InterruptedException
     */
    @Override
    public Calculator createObject() throws InterruptedException {
        Calculator calculator = new Calculator(created() + 1);
        objectPool.put(calculator);
        size++;
        return calculator;
    }


    /**
     * Removes the specified object from pool.
     *
     * @param calculator
     * @throws NoSuchObjectExistsException when the desired object to remove is not found
     */
    @Override
    public void destroyObject(Calculator calculator) throws NoSuchObjectExistsException {
        boolean isObjectRemoved = objectPool.remove(calculator);
        if (!isObjectRemoved) {
            throw new NoSuchObjectExistsException();
        }
    }

    /**
     * Retrieves the head element from the pool queue. Once retrieved the object is no longer present in the pool.
     * Waits for the head element to be created when there is no object
     *
     * @return
     */
    @Override
    public Calculator borrowObject() {
        return objectPool.poll();
    }

    /**
     * Adds the object back to the object pool
     *
     * @param object
     */
    @Override
    public void releaseObject(Calculator object) throws InterruptedException {
        objectPool.put(object);
        size++;
    }


}