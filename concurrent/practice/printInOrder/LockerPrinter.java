package fgafa.concurrent.practice.printInOrder;

public class LockerPrinter implements Printer{

    private volatile boolean run1;
    private volatile boolean run2;
    private volatile boolean run3;

    public LockerPrinter(){
        run1 = true;
        run2 = false;
        run3 = false;
    }

    public synchronized void first(Runnable print) throws InterruptedException {
        while(!run1){
            wait();
        }

        // printFirst.run() outputs "first". Do not change or remove this line.
        print.run();
        run1 = false;
        run2 = true;
        notifyAll();
    }

    public synchronized void second(Runnable print) throws InterruptedException {
        while(!run2){
            wait();
        }

        // printSecond.run() outputs "second". Do not change or remove this line.
        print.run();
        run2 = false;
        run3 = true;
        notifyAll();
    }

    public synchronized void third(Runnable print) throws InterruptedException {
        while(!run3){
            wait();
        }

        // printThird.run() outputs "third". Do not change or remove this line.
        print.run();
        run3 = false;
        run1 = true;
        notifyAll();
    }

}
