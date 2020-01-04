package fgafa.concurrent.myBlockingQueue.mySynchronousQueue;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    final static Random random = new Random();
    static void run(MySynchronousQueue mySynchronousQueue, Integer item){
        try {
            if(random.nextInt(2) == 0) {
                mySynchronousQueue.put(item);
            }else {
                mySynchronousQueue.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        MySynchronousQueue[] mySynchronousQueues = {
                new MySynchronousQueueWithSynchronized(),
                new MySynchronousQueueWithLock(),
                new MySynchronousQueueWithSemaphore()
        };


        for(MySynchronousQueue mySynchronousQueue : mySynchronousQueues){

            ExecutorService executor = Executors.newFixedThreadPool(20);

            for(int i = 0; i < 100; i++){

                final Integer item = i;

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Main.run(mySynchronousQueue, item);
                    }
                });
//                new Thread(){
//                    @Override
//                    public void run() {
//                        Main.run(mySynchronousQueue, item);
//                    }
//                }.start();

            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            executor.shutdownNow();
            System.out.println(" ------   ------    --------  ------- ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
