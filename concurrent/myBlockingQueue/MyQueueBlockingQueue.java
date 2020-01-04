package fgafa.concurrent.myBlockingQueue;

import java.util.LinkedList;
import java.util.Queue;



public class MyQueueBlockingQueue<E> implements MyBlockingQueue<E> {
    private Queue<E> queue;
    private int capacity = 10;
     
    // Constructor 
    public MyQueueBlockingQueue(int capacity) {
        queue = new LinkedList<>();
        this.capacity = capacity;
    }
     
    // Add the given element to the end of the queue, 
    // Waiting if necessary for space to become available
    @Override
    public synchronized void put(E obj) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
         
        queue.add(obj);
        notifyAll();
    }

    // Retrive and remove the head of the queue,
    // waiting if no elements are present
    @Override
    public synchronized E poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
         
        E obj = queue.poll();
        notifyAll();
         
        return obj;
    }
}
