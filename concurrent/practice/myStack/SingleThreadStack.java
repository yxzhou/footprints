package fgafa.concurrent.practice.myStack;

public class SingleThreadStack {

    class StackNode {
        StackNode next;
        int data;
    }


    StackNode head;

    /**
     *  Given StackNode class and head.
     *  Problem:  implement the pop() and push(data)
     *
     */

    int pop(){
        if(head == null){
            throw new IllegalStateException("The stack is empty, not support pop().");
        }

        int result = head.data;
        head = head.next;

        return result;
    }

    void push(int data){
        StackNode curr = new StackNode();
        curr.data = data;

        curr.next = head;
        head = curr;
    }

}
