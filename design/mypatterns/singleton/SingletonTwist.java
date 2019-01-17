package fgafa.design.mypatterns.singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * Implement the singleton pattern with a twist. First, instead of storing one instance, store two instances.
 * And in every even call of getInstance(), return the first instance and in every odd call of getInstance(), return the second instance.
 *
 * Tags: ms
 */

public class SingletonTwist {
    private static volatile SingletonTwist[] instances = null;
    private static volatile AtomicInteger count = new AtomicInteger(0);

    private SingletonTwist(){
    }

    public static SingletonTwist getInstance(){
        if(instances == null){
            synchronized (SingletonTwist.class){
                if(instances == null) {
                    instances = new SingletonTwist[2];
                    instances[0] = new SingletonTwist();
                    instances[1] = new SingletonTwist();
                }
            }
        }

        if(count.compareAndSet(0, 1)){
            return instances[0];
        }else if(count.compareAndSet(1, 0)){
            return instances[1];
        }

        return null; // this will not triggered
    }

}
