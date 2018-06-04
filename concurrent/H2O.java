package fgafa.concurrent;

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
    static int COUNTH = 0;
    static int COUNTO = 0;

    static void check() {
        while (COUNTH >= 2 && COUNTO >= 1) {
            CONDITIONH.signal();
            CONDITIONH.signal();
            CONDITIONO.signal();

            //WATER.signalAll()   // wrong !!

            COUNTH -= 2;
            COUNTO -= 1;
        }
    }

    public static void h() {
        LOCK.lock();
        try {
            ++COUNTH;
            CONDITIONH.awaitUninterruptibly();

            check();
        } finally {
            LOCK.unlock();
        }
    }

    public static void o() {
        LOCK.lock();
        try {
            ++COUNTO;
            CONDITIONO.awaitUninterruptibly();

            check();
        } finally {
            LOCK.unlock();
        }
    }

}
