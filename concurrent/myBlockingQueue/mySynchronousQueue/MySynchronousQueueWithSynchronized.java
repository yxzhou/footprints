package concurrent.myBlockingQueue.mySynchronousQueue;

public class MySynchronousQueueWithSynchronized<E> implements MySynchronousQueue<E>, java.io.Serializable {

    //private boolean putting = false;

    private E item = null;

    @Override
    public synchronized E take() throws InterruptedException {
        while (item == null) {
            wait();
        }

        E result = item;

        item = null;
        //notify();
        notifyAll();

        System.out.println("take: " + result);
        return result;
    }

    @Override
    public synchronized void put(E item) throws InterruptedException {
        if(item == null) {
            return;
        }

        //while (putting){
        while (this.item != null) {
            wait();
        }

        //putting = true;

        this.item = item;
        //notify();
        notifyAll();

        System.out.println("put: " + this.item);

//        while (item != null) {
//            wait();
//        }

//        putting = false;
//        notifyAll();
    }

}
