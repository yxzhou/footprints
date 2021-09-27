package concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1 Given thread class A and B,  class A.run() will call A.a(),  class B.run() will call B.b(),
 *   How to do if A.a() have to be called before B.b() ?
 * 2 Given thread class A, B and C,  class A.run() will call A.a(),  class B.run() will call B.b(), class C.run() will call C.c().
 *   How to do if A.a() have to be called before B.b(),  B.b() have to be called before C.c() ?
 */
public class LockAndConditionExample2 {
    
    static final Lock LOCK = new ReentrantLock();
    static final Condition CONDITION = LOCK.newCondition();

}




class A2 implements Runnable {
    public void a(){
        //do something
    }

    @Override
    public void run() {
        LockAndConditionExample2.LOCK.lock();

        try{
            a();

        }finally {
            LockAndConditionExample2.CONDITION.signal();
            LockAndConditionExample2.LOCK.unlock();
        }
    }
}

class B2 implements Runnable {
    public void b(){
        //do something
    }

    @Override
    public void run() {
        LockAndConditionExample2.LOCK.lock();

        try{
            LockAndConditionExample2.CONDITION.awaitUninterruptibly();

            b();

        }finally {
            LockAndConditionExample2.LOCK.unlock();
        }
    }
}

