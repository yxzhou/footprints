package concurrent.executorService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ScheduledExecutorServiceTest {

    @Test public void testTimer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try{
                    System.out.println("running : " + System.currentTimeMillis());

                    throw new RuntimeException("foo");

                } catch (Throwable t) {
                    System.out.println("Failed : ");
                    //throw t;
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1000, 1000);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        timer.cancel();
    }

    @Test public void testScheduledExecutorService(){

        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try{
                    System.out.println("running : " + System.currentTimeMillis());

                    throw new RuntimeException("foo");

                } catch (Throwable t) {
                    System.out.println("Failed : ");
                    //throw t;
                }

            }
        }, 1, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.shutdownNow();

//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//        ScheduledFuture<?> handle =
//                scheduler.scheduleWithFixedDelay(new Runnable() {
//                    public void run() {
//                        throw new RuntimeException("foo");
//                    }
//                }, 1, 10, TimeUnit.SECONDS);


//        try {
//            handle.get();
//        } catch (ExecutionException e) {
//            Exception rootException = e.getCause();
//        }


    }

}
