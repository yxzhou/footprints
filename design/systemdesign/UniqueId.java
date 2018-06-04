package fgafa.design.systemdesign;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * 
 * Implement a function, return no duplicated result to every call 
 *
 */

/*
 * Q: Implement a function, return no duplicated result to every call ?
 * A: Do it as a counter 
 *    
 *  Q: How to do if the application was restart ?
 *  A: need persist the counter variable.
 *  
 *  Q: How to do if there are lots of request call getNext() ? 
 *  A: need synchronized getNext().
 *
 *  Q: How to make it high throughput and low latency ? 
 *  A: There are 2 steps,  
 *    1) counter++, persist counter
 *    2) return counter
 *  #1 will be the bottleneck. 
 *  Here the solution is to make #1 and #2 asychronized, and create a batch of counters at one time, reduce the persist call. 
 *  Example implement it with a blocking queue, 
 *  
 *  Q: How to do if it's used in multiple applications ? 
 *  A: 2 points:
 *   1) use different UniqueId to different applications as possible, example adminId and JobId can be from different UniqueId. 
 *   2) cache unique id in multiple Queue, the producer (that only one producer) check every Queue and filled with counters.   
 *  
 *  Q: Let's assume this application was deployed in 2 datacenter, how to make the result still no duplicated ? 
 *  A: need implement UUID, 
 *  
 *  
 */
class Counter {
    int counter = 0;

    public int getNext() {
        return counter++;
    }
}

public class UniqueId {
    int counter = 0;
    
    UniqueId(){
        counter = getCounter();
    }
    
    public int getNext(){
        counter++;
        saveCounter(counter);
        return counter;
    }
    
    private int getCounter(){
        //get from persistence
        return 0;
    }
    
    private void saveCounter(int counter){
        // persist counter
    }
}


class UniqueId_lowLatency {
    final int BATCHSIZE = 100;
    final int BUFFERSIZE = 20;
    
    int counter = 0;
    ExecutorService executor = Executors.newFixedThreadPool(6);
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(BATCHSIZE + BUFFERSIZE);
    
    UniqueId_lowLatency(){
        counter = getCounter();
        produceCounter();
    }
    
    //consumer
    public Future<Integer> getNext() {

        Future<Integer> result = executor.submit(
                    new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {                            
                            return queue.take();
                        }
                    });
        
        return result;
    }

    //producer
    private Future<Integer> produceCounter() {

        Future<Integer> result = executor.submit(
                    new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            while(true){
                                if(queue.size() < BUFFERSIZE ){
                                    for(int i = 0; i < BATCHSIZE; i++){
                                        counter++;
                                        queue.put(counter);
                                    }
                                    
                                    saveCounter(counter);
                                }
                                
                                Thread.sleep(20);
                            }
                        }
                    });
        
        return result;
    }
    
    private void closeExecutor(){
        executor.shutdown();
        
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("All tasks are finished!");
    }
    
    @Override
    public void finalize(){
        closeExecutor();
    }
    
    private int getCounter(){
        //get from persistence
        return 0;
    }
    
    private void saveCounter(int counter){
        // persist counter
    }
}

class UniqueId_uuid {
    int counter = 0;

    public UUID getNext(){
        UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");     
        
        return UUID.randomUUID();   
    }
}
