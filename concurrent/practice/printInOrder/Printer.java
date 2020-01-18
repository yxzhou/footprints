package fgafa.concurrent.practice.printInOrder;

public interface Printer {
    void first(Runnable r) throws InterruptedException;
    void second(Runnable r) throws InterruptedException;
    void third(Runnable r) throws InterruptedException;
}
