package fgafa.concurrent.semaphore;

/**
 * implements a semaphore with Lock and Condition
 */
public class SimpleSemaphore implements MySemaphore{
    private boolean signal = false;


    @Override
    public synchronized void acquire() throws InterruptedException {
        signal = true;
        this.notify();
    }
    
    @Override
    public synchronized void release() throws InterruptedException {
        while(!signal){
            this.wait();
        }

        this.signal = false;
    }
}


class SendingThread implements Runnable{

    MySemaphore semaphore = null;

    SendingThread(MySemaphore semaphore){
        this.semaphore = semaphore;
    }

    @Override
    public void run(){
        // do something, then send out signal
        try {
            this.semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class ReceiveingThread implements Runnable{

    MySemaphore semaphore = null;

    ReceiveingThread(MySemaphore semaphore){
        this.semaphore = semaphore;
    }

    @Override
    public void run(){
        while(true){
            try {
                this.semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //receive a signal, then do something
        }
    }

}