package fgafa.concurrent.myBlockingQueue.mySynchronousQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MySynchronousQueueWithLock<E> implements java.io.Serializable {

    private volatile E item = null;

    private static final Lock lock = new ReentrantLock();
    private static final Condition putting = lock.newCondition();
    private static final Condition getting = lock.newCondition();

    public E take() throws InterruptedException {

        lock.lock();

        try {
            while (item == null) {
                getting.awaitUninterruptibly();
            }

            E result = item;

            item = null;
            putting.signal();

            return result;
        } finally {
            lock.unlock();
        }

    }

    public synchronized void put(E item) throws InterruptedException {
        if(item == null) {
            return;
        }

        lock.lock();

        try {

            while (this.item != null) {
                putting.awaitUninterruptibly();
            }

            this.item = item;
            getting.signal();
        } finally {
            lock.unlock();
        }
    }

}
