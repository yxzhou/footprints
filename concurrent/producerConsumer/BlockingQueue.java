package fgafa.concurrent.producerConsumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * public interface BlockingQueue {
    // Retrieve and remove the head of the queue, waiting if no elements are present. 
    T take();
 
    // Add the given element to the end of the queue, waiting if necessary for space to become available. 
    void put (T obj);
   }
 *
 */
/**
 * 
 * Solution:
    A blocking queue is essentially a producer-consumer problem. 
    We need to use Java wait(), notify() and notifyAll() methods. 
    
    This post is particularly useful:
    http://howtodoinjava.com/2015/01/08/how-to-work-with-wait-notify-and-notifyall-in-java/ 
    
    wait( ) tells the calling thread to give up the monitor and go to sleep until some other 
    thread enters the same monitor and calls notify( ).
    notify( ) wakes up the first thread that called wait( ) on the same object.
    notifyAll( ) wakes up all the threads that called wait( ) on the same object. The 
    highest priority thread will run first.
 *
 */

public class BlockingQueue<T> {
    private Queue<T> queue;
    private int capacity = 10;
     
    // Constructor 
    public BlockingQueue(int capacity) {
        queue = new LinkedList<>();
        this.capacity = capacity;
    }
     
    // Add the given element to the end of the queue, 
    // Waiting if necessary for space to become available
    public synchronized void put(T obj) 
            throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
         
        queue.add(obj);
        notifyAll();
    }
     
    // Retrive and remove the head of the queue, 
    // waiting if no elements are present
    public synchronized T take() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
         
        T obj = queue.poll();
        notifyAll();
         
        return obj;
    }
}
