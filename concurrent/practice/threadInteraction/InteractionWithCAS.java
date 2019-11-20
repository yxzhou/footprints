package fgafa.concurrent.practice.threadInteraction;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Function: 两个线程交替执行打印 1~100
 * <p>
 * non blocking 版：
 * 两个线程轮询volatile变量(flag)
 * 线程一"看到"flag值为1时执行代码并将flag设置为0,
 * 线程二"看到"flag值为0时执行代码并将flag设置未1,
 * 2个线程不断轮询直到满足条件退出
 *
 */

public class InteractionWithCAS implements Runnable{

    //shared objects
    static AtomicInteger count = new AtomicInteger(0);
    static int end = 100;

    static AtomicBoolean token = new AtomicBoolean(true);

    //thread local variables
    String name;
    boolean flag;

    InteractionWithCAS(String name, boolean flag) {
        this.name = name;
        this.flag = flag;
    }

    @Override
    public void run() {
        while (count.get() <= end) {
            boolean next = !flag;

            //todo,  the following is not right

            if (token.compareAndSet(flag, next)) {
                System.out.println(name + " \t " + flag + "\t" + count.get());

                count.incrementAndGet();
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }
    }


    public static void main(String[] args) {

        end = 100;

        new Thread(new InteractionWithCAS("t11", true)).start();
        new Thread(new InteractionWithCAS("t12", true)).start();

        new Thread(new InteractionWithCAS("t21", false)).start();
        new Thread(new InteractionWithCAS("t21", false)).start();
    }



}
