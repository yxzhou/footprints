package todo;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class ContextSwitch {

    final static int N = 100;
    final static int sleepTime = 500; //ms
    final static int threadNum = 2;


    private static void rest(int state, String threadName, int ms) throws InterruptedException{
        System.out.println(String.format("start to sleep %d %s \t %d ", state, threadName, System.nanoTime()));
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        }catch(InterruptedException e){
            throw e;
        }
        System.out.println(String.format("end to sleep %d %s \t %d ", state, threadName, System.nanoTime()));
    }

    private static long rest(String threadName) throws InterruptedException{
        long startTime0 = System.nanoTime();

        rest(0, threadName, sleepTime);

        long diff = (System.nanoTime() - startTime0);
        System.out.println(diff);

        return diff;
    }

    private static long rest_SingleThread(String threadName){
        try{
           return rest(threadName);
        }catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        return -1;
    }

    private static long rest_multipleThread(){
        long startTime1 = System.nanoTime();
        Callable<Long> task = () -> {
            try {
                String threadName = Thread.currentThread().getName();

                long diff = rest(threadName);

                return diff;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };


        ExecutorService executor = Executors.newFixedThreadPool(threadNum);


        List<Callable<Long>> callables = new ArrayList<>(threadNum);

        for(int i = 0; i < threadNum; i++){
            callables.add(task);
        }

        try{
            long total = 0;
            executor.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        }
                        catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    //.average();
                    .forEach(System.out::println);
//                    .forEach(s -> {
//                        total += s;
//                     });

            return total;

        }catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
//            if (!executor.isTerminated()) {
//                System.err.println("cancel non-finished tasks");
//                executor.shutdownNow();
//                System.out.println("shutdown finished");
//            }else{
            System.err.println("All tasks finished");
//            }


        }

        System.out.println(" == " + ( System.nanoTime() - startTime1) );

        return -1;
    }

    public static void main(String[] args) {

        rest_SingleThread("main");
        rest_SingleThread("main");
        rest_SingleThread("main");

        long total = 0;
        for(int i = 0; i < N; i++){
            total += rest_SingleThread("main");
        }
        long t = total / N;
        System.out.println("=======" + t);

        System.out.println();
//        rest_multipleThread();
//        rest_multipleThread();
//
//        System.out.println();
//        total = 0;
//        for(int i = 0; i < N; i++){
//            total += rest_multipleThread();
//        }
//        long tt = total / N;
//        System.out.println("=======" + tt);

        System.out.println();

        rest_SingleThread("main");
        rest_SingleThread("main");

    }

}
