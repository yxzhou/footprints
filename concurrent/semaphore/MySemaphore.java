package fgafa.concurrent.semaphore;

/**
 * Created by joeyz on 3/1/17.
 */
public interface MySemaphore {
    public void acquire() throws InterruptedException;

    public void release() throws InterruptedException;

}
