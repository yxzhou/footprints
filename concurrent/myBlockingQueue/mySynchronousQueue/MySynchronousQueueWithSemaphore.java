package fgafa.concurrent.myBlockingQueue.mySynchronousQueue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MySynchronousQueueWithSemaphore<E> implements MySynchronousQueue<E>, java.io.Serializable {

    private volatile E item = null;

    private static final Semaphore sync = new Semaphore(0);
    private static final Semaphore putting = new Semaphore(1);
    private static final Semaphore getting = new Semaphore(0);

    @Override
    public E take() throws InterruptedException {

        if(getting.tryAcquire(1, TimeUnit.SECONDS)) {
            E result = item;

            sync.release();
            putting.release();

            System.out.println("take: " + result);
            return result;
        }

        return null;
    }

    @Override
    public synchronized void put(E item) throws InterruptedException {
        if(item == null) {
            return;
        }

        if(putting.tryAcquire(1, TimeUnit.SECONDS)) {

            this.item = item;

            System.out.println("put: " + this.item);

            getting.release();
            sync.acquire();
        }
    }

}
