package fgafa.concurrent;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 实现两个函数: H() and O(), 这两个函数会被多线程调用。当一个线程调用H或O时，如果当前已经有至少两个线程call H和一个线程call O。那么让两个call H和一个call O的线程返回（产生一个水分子），其他的都block
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
        while(loopable){
            //while (COUNTH.intValue() >= 2 && COUNTO.intValue() >= 1) {
            while (COUNTH >= 2 && COUNTO >= 1) {
                LOCK.lock();
                try {
                    System.out.println("Output a H2O");
                    CONDITIONH.signal();
                    //sleep(2);
                    CONDITIONH.signal();
                    //sleep(2);
                    CONDITIONO.signal();
                    //sleep(2);

                    COUNTH -= 2;
                    COUNTO--;
                } finally {
                    LOCK.unlock();
                }

                //WATER.signalAll()   // wrong !!

                //COUNTH.decrementAndGet();
                //COUNTH.decrementAndGet();
                //COUNTO.decrementAndGet();
            }

            //sleep(1);
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
            System.out.println(currThread + " is released");

        } finally {
            LOCK.unlock();
        }
    }

    public static void o() {
        LOCK.lock();
        try {
            String currThread = Thread.currentThread().getName();

            //COUNTO.incrementAndGet();
            COUNTO++;
            System.out.println(currThread+  "Input a O");

            //check(false);

            CONDITIONO.awaitUninterruptibly();
            System.out.println(currThread + " is released");

        } finally {
            LOCK.unlock();
        }
    }


    public static void main(String[] args){
        Random random = new Random();

        //
        new Thread(() -> {
            H2O.check(true);
        }).start();

        for (int i = 0; i < 30; i++) {
            final int j = i;

            new Thread(() -> {
                if (random.nextInt(3) == 2) {
                //if ( j % 3 == 2) {
                    H2O.o();
                } else {
                    H2O.h();
                }
            }).start();
        }

    }
}
