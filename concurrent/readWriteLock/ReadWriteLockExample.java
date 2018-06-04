package fgafa.concurrent.readWriteLock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by joeyz on 2/27/17.
 */
public class ReadWriteLockExample {

    public static void main(String[] args){
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


        readWriteLock.readLock().lock();
        try{
            // multiple readers can enter this section
            // if not locked for writing, and not writers waiting
            // to lock for writing.

        }finally {
            readWriteLock.readLock().unlock();
        }


        readWriteLock.writeLock().lock();
        try{
            // only one writer can enter this section,
            // and only if no threads are currently reading.
        }finally {
            readWriteLock.writeLock().unlock();
        }


    }
}
