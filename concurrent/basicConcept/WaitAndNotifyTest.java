package concurrent.basicConcept;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

class SharedObject{
    private volatile List<String> list = new LinkedList<>();

    void add(){
        list.add("bla bla");
    }

    int size(){
        return list.size();
    }
}

/**
 * wait(), force the current thread to wait a lock. it meams the current thread will be added in waiting list of the lock.
 *     If the current thread hold the lock, it will release the lock.
 *     When the lock (the object's monitor) was 'notified', all threads in the waiting list will try to compete the lock.
 *     If this thread got the lock, it will process the next line after the wait(). Usually the wait() is enclosed in a while loop.
 *
 * notify(), wake up a single random thread in the waiting list.
 *     If the current thread still hold the lock, the notify() will be ignored.
 *     Usually it need make sure the lock is free before calling notify()
 *
 * notifyAll(), wake up all threads in the waiting list.
 *
 */

public class WaitAndNotifyTest {

    /**
     * in @Test, it'll not care about the sub-thread running. When the main thread finished, the test() method finished, unless there is source code wait for the sub-thread finished.
     *
     * in Main(), it finished when all threads finished.
     *
     */
//    @Test
//    public void test() {
    public static void main(String[] args){
        final SharedObject sharedObject = new SharedObject();
        final Object lock = new Object();
        //Lock lock = new ReentrantLock();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println("start r1");

                synchronized (lock) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("r1 add 1");
                        sharedObject.add();

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (sharedObject.size() == 5) {
                            System.out.println("r1 do notify");
                            lock.notify(); //this notify will trigger line #93, not immediately  until the current thread release the object's lock.
                        }
                    }

                    /**
                     *
                     */
                    //object.notify();
                }
            }
        };

        Thread thread2 =  new Thread() {
            @Override
            public void run() {
                System.out.println("start r2");

                synchronized (lock){
                    while ( sharedObject.size() != 5 && sharedObject.size() != 10 ){
                        //System.out.println("r2, sharedObject's size: " + sharedObject.size());

                        try {
                            Thread.sleep(100);

                            System.out.println("r2 do wait");

                            lock.wait();

                            System.out.println("r2 wake up");

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("free r2");
                }
            }
        };

        /**
         * run() vs start()
         *
         * run() is just call the run() method, it's still in the Main thread
         * start() is to start a new thread.
         *
         */
        //thread2.run();
        thread2.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread1.start();
    }


}
