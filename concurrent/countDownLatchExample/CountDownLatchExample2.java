package fgafa.concurrent.countDownLatchExample;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The first CountDownLatchExample is a start signal that prevents any worker from proceeding until the driver is ready for them to proceed;
 * The second CountDownLatchExample is a completion signal that allows the driver to wait until all workers have completed.
 */

// Driver
public class CountDownLatchExample2 {
    static final int N = 3;

    static final CountDownLatch DONESIGNAL = new CountDownLatch(N);

    void main()
        throws InterruptedException {

        Executor e = Executors.newFixedThreadPool(3);

        for (int i = 0; i < N; ++i) { // create and start threads
            e.execute(new WorkerRunnable(DONESIGNAL, i));
        }

        DONESIGNAL.await(); // wait for all to finish
    }

}

class WorkerRunnable implements Runnable {
    private final CountDownLatch doneSignal;
    private final int i;

    WorkerRunnable(CountDownLatch doneSignal,
                   int i) {
        this.doneSignal = doneSignal;
        this.i = i;
    }

    public void run() {
        try {
            doWork(i);
            doneSignal.countDown();
        } catch (Exception ex) { // InterruptedException

        } // return;

    }

    void doWork(int i) {
        // do something
    }
}
