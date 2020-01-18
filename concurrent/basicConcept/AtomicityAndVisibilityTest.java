package fgafa.concurrent.basicConcept;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * refer to:
 *    http://jeremymanson.blogspot.com/2007/08/atomicity-visibility-and-ordering.html
 *    https://www.cnblogs.com/dolphin0520/p/3920373.html
 *
 * When a concurrent program is not correctly written, the errors tend to fall into one of the three categories:
 *  atomicity,  visibility or ordering
 *
 * 1) Atomicity
 *   which is atomicity?
 *   a) x = 10;
 *   b) y = x;
 *   c) x++;
 *   d) x = x + 1;
 *
 *  Result:
 *   #b includes 2 operators, get x value from memory and set x value to y's memory address.
 *   #a in 32 bits OS, long is 64 bit, it need 2 operators to 'long x = 10L'. Only in the new JDK, JMM make it's
 *   atomicity to get and set a primitive variables
 *
 *   synchronized, atomic wrapper is designed for atomicity.
 *
 * 2) Visibility
 *   If two threads are working on a shared variable, x = 10, The x will have 3 copies, in main memory instantly and
 *   two thread local cache. if one thread modifies x, it might not reflect in the original one in main memory instantly.
 *   And the other thread is not aware of the modified value which leads to data inconsistency.
 *
 *   synchronized / locking, atomic wrapper and volatile is designed for visibility
 *
 * 3) ordering
 *   in JMM, compiler and processor will do Instruction Reorder (topology sort) for optimizing performance.
 *   synchronized / locking is designed for getting intuitive ordering constraints
 *
 *   volatile can avoid the instruction reorder on itself.
 *
 *   {
 *       int x = 2;     // l1
 *       int y = 0;     // l2
 *       volatile boolean flag = true;  // l3
 *       x = 4;         // l4
 *       y = -1;        // l5
 *   }
 *
 *   if l3 doesn't have volatile, the order maybe (l1, l2, l3) -> (l4, l5)
 *   if l3 does have volatile, the order maybe (l1, l2) -> l3 -> (l4, l5)
 */

interface Increase{
    //int i = 0;
    void increase();

    int get();
}

class MyThread implements Runnable {

    Increase increase;

    public MyThread(Increase increase){
        this.increase = increase;
    }

    @Override
    public void run(){
        for (int j = 0; j < 10; j++) {
            increase.increase();
        }
    }
}

public class AtomicityAndVisibilityTest {

    @Test
    public void test1(){

        final Random random = new Random();

        List<Increase> sharedObjects = Arrays.asList(
             new Increase() { //not thread safe, i++ is not atomic operator
                private int i = 0;

                @Override
                public void increase(){
                    i++;

                    //System.out.println("---" + i + " " + Thread.currentThread().getName());
                }

                @Override
                public int get(){
                    return i;
                }
            },
            new Increase() { //not thread safe, i++ is not atomic operator, 'volatile' only works on visibility
                private volatile int i = 0;

                @Override
                public void increase(){
                    i++;

                    //System.out.println("---" + i + " " + Thread.currentThread().getName());
                }

                @Override
                public int get(){
                    return i;
                }
            },
            new Increase() { // thread safe, i's update and get both are in synchronized
                 /** volatile */
                 private int i = 0;

                public synchronized void increase(){
                    i++;

                    //System.out.println("---" + i + " " + Thread.currentThread().getName());
                }
                @Override
                public synchronized int get(){
                    return i;
                }
            },
            new Increase() { // thread safe, AtomicInteger is atomicity and visibility
                private AtomicInteger i = new AtomicInteger(0);

                public synchronized void increase(){
                    i.incrementAndGet();

                    //System.out.println("---" + i + " " + Thread.currentThread().getName());
                }
                @Override
                public synchronized int get(){
                    return i.get();
                }
            }
        );

        final int TIMES = 50;
        for(Increase sharedObject : sharedObjects) {
            ExecutorService taskExecutor = Executors.newFixedThreadPool(10);

            for (int i = 0; i < TIMES; i++) {
                taskExecutor.submit(new MyThread(sharedObject));
            }

            //System.out.println("1 - "+ sharedObject.get() + " " + Thread.currentThread().getName());

            taskExecutor.shutdown();

            //System.out.println("2 - "+sharedObject.get() + " " + Thread.currentThread().getName());

            try{
                taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e){
                //
            }

            System.out.println("3 - "+sharedObject.get() + " " + Thread.currentThread().getName());
        }


    }


    @Test
    public void test2(){

        final Random random = new Random();

        List<Increase> sharedObjects = Arrays.asList(
                new Increase() { //not thread safe, i++ is not atomic operator
                    private Integer i = 0;

                    @Override
                    public void increase(){
                        i++;

                        //System.out.println("---" + i + " " + Thread.currentThread().getName());
                    }

                    @Override
                    public int get(){
                        return i;
                    }
                },
                new Increase() { //not thread safe, i++ is not atomic operator, 'volatile' only works on visibility
                    private volatile Integer i = 0;

                    @Override
                    public void increase(){
                        i++;

                        //System.out.println("---" + i + " " + Thread.currentThread().getName());
                    }

                    @Override
                    public int get(){
                        return i;
                    }
                },
                new Increase() { // thread safe, i's update and get both are in synchronized
                    /** volatile */
                    private Integer i = 0;

                    public synchronized void increase(){
                        i++;

                        //System.out.println("---" + i + " " + Thread.currentThread().getName());
                    }
                    @Override
                    public synchronized int get(){
                        return i;
                    }
                },
                new Increase() { // thread safe, AtomicInteger is atomicity and visibility
                    private AtomicInteger i = new AtomicInteger(0);

                    public synchronized void increase(){
                        i.incrementAndGet();

                        //System.out.println("---" + i + " " + Thread.currentThread().getName());
                    }
                    @Override
                    public synchronized int get(){
                        return i.get();
                    }
                }
        );

        final int TIMES = 50;
        for(Increase sharedObject : sharedObjects) {
            ExecutorService taskExecutor = Executors.newFixedThreadPool(10);

            for (int i = 0; i < TIMES; i++) {
                taskExecutor.submit(new MyThread(sharedObject));
            }

            //System.out.println("1 - "+ sharedObject.get() + " " + Thread.currentThread().getName());

            taskExecutor.shutdown();

            //System.out.println("2 - "+sharedObject.get() + " " + Thread.currentThread().getName());

            try{
                taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e){
                //
            }

            System.out.println("3 - "+sharedObject.get() + " " + Thread.currentThread().getName());
        }


    }
}



