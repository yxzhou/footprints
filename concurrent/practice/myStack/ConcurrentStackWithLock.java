package fgafa.concurrent.practice.myStack;

public class ConcurrentStackWithLock {

    class StackNode {
        StackNode next;
        int data;
    }

    class Lock {
        void lock(){

        }

        void unlock(){

        }
    }

    StackNode head;

    Lock lock = new Lock();

    /**
     *  Given StackNode class and head and lock
     *  Problem:  implement the pop() and push(data), support concurrency
     *
     */

    int pop(){
        if(head == null){
            throw new IllegalStateException("The stack is empty, not support pop().");
        }

        lock.lock();

        if(head == null){
            throw new IllegalStateException("The stack is empty, not support pop().");
        }

        try{
            int result = head.data;
            head = head.next;

            return result;
        } finally {
            lock.unlock();
        }

    }

    void push(int data){
        StackNode curr = new StackNode();
        curr.data = data;

        lock.lock();

        try{
            curr.next = head;
            head = curr;
        } finally {
            lock.unlock();
        }

    }

}
