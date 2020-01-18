package fgafa.concurrent.practice.printInOrder;

import java.util.concurrent.atomic.AtomicInteger;

public class CASPrinter implements Printer {
    // need volatile before AtomicInteger
    private final AtomicInteger status;

    public CASPrinter() {
        status = new AtomicInteger(0);
    }

    public void first(Runnable print) throws InterruptedException {
        while(true){
            if(status.compareAndSet(0, 1)){
                // printFirst.run() outputs "first". Do not change or remove this line.
                print.run();
                break;
            }
        }

    }

    public void second(Runnable print) throws InterruptedException {
        while(true){
            if(status.compareAndSet(1, 2)){
                // printSecond.run() outputs "second". Do not change or remove this line.
                print.run();
                break;
            }
        }


    }

    public void third(Runnable print) throws InterruptedException {
        while(true){
            if(status.compareAndSet(2, 0)){
                // printThird.run() outputs "third". Do not change or remove this line.
                print.run();
                break;
            }
        }
    }


}
