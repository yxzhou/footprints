package concurrent.practice;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Q: implement a counter, make it thread safe
 */

public interface SychronizedCounter {

    public void inc();

}


class MyCounter_synchronizedMethod implements SychronizedCounter{

    private static volatile int counter = 0;

    MyCounter_synchronizedMethod(int initValue){
        this.counter = initValue;
    }

    @Override
    public synchronized void inc(){
        counter++;
    }

}

class MyCounter_synchronizedStatement implements SychronizedCounter{

    private static volatile int counter = 0;

    MyCounter_synchronizedStatement(int initValue){
        this.counter = initValue;
    }

    @Override
    public void  inc(){
        synchronized (this){
            counter++;
        }
    }

}

class MyCounter_CompareAndSwap implements SychronizedCounter{

    private static volatile AtomicInteger counter = new AtomicInteger(0);

    MyCounter_CompareAndSwap(int initValue){
        this.counter = new AtomicInteger(initValue);
    }

    @Override
    public void  inc(){
        boolean isSuccess = false;
        while (!isSuccess){
            int previous = counter.get();

            isSuccess = counter.compareAndSet(previous, previous + 1);
        }
    }

}