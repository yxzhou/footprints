package fgafa.concurrent.myBlockingQueue;

import com.sun.istack.internal.NotNull;


/**
 *  A blocking queue is essentially a producer-consumer problem.
 * public interface BlockingQueue {
     // Retrieve and remove the head of the queue, waiting if no elements are present.
     T poll();

     // Add the given element to the end of the queue, waiting if necessary for space to become available.
     void put (T obj);
 }
 *
 */
/**
 *
 * Solution:
 *

 1)use Java wait(), notify() and notifyAll() methods.

 This post is particularly useful:
 http://howtodoinjava.com/2015/01/08/how-to-work-with-wait-notify-and-notifyaMyll-in-java/

 wait( ) tells the calling thread to give up the monitor and go to sleep until some other
 thread enters the same monitor and calls notify( ).
 notify( ) wakes up the first thread that called wait( ) on the same object.
 notifyAll( ) wakes up all the threads that called wait( ) on the same object. The
 highest priority thread will run first.

 2)use

 *
 */

public interface MyBlockingQueue<E> {

    void put(@NotNull E e) throws InterruptedException;
    @NotNull E poll() throws InterruptedException;

}
