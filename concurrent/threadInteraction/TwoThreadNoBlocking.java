package fgafa.concurrent.threadInteraction;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Function: 两个线程交替执行打印 1~100
 * <p>
 * non blocking 版：
 * 两个线程轮询volatile变量(flag)
 * 线程一"看到"flag值为1时执行代码并将flag设置为0,
 * 线程二"看到"flag值为0时执行代码并将flag设置未1,
 * 2个线程不断轮询直到满足条件退出
 *
 */

public class TwoThreadNoBlocking implements Runnable{

        int value;
        int end;

        String name;

        public static volatile int flag = 0;

        private final static Lock LOCK = new ReentrantLock();

        TwoThreadNoBlocking(String name, int value, int end){
            this.name = name;
            this.value = value;
            this.end = end;
        }

        @Override public void run(){
            while(value <= end){
                if((value & 1) == flag){
                    System.out.println(value + " \t " + Thread.currentThread().getName());
                    value += 2;

                    flag ^= 1;
                }else{
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                }
            }
        }


        public static void main(String[] args){
            new Thread(new TwoThreadNoBlocking("t11", 0, 100)).start();
            //new Thread(new TwoThreadNoBlocking("t12", 1, 100)).start();

            new Thread(new TwoThreadNoBlocking("t21", 1, 100)).start();
            //new Thread(new TwoThreadNoBlocking("t21", 1, 100)).start();

        }



}
