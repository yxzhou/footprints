package dailyCoding.design;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * Given a function f, and N return a debounced f of N milliseconds.

 That is, as long as the debounced f continues to be invoked, f itself will not be called for N milliseconds.
 *
 *  tags: facebook
 */

public class RebouncedFunction {

    AtomicLong expiredTime = new AtomicLong(0);


    public void f(int waitTime){
        long currTime = System.currentTimeMillis();
        long nextExpiredTime = currTime + waitTime;

        if(expiredTime.longValue() < currTime){
            synchronized (this){
                if(expiredTime.longValue() < currTime) {
                    expiredTime.getAndSet(nextExpiredTime);
                }
            }
        }

        if(expiredTime.compareAndSet(nextExpiredTime, nextExpiredTime)){
            //do something
        }

    }

}
