package fgafa.concurrent.myBlockingQueue.mySynchronousQueue;

public interface MySynchronousQueue<E> {

    E take() throws InterruptedException;

    void put(E item) throws InterruptedException;
}
