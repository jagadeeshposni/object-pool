package com.turvo.objectpool;

import com.turvo.objectpool.exception.NoSuchObjectExistsException;
import com.turvo.objectpool.exception.ObjectPoolSizeOutOfBoundsException;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class ObjectPoolService<T> implements ObjectPool<T> {
    private final int minIdle;//Minimum number of objects present in the pool at any given point of time
    private final int maxIdle;//Maximum number of objects that a pool can hold at any given point of time
    protected int size;//Capacity of the pool. Only increments as objects are added to pool. Maximum value is maxIdle. Cannot be decremented.
    protected LinkedBlockingQueue<T> objectPool;

    public ObjectPoolService(final int minIdle, final int maxIdle) {
        this.minIdle = minIdle;
        this.maxIdle = maxIdle;
        this.size = 0;
        objectPool = new LinkedBlockingQueue<T>(maxIdle);
    }


    public int size() {
        return size;
    }

    public int minIdle() {
        return minIdle;
    }

    public int maxIdle() {
        return maxIdle;
    }

    public int created() {
        return size;
    }

    public int availableActive() {
        return objectPool.size();
    }

    public abstract T createObject() throws ObjectPoolSizeOutOfBoundsException, InterruptedException;
    public abstract void destroyObject(T t) throws ObjectPoolSizeOutOfBoundsException, NoSuchObjectExistsException;
    public abstract T borrowObject() throws ObjectPoolSizeOutOfBoundsException;
    public abstract void releaseObject(T t) throws ObjectPoolSizeOutOfBoundsException, InterruptedException;


    public void populatePool() throws ObjectPoolSizeOutOfBoundsException, InterruptedException {
        while (availableActive() < minIdle() && created() < maxIdle()) {
            createObject();
        }
    }
}
