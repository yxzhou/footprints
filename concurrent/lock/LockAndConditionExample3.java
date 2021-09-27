package concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Given thread class A, B and C,  class A.run() will call A.a(),  class B.run() will call B.b(), class C.run() will call C.c().
 *   How to do if A.a() have to be called before B.b(),  B.b() have to be called before C.c() ?
 */
public class LockAndConditionExample3 {
    static final Lock LOCK = new ReentrantLock();
    static final Condition CONDITIONA = LOCK.newCondition();
    static final Condition CONDITIONB = LOCK.newCondition();

}

class A implements Runnable {
    public void a(){
        //do something
    }

    @Override
    public void run() {
        LockAndConditionExample3.LOCK.lock();

        try{
            a();

        }finally {
            LockAndConditionExample3.CONDITIONA.signal();
            LockAndConditionExample3.LOCK.unlock();
        }
    }
}

class B implements Runnable {
    public void b(){
        //do something
    }

    @Override
    public void run() {
        LockAndConditionExample3.LOCK.lock();

        try{
            LockAndConditionExample3.CONDITIONA.awaitUninterruptibly();

            b();

        }finally {
            LockAndConditionExample3.CONDITIONB.signal();
            LockAndConditionExample3.LOCK.unlock();
        }
    }
}

class C implements Runnable {
    public void c(){
        //do something
    }

    @Override
    public void run() {
        LockAndConditionExample3.LOCK.lock();

        try{
            LockAndConditionExample3.CONDITIONB.awaitUninterruptibly();

            c();

        }finally {
            LockAndConditionExample3.LOCK.unlock();
        }
    }
}


