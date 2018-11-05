package com.turvo.objectpool;

import com.turvo.objectpool.exception.NoSuchObjectExistsException;
import com.turvo.objectpool.exception.ObjectPoolSizeOutOfBoundsException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorPoolTest {

    private CalculatorPool calcPool;
    private Log logger = LogFactory.getLog(CalculatorPoolTest.class);


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        calcPool = null;
    }

    @Test
    void testCreateObject() throws ObjectPoolSizeOutOfBoundsException {
        calcPool = new CalculatorPool(5, 10);
        calcPool.createObject();
        Assertions.assertEquals(1,calcPool.availableActive());
    }

    @Test
    void testDestroyObject() throws ObjectPoolSizeOutOfBoundsException, NoSuchObjectExistsException {
        calcPool = new CalculatorPool(5, 10);
        Calculator createdObj = calcPool.createObject();
        calcPool.destroyObject(createdObj);
        Assertions.assertEquals(0, calcPool.availableActive());

    }

    @Test
    void testDestroyNonExistingObject() throws ObjectPoolSizeOutOfBoundsException, NoSuchObjectExistsException {
        calcPool = new CalculatorPool(5, 10);
        Calculator createdObj = calcPool.createObject();
        Assertions.assertThrows(NoSuchObjectExistsException.class , () -> {

        calcPool.destroyObject(new Calculator(999));
        });

    }

    @Test
    void testSamplePopulatePoll() throws ObjectPoolSizeOutOfBoundsException {
        calcPool = new CalculatorPool(5, 10);
        Assertions.assertEquals(0, calcPool.availableActive());
        calcPool.populatePool();
        Assertions.assertEquals(5, calcPool.availableActive());
    }

    @Test
    void testBorrowOneObject() throws ObjectPoolSizeOutOfBoundsException {
        calcPool = new CalculatorPool(5, 10);
        calcPool.populatePool();
        Assertions.assertEquals(5, calcPool.availableActive());
        Assertions.assertEquals(5, calcPool.size());
        calcPool.createObject();
        calcPool.borrowObject();
        Assertions.assertEquals(5, calcPool.availableActive());
        Assertions.assertEquals(6, calcPool.size());
    }

    @Test
    void testReleaseOneObject() throws ObjectPoolSizeOutOfBoundsException {
        calcPool = new CalculatorPool(5, 10);
        Assertions.assertEquals(0, calcPool.availableActive());
        Assertions.assertEquals(0, calcPool.size());
        calcPool.releaseObject(new Calculator(999));
        Assertions.assertEquals(1, calcPool.availableActive());
        Assertions.assertEquals(1, calcPool.size());
    }

    @Test
    void testBorrowReleaseObjectsFromPool() throws ObjectPoolSizeOutOfBoundsException {
        calcPool = new CalculatorPool(5, 10);
        calcPool.populatePool();

        Assertions.assertEquals(5, calcPool.availableActive());
        Assertions.assertEquals(5, calcPool.size());

        calcPool.createObject();
        Calculator borrowedObj = calcPool.borrowObject();
        calcPool.releaseObject(borrowedObj);

        Assertions.assertEquals(6, calcPool.availableActive());
        Assertions.assertEquals(7, calcPool.size());

        borrowedObj = calcPool.borrowObject();
        calcPool.releaseObject(borrowedObj);

        Assertions.assertEquals(6, calcPool.availableActive());
        Assertions.assertEquals(8, calcPool.size());

    }

    @Test
    void raisesMinIdleExceptionOnBorrowObject() throws ObjectPoolSizeOutOfBoundsException {
        calcPool = new CalculatorPool(5, 10);
        calcPool.populatePool();
        Assertions.assertThrows(ObjectPoolSizeOutOfBoundsException.class, () -> {
            calcPool.borrowObject();
        });
    }

    @Test
    void raisesMinIdleExceptionOnDestroyObject() throws ObjectPoolSizeOutOfBoundsException, NoSuchObjectExistsException {
        calcPool = new CalculatorPool(5, 10);
        calcPool.populatePool();
        Calculator createdObj = calcPool.createObject();
        calcPool.destroyObject(createdObj);
        Assertions.assertThrows(ObjectPoolSizeOutOfBoundsException.class, () -> {
            calcPool.destroyObject(createdObj);
        });
    }

    @Test
    void raisesMaxIdleException() throws ObjectPoolSizeOutOfBoundsException {
        calcPool = new CalculatorPool(5, 10);
        calcPool.populatePool();
        calcPool.createObject();
        calcPool.createObject();
        calcPool.createObject();
        calcPool.createObject();
        calcPool.createObject();
        Assertions.assertThrows(ObjectPoolSizeOutOfBoundsException.class, () -> {
            calcPool.createObject();
        });
    }

    @Test
    void raisesMaxIdleExceptionOnReleaseObject() throws ObjectPoolSizeOutOfBoundsException{
        calcPool = new CalculatorPool(0,5);
        calcPool.createObject();
        calcPool.createObject();
        calcPool.createObject();
        calcPool.createObject();
        calcPool.createObject();
        Assertions.assertThrows(ObjectPoolSizeOutOfBoundsException.class, () -> {
            calcPool.releaseObject(new Calculator(999));
        });
    }

}