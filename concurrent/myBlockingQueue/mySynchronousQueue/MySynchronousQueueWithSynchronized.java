package fgafa.concurrent.myBlockingQueue.mySynchronousQueue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MySynchronousQueueWithLock<E> implements java.io.Serializable {

    private boolean putting = false;

    private E item = null;

    public synchronized E take() throws InterruptedException {
        while (item == null) {
            wait();
        }

        E result = item;

        item = null;
        notifyAll();

        return result;
    }

    public synchronized void put(E item) throws InterruptedException {
        if(item == null) {
            return;
        }

        while (putting){
            wait();
        }

        putting = true;

        this.item = item;
        notifyAll();

        while (item != null) {
            wait();
        }

        putting = false;
        notifyAll();
    }

}
