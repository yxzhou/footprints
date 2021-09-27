package concurrent.myBlockingQueue;


/**
 *
 *
 *  one lock, two condition
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyArrayBlockingQueue<T> implements MyBlockingQueue<T> {

    private List<T> list;
    private int capacity;
    private int i = 0;

    static final Lock LOCK = new ReentrantLock();
    static final Condition CONDITIONPUT = LOCK.newCondition();
    static final Condition CONDITIONPOLL = LOCK.newCondition();

    MyArrayBlockingQueue(int capacity){
        this.capacity = capacity;
        list = new ArrayList<>(capacity);
    }


    @Override
    public void put(T t) throws InterruptedException {

        LOCK.lock();
        try{

            while(list.size() >= capacity ){
                CONDITIONPUT.awaitUninterruptibly();
            }

            list.add(t);
            i++;

            //CONDITIONPOLL.signal();
            CONDITIONPOLL.signalAll();

        }finally {
            LOCK.unlock();
        }
    }

    @Override
    public T poll() throws InterruptedException {
        LOCK.lock();
        try{
            while(list.size() == capacity ){
                CONDITIONPOLL.awaitUninterruptibly();
            }

            i--;
            T result = list.get(i);

            //CONDITIONPUT.signal();
            CONDITIONPUT.signalAll();

            return result;
        }finally {
            LOCK.unlock();
        }
    }
}
