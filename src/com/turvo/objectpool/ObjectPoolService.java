package com.turvo.objectpool;

import com.turvo.objectpool.exception.NoSuchObjectExistsException;
import com.turvo.objectpool.exception.ObjectPoolSizeOutOfBoundsException;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class ObjectPoolService<T> implements ObjectPool<T> {
    private final int minIdle;
    private final int maxIdle;
    protected int size;
    private boolean shutdown = false;
    protected LinkedBlockingQueue<T> objectpool;

    public ObjectPoolService(final int minIdle, final int maxIdle) {
        this.minIdle = minIdle;
        this.maxIdle = maxIdle;
        this.size = 0;
        initialize(minIdle, maxIdle);
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

    //public long expiryInterval() {}
    public int created() {
        return size;
    }

    public int borrowed() {
        return size - objectpool.size();
    }

    public int availableActive() {
        return objectpool.size();
    }

//    public T borrowObject() {
//        if (!shutdown) {
////            populatePool();
//            return objectpool.poll();
//        } else return null;
//    }
//
//    public void releaseObject(T object) {
//        objectpool.add(object);
//        size++;
//        if (shutdown && availableActive() == created()) {
//            objectpool.clear();
//        }
//    }

    public void shutdown() {
        shutdown = true;
    }

    public abstract T createObject() throws ObjectPoolSizeOutOfBoundsException;
    public abstract void destroyObject(T t) throws ObjectPoolSizeOutOfBoundsException, NoSuchObjectExistsException;
    public abstract T borrowObject() throws ObjectPoolSizeOutOfBoundsException;
    public abstract void releaseObject(T t) throws ObjectPoolSizeOutOfBoundsException;

    public void initialize(final int minIdle, final int maxIdle) {
        objectpool = new LinkedBlockingQueue<T>(maxIdle);
//        for (int i = 0; i < minIdle; i++) {
//            objectpool.add(createObject());
//            size++;
//        }
//        size = minIdle;
    }

    public void populatePool() throws ObjectPoolSizeOutOfBoundsException {
        while (availableActive() < minIdle() && created() < maxIdle()) {
            createObject();
        }
    }

    //    public T tryBorrowObject(long timeout) throws InterruptedException {
//        if (!shutdown) {
////            populatePool();
//            return objectpool.poll(timeout, TimeUnit.MILLISECONDS);
//        } else return null;
//    }
//



//    public int availablePassive() {
//        return maxIdle() - created();
//    }
//
//    public int availableTotal() {
//        return maxIdle - borrowed();
//    }
}
