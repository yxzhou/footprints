package fgafa.concurrent.threadInteraction;

/**
 *
 * Function: 两个线程交替执行打印 1~100
 * <p>
 * lock 版：
 * 2+ thread check the AtomicInteger wrapper.
 *
 *    thread 1 or 2, found the wrapper is even, print out and
 *    thread 3 or 4, foudn the wrapper is odd, print out and
 *    until wapper is to 100.
 *
 */


public class InteractionWithNotify {

    static class Wrapper {
        int value = 0;
    }

    static class MyThread implements Runnable{
        String name;
        int end;
        int flag;

        InteractionWithLock.Wrapper wrapper;

        MyThread(String name, int end, int flag, InteractionWithLock.Wrapper wrapper){
            this.name = name;
            this.end = end;
            this.flag = flag;

            this.wrapper = wrapper;
        }

        @Override public void run(){
            while(wrapper.value <= end){
                if((wrapper.value & 1) == flag){
                    synchronized (wrapper){
                        if((wrapper.value & 1) == flag){
                            System.out.println(wrapper.value + " \t " + Thread.currentThread().getName());

                            wrapper.value++;

                            wrapper.notify();
                        }
                    }
                }else{
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        //ignore
                    }
                }
            }
        }
    }


    public static void main(String[] args){

        InteractionWithLock.Wrapper wrapper = new InteractionWithLock.Wrapper();

        new Thread(new InteractionWithLock.MyThread("t11", 100, 0, wrapper)).start();
        new Thread(new InteractionWithLock.MyThread("t12", 100, 0, wrapper)).start();

        new Thread(new InteractionWithLock.MyThread("t21", 100, 1, wrapper)).start();
        new Thread(new InteractionWithLock.MyThread("t22", 100, 0, wrapper)).start();
    }

}
