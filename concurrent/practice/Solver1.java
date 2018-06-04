package fgafa.concurrent.practice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 *
 *  Thinking points:
 *    Point #1,  it required "Say f() call is an expensive call, try to make it called once and only once!".It's a Singlton,  double checking lock
 *    Point #2,  HashMap is not thrad safe,  Hashtable locks on all map,  ConcurrentHashMap locks on segment, it's the best choce.
 *    Point #3,  About the shared data's visibility,  volatile .
 *    Point #4,  Synchronized statement,
 *
 *
 */
public class Solver1 {
    Function<Key, Value> f = new Function<fgafa.concurrent.practice.Promblem1.Key, fgafa.concurrent.practice.Promblem1.Value>() {
        @Override
        public Value apply(Key t)
        {
            return new Value();
        }
    };

    public class Key {

    }

    public class Value {

    }

    volatile Map<Key, Value> map = new ConcurrentHashMap<>();
    private final String mutex = new String("mutex for f.apply()");

    /**
     * {@code Key} cannot be applied lock.
     */
    //doubleCheckLocking
    public Value compute(Key k) {
        Value v = null;

        if (!map.containsKey(k)) {
            /*
             * Say f() call is an expensive call, try to
             * make it called once and only once!
             */
            synchronized (mutex) {
                if(v == null) {
                    v = f.apply(k);
                    map.put(k, v);
                }
            }
        }

        return map.get(k);
    }
}
