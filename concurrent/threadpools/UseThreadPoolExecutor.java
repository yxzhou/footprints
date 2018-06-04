package fgafa.concurrent.threadpools;

import sun.jvm.hotspot.utilities.WorkerThread;

import java.util.concurrent.*;

/**
 * Executors class provide simple implementation of ExecutorService using ThreadPoolExecutor but ThreadPoolExecutor provides much more feature than that.
 * We can specify the number of threads that will be alive when we create ThreadPoolExecutor instance and we can limit the size of thread pool
 * and create our own
 * <p>
 * ThreadPoolExecutor provides several methods using which we can find out the current state of executor, pool size, active thread count and task count.
 * So I have a monitor thread that will print the executor information at certain time interval.
 *
 *  From Alibaba
 * 【强制】线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
 说明：
 Executors各个方法的弊端：
 1）newFixedThreadPool 和 newSingleThreadExecutor: 主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至 OOM。
 2）newCachedThreadPool 和 newScheduledThreadPool: 主要问题是线程数最大数是 Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
 *
 *
 */
public class UseThreadPoolExecutor {

    public static void main(String[] args) {
        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();

        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        //creating the ThreadPoolExecutor
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);

        //start the monitoring thread
        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();

        //submit work to the thread pool
        for (int i = 0; i < 10; i++) {
            executorPool.execute(new MyThread(100000L + i));
        }

        Thread.sleep(30000);

        //shut down the pool
        executorPool.shutdown();

        //shut down the monitor thread
        Thread.sleep(5000);
        monitor.shutdown();

    }

}


class MyMonitorThread implements Runnable {

    private ThreadPoolExecutor executor;
    private int seconds;
    private boolean run = true;

    MyMonitorThread(ThreadPoolExecutor executor, int delay) {
        this.executor = executor;
        this.seconds = delay;
    }

    void shutdown() {
        this.run = false;
    }

    @Override
    public void run() {
        while (run) {
            System.out.println(
                    String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                            this.executor.getPoolSize(),
                            this.executor.getCorePoolSize(),
                            this.executor.getActiveCount(),
                            this.executor.getCompletedTaskCount(),
                            this.executor.getTaskCount(),
                            this.executor.isShutdown(),
                            this.executor.isTerminated()));
            try {

                Thread.sleep(seconds * 1000);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }
    }
}

class RejectedExecutionHandlerImpl implements RejectedExecutionException {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + " is rejected");
    }
}