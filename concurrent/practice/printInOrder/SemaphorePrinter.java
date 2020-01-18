package fgafa.concurrent.practice.printInOrder;


import java.util.concurrent.Semaphore;

/**
 * "Semaphore is a bowl of marbles" - Professor Stark
 *
 * 1 Semaphore is a bowl of marbles (or locks in this case).
 *   If you need a marble, and there are none, you wait. You wait until there is one marble and then you take it.
 *   If you release(), you will add one marble to the bowl (from thin air). If you release(100), you will add 100 marbles to the bowl (from thin air).
 * 2 The thread calling third() will wait until the end of second() when it releases a '3' marble. The second() will wait until the end of first() when it releases a '2' marble.
 *   Since first() never acquires anything, it will never wait. There is a forced wait ordering.
 * 3 With semaphores, you can start out with 1 marble or 0 marbles or 100 marbles. A thread can take marbles (up until it's empty) or put many marbles (out of thin air) at a time.
 *
 */

public class SemaphorePrinter implements Printer {

    private Semaphore run1;
    private Semaphore run2;
    private Semaphore run3;

    public SemaphorePrinter() {
        run1 = new Semaphore(1);
        run2 = new Semaphore(0);
        run3 = new Semaphore(0);
    }

    public void first(Runnable print) throws InterruptedException {
        run1.acquire();
        // printFirst.run() outputs "first". Do not change or remove this line.
        print.run();
        run2.release();

    }

    public void second(Runnable print) throws InterruptedException {
        run2.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        print.run();
        run3.release();

    }

    public void third(Runnable print) throws InterruptedException {
        run3.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        print.run();
        run1.release();
    }

}
