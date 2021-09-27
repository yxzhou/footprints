package concurrent.practice.threadInteraction;

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


public class InteractionWithSynchronized {

    //shared objects
    static int count = 0;
    static int end;
    static boolean token = true;

    class MyThread implements Runnable{
        String name;
        boolean flag;

        MyThread(String name, boolean flag){
            this.name = name;
            this.flag = flag;
        }

        @Override public void run(){
            while(count <= end){
                if(flag == token){
                    synchronized (this) {//object level lock
                    //synchronized (MyThread.class){ //class level lock
                        if(count <= end && flag == token){
                            System.out.println(name + " \t " + flag + "\t" + count);

                            count++;
                            token = !flag;
                        }
                    }
                }else{
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        //ignore
                    }
                }
            }
        }
    }


    public static void main(String[] args){

        end = 100;
        InteractionWithSynchronized outer = new InteractionWithSynchronized();

        new Thread(outer.new MyThread("t11", true)).start();
        new Thread(outer.new MyThread("t12", true)).start();

        new Thread(outer.new MyThread("t21", false)).start();
        new Thread(outer.new MyThread("t22", false)).start();
    }

}
