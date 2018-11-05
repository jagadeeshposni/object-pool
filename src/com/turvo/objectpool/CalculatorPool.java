package com.turvo.objectpool;

import com.turvo.objectpool.exception.NoSuchObjectExistsException;
import com.turvo.objectpool.exception.ObjectPoolSizeOutOfBoundsException;

import java.util.concurrent.LinkedBlockingQueue;

public class CalculatorPool extends ObjectPoolService<Calculator> {

    public CalculatorPool(int minIdle, int maxIdle) {
        super(minIdle, maxIdle);
    }

    @Override
    public Calculator createObject() throws ObjectPoolSizeOutOfBoundsException {
        if(maxIdle() == objectpool.size()){
            throw new ObjectPoolSizeOutOfBoundsException("maxIdle");
        }
        Calculator calculator = new Calculator(created() + 1);
        objectpool.add(calculator);
        size++;
        return calculator;
    }


    @Override
    public synchronized void destroyObject(Calculator calculator) throws ObjectPoolSizeOutOfBoundsException, NoSuchObjectExistsException {
        if(minIdle() == objectpool.size()){
            throw new ObjectPoolSizeOutOfBoundsException("minIdle");
        }
        boolean isObjectRemoved = objectpool.remove(calculator);
        if(!isObjectRemoved){
            throw new NoSuchObjectExistsException();
        }
    }


    @Override
    public synchronized Calculator borrowObject() throws ObjectPoolSizeOutOfBoundsException {
//        size--;
        if(minIdle() == objectpool.size()){
            throw new ObjectPoolSizeOutOfBoundsException("minIdle");
        }
        return objectpool.poll();
    }

    @Override
    public void releaseObject(Calculator object) throws ObjectPoolSizeOutOfBoundsException {
        if(maxIdle() == objectpool.size()){
            throw new ObjectPoolSizeOutOfBoundsException("maxIdle");
        }
        objectpool.add(object);
        size++;
    }


}