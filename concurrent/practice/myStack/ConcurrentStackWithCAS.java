package fgafa.concurrent.practice.myStack;

public class ConcurrentStackWithCAS {
    class StackNode {
        StackNode next;
        int data;
    }

    // lock cmpxchg
    class AtomicReference<T> {
        T ref;

        T get() { return ref; }

        void set(T newRef) { ref = newRef; }

        boolean compareAndSet(T expect, T update)
        {
            if (expect == ref)
            {
                ref = update;
                return true;
            }

            return false;
        }
    }

    AtomicReference<StackNode> head = new AtomicReference<>();

    /**
     *  Given StackNode class and head.
     *  Problem:  implement the pop() and push(data), support concurrency
     *
     */

    int pop(){
        while(true){
            StackNode result = head.get();

            if(result == null){
                throw new IllegalStateException("The stack is empty, not support pop().");
            }

            if(head.compareAndSet(result, result.next)){
                return result.data;
            }
        }
    }

    void push(int data){
        StackNode curr = new StackNode();
        curr.data = data;

        while(true){
            curr.next = head.get();

            if(head.compareAndSet(curr.next, curr)){
                break;
            }
        }

    }
}
