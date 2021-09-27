package concurrent.cyclicBarrierExample;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * The java.util.concurrent.CyclicBarrier class is a synchronization mechanism that can synchronize threads progressing through some algorithm.
 * In other words, it is a barrier that all threads must wait at, until all threads reach it, before any of the threads can continue.
 */
public class CyclicBarrierExample {

    public static void main(String[] args){

        CyclicBarrier barrier1 = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("BarrierAction 1 executed. -- ");
            }
        });

        CyclicBarrier barrier2 = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("BarrierAction 2 executed. -- ");
            }
        });

        new Thread(new CyclicBarrierRunnable(barrier1, barrier2)).start();
        new Thread(new CyclicBarrierRunnable(barrier1, barrier2)).start();
    }

}


class CyclicBarrierRunnable implements Runnable {
    private CyclicBarrier barrier1 = null;
    private CyclicBarrier barrier2 = null;

    public CyclicBarrierRunnable(CyclicBarrier barrier1, CyclicBarrier barrier2){
        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
    }

    @Override
    public void run(){
        try{
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " waiting at barrier #1.");
            this.barrier1.await();

            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " waiting at barrier #2.");
            this.barrier2.await();

            System.out.println(Thread.currentThread().getName() + " done!");

        }catch(InterruptedException | BrokenBarrierException e){
            e.printStackTrace();
        }

    }
}

//Thread-1 waiting at barrier #1.
//Thread-0 waiting at barrier #1.
//BarrierAction 1 executed. --
//Thread-0 waiting at barrier #2.
//Thread-1 waiting at barrier #2.
//BarrierAction 2 executed. --
//Thread-1 done!
//Thread-0 done!
