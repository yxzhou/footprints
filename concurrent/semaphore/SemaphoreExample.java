package concurrent.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by joeyz on 2/27/17.
 */
public class SemaphoreExample {
    
    public static void main(String[] args){
        
        Semaphore semaphoreAsLock = new Semaphore(1, true);
        
        Semaphore boundedSemaphore = new Semaphore(4);

        //in thread #1
        try {
            boundedSemaphore.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //in thread #2
        boundedSemaphore.release();
        
    }
    
}
