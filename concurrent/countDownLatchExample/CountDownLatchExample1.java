package concurrent.countDownLatchExample;

import java.util.concurrent.CountDownLatch;

/**
 * The first CountDownLatchExample is a start signal that prevents any worker from proceeding until the driver is ready for them to proceed;
 * The second CountDownLatchExample is a completion signal that allows the driver to wait until all workers have completed.
 */

// Driver
public class CountDownLatchExample1 {
    static final int N = 3;

    static final CountDownLatch STARTSIGNAL = new CountDownLatch(1);
    static final CountDownLatch DONESIGNAL = new CountDownLatch(N);

    void main()
        throws InterruptedException {

        for (int i = 0; i < N; ++i) { // create and start threads
            new Thread(new Worker(STARTSIGNAL, DONESIGNAL)).start();
        }

        // doSomethingElse(); // don't let run yet

        STARTSIGNAL.countDown(); // let all threads START proceed

        // doSomethingElse();

        DONESIGNAL.await(); // wait for all to finish
    }
}

class Worker implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    Worker(CountDownLatch startSignal,
           CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    public void run() {
        try {
            startSignal.await();

            doWork();

            doneSignal.countDown();

        } catch (InterruptedException ex) {
        } // return;
    }

    void doWork() {
        // do someting
    }
}