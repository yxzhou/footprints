package dailyCoding;

import java.io.Closeable;
import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.*;

import java.util.Timer;


/**
 *
 * Implement a job scheduler which takes in a function f and an integer n, and calls f after n milliseconds.
 *
 * Tags: Apple,
 */

public class JobScheduler{

}



/**  schedule with Executors.newScheduledThreadPool */
class JobScheduler_ScheduledExecutorService<V> implements Closeable {
    private final ScheduledExecutorService scheduler1 = Executors.newScheduledThreadPool(1);

    public V execute(Callable<V> f, int n) throws ExecutionException, InterruptedException {
        ScheduledFuture scheduledFuture = scheduler1.schedule(f, n, TimeUnit.MICROSECONDS);

        return (V) scheduledFuture.get();
    }

    @Override public void close() throws IOException {
        scheduler1.shutdown();
    }

}

/**  schedule with Timer and TimerTask */
class JobScheduler_Timer implements Closeable{
    private final Timer timer = new Timer("myTest2",true);

    public void execute(Runnable f, int n){
        TimerTask timerTask = new TimerTask() {
            @Override public void run() {
                f.run();
            }
        };

        timer.schedule(timerTask, n);
    }

    @Override public void close() throws IOException {
        timer.cancel();
    }
}

/**  schedule with a thread */
class JobScheduler_ExecutorService implements Closeable{
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public void execute(Runnable f, int n){
        Runnable runnable = new Runnable() {
            public void run() {

                try {
                    Thread.sleep(n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                f.run();
            }
        };

        executor.execute(runnable);
    }

    @Override public void close() throws IOException {
        executor.shutdownNow();
    }
}

/**  schedule with Quartz _http://www.quartz-scheduler.org/ */
class JobScheduler_Quartz {

}