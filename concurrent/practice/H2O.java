package fgafa.concurrent.practice;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 实现两个函数: H() and O(), 这两个函数会被多线程调用。当一个线程调用H或O时，如果当前已经有至少两个线程call H和一个线程call O。
 * 那么让两个call H和一个call O的线程返回（产生一个水分子），其他的都block
 *
 */
public class H2O {

    static final Lock LOCK = new ReentrantLock();

    static final Condition CONDITIONH = LOCK.newCondition();
    static final Condition CONDITIONO = LOCK.newCondition();
    static volatile int COUNTH = 0;
    static volatile int COUNTO = 0;

    //static volatile AtomicInteger COUNTH = new AtomicInteger(0);
    //static volatile AtomicInteger COUNTO = new AtomicInteger(0);

    static void check(boolean loopable) {
        String currThread = Thread.currentThread().getName();

        while(true){
            //while (COUNTH.intValue() >= 2 && COUNTO.intValue() >= 1) {
            if (COUNTH >= 2 && COUNTO >= 1) {
                LOCK.lock();
                try {
                    System.out.println(currThread + " Output a H2O");
                    CONDITIONH.signal();
                    CONDITIONH.signal();

                    CONDITIONO.signal();

                    COUNTH -= 2;
                    COUNTO--;
                } finally {
                    LOCK.unlock();
                }

                //WATER.signalAll()   // wrong !!

                //COUNTH.decrementAndGet();
                //COUNTH.decrementAndGet();
                //COUNTO.decrementAndGet();
            }else{
                sleep(10);

                if(!loopable){
                    break;
                }
            }
        }
    }

    private static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void h() {
        LOCK.lock();
        try {
            String currThread = Thread.currentThread().getName();

            System.out.println(currThread + " Input a H");
            //COUNTH.incrementAndGet();
            COUNTH++;

            //check(false);

            CONDITIONH.awaitUninterruptibly();

            //check(false);

            System.out.println(currThread + " is released");

        } finally {
            LOCK.unlock();
        }
    }

    public static void o() {
        LOCK.lock();
        try {
            String currThread = Thread.currentThread().getName();

            System.out.println(currThread+  " Input a O");
            //COUNTO.incrementAndGet();
            COUNTO++;

            //check(false);

            CONDITIONO.awaitUninterruptibly();

            //check(false);

            System.out.println(currThread + " is released");

        } finally {
            LOCK.unlock();
        }
    }


    public static void main(String[] args){

        ExecutorService pool = Executors.newFixedThreadPool(20);
        //
        pool.submit(() -> {
            H2O.check(true);
        });

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            final int j = i;

            pool.submit(() -> {
                if (random.nextInt(3) == 2) {
                //if ( j % 3 == 2) {
                    H2O.o();
                } else {
                    H2O.h();
                }
            });
        }

    }
}

/**
 *    Note:
 *    1 "call check() in h() and o()" VS "call check() in a special thread"
 *       There is an issue in "call check() in h() and o()":
 *       if the check() is before CONDITIONO.awaitUninterruptibly(), it will miss some H2O output.
 *       Example to use-case, input,  H, H, H, O.  It would no output.
 *       if the check() is after CONDITIONO.awaitUninterruptibly(), all threads will be in wait, no H2O output.
 *       Example to use-case, input,  H, H, H, O.  every thread will be in wait, no chance to call check().
 *
 *    2 It would throw IllegalMonitorStateException if the current thread is not the owner of the lock.
 *
 *    3 because there is LOCK.lock(), COUNTH and COUNTO is same to set as integer and AtomicInteger
 *
 *    4 when use thread pool, if the pool size is not big enough, all threads maybe be blocked
 */