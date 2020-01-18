package fgafa.concurrent.myBlockingQueue.mySynchronousQueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MySynchronousQueueWithLock<E> implements MySynchronousQueue<E>, java.io.Serializable {

    private volatile E item = null;

    private static final Lock lock = new ReentrantLock();
    private static final Condition putting = lock.newCondition();
    private static final Condition getting = lock.newCondition();

    @Override
    public E take() throws InterruptedException {

        lock.lock();

        try {
            while (item == null) {
            //if (item == null) {
                getting.await();
            }

            E result = item;

            item = null;
            putting.signal();

            System.out.println("take: " + result);
            return result;
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void put(E item) throws InterruptedException {
        if(item == null) {
            return;
        }

        lock.lock();

        try {
            while (this.item != null) {
            //if (this.item != null) {
                putting.await();
            }

            this.item = item;
            System.out.println("put: " + this.item);
            getting.signal();
        } finally {
            lock.unlock();
        }
    }

}
