package fgafa.concurrent.semaphore;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * implements a semaphore with Lock and Condition
 */
public class SemaphoreWithReentrantLock implements MySemaphore{
    int permits;
    //List<Thread> waiting; //List of threads waiting for semaphore

    Lock lock = new ReentrantLock();
    Condition permitAvailable = lock.newCondition();

    SemaphoreWithReentrantLock(int initValue){
        this.permits = initValue;
    }

    @Override
    public void acquire() throws InterruptedException {
        
        lock.lock();
        try{
            while(permits < 1){
                permitAvailable.await();
            }
            
            permits--;
        }finally{
            lock.unlock();
        }

    }
    
    @Override
    public void release() throws InterruptedException {
        lock.lock();
        try{
            permits++;
            
            permitAvailable.signal();
            
        }finally{
            lock.unlock();
        }
    }
}
