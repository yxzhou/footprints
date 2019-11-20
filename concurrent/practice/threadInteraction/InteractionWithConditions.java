package fgafa.concurrent.practice.threadInteraction;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InteractionWithConditions implements Runnable {

    //shared objects
    static final Lock LOCK = new ReentrantLock();
    static final Condition CONDITION0 = LOCK.newCondition();
    static final Condition CONDITION1 = LOCK.newCondition();

    static int count = 0;
    static final int end = 100;

    //thread private variable
    String name;
    boolean flag;

    InteractionWithConditions(String name, boolean flag){
        this.name = name;
        this.flag = flag;
    }

    @Override
    public void run(){
        while(count <= end){
            LOCK.lock();

            try{
                if(flag){
                    CONDITION0.awaitUninterruptibly();

                    if(count <= end) {
                        System.out.println(name + " \t " + flag + "\t" + count);
                        count++;
                    }

                    CONDITION1.signal();
                }else{
                    CONDITION1.awaitUninterruptibly();
                    if(count <= end) {
                        System.out.println(name + " \t " + flag + "\t" + count);
                        count++;
                    }

                    CONDITION0.signal();
                }

            }finally {
                LOCK.unlock();
            }


        }
    }


    public static void main(String[] args){


        new Thread(new InteractionWithConditions("t11", true)).start();
        new Thread(new InteractionWithConditions("t12", true)).start();

        new Thread(new InteractionWithConditions("t21", false)).start();
        new Thread(new InteractionWithConditions("t22", false)).start();

        LOCK.lock();

        try {
            CONDITION0.signal();
        }finally {
            LOCK.unlock();
        }

    }

}
