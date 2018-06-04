package fgafa.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by joeyz on 2/27/17.
 */
public class ContextSwitch {

    public static void main(String[] args){
        MyThread thread1 = new MyThread("T1");
        MyThread thread2 = new MyThread("T2");
        //MyThread thread3 = new MyThread("T3");

        thread1.start();
        thread2.start();
        //thread3.start();

    }

}

class MyThread extends Thread{
    public static Lock LOCK = new ReentrantLock();

    public static final Condition CONDITION = LOCK.newCondition();

    private String name;

    MyThread(String name){
        this.name = name;
    }

    @Override
    public void run(){
        int[] array = new int[1024];
        for(int i = 0; i < array.length; i++){
            array[i] = i;
        }

        for(int i = 0; i < 10; i++){
            rest(100, i);
        }

    }

    public void rest(int ms, int flag){
        LOCK.lock();
        try {
            long start = System.nanoTime();
            System.out.println(String.format("Thread:%s, \ti:%d, \tStart:%d", this.name, flag, start));

            TimeUnit.MILLISECONDS.sleep(ms);

//            if(Thread.activeCount() == 0){
//
//            }
            CONDITION.signal();

            long end = System.nanoTime();
            System.out.println(String.format("Thread:%s, \ti:%d, \tEnd:%d, \tCost:%d", this.name, flag, end, end - start));


            CONDITION.await();


        }catch(InterruptedException e){
            //e.printStackTrace();
        }finally {
            LOCK.unlock();
        }
    }
}