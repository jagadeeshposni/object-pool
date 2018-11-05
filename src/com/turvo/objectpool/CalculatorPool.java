package com.turvo.objectpool;

import com.turvo.objectpool.exception.NoSuchObjectExistsException;
import com.turvo.objectpool.exception.ObjectPoolSizeOutOfBoundsException;

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
     * @throws ObjectPoolSizeOutOfBoundsException when pool is already full
     * @throws InterruptedException
     */
    @Override
    public Calculator createObject() throws ObjectPoolSizeOutOfBoundsException, InterruptedException {
        if (maxIdle() == objectPool.size()) { //pool is already full
            throw new ObjectPoolSizeOutOfBoundsException("maxIdle");
        }
        Calculator calculator = new Calculator(created() + 1);
        objectPool.put(calculator);
        size++;
        return calculator;
    }


    /**
     * Removes the specified object from pool.
     *
     * @param calculator
     * @throws ObjectPoolSizeOutOfBoundsException when the pool is empty
     * @throws NoSuchObjectExistsException        when the desired object to remove is not found
     */
    @Override
    public void destroyObject(Calculator calculator) throws ObjectPoolSizeOutOfBoundsException, NoSuchObjectExistsException {
        if (minIdle() == objectPool.size()) { //pool is empty. Nothing to destroy
            throw new ObjectPoolSizeOutOfBoundsException("minIdle");
        }
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
     * @throws ObjectPoolSizeOutOfBoundsException
     */
    @Override
    public Calculator borrowObject() throws ObjectPoolSizeOutOfBoundsException {
        if (minIdle() == objectPool.size()) { //pool is empty. Nothing to borrow
            throw new ObjectPoolSizeOutOfBoundsException("minIdle");
        }
        return objectPool.poll();
    }

    @Override
    public void releaseObject(Calculator object) throws ObjectPoolSizeOutOfBoundsException, InterruptedException {
        if (maxIdle() == objectPool.size()) { //pool is full, Cannot put any borrowed object back.
            throw new ObjectPoolSizeOutOfBoundsException("maxIdle");
        }
        objectPool.put(object);
        size++;
    }


}