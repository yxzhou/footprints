package datastructure.queue;

import java.util.Stack;

/**
 * _https://www.lintcode.com/problem/40
 * 
 * Implement the following operations of a queue using stacks.
 * 
 * push(x) -- Push element x to the back of queue. 
 * pop() -- Removes the element from in front of queue. 
 * peek() -- Get the front element. 
 * empty() -- Return whether the queue is empty.
 * 
 * Notes: 
 * You must use only standard operations of a stack -- which means only push to top, peek/pop from top, size, and
 * is empty operations are valid. Depending on your language, stack may not be supported natively. 
 * You may simulate a stack by using a list or deque, as long as you use only standard operations of a stack. 
 * You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).
 *
 */

public class MyQueue {

    Stack<Integer> in;
    Stack<Integer> out;

    public MyQueue() {
        in = new Stack<>();
        out = new Stack<>();
    }
    
    // Push element x to the back of queue.
    public void push(int x) {
        in.push(x);
    }

    // Removes the element from in front of queue.
    public void pop() {
        if (!out.isEmpty()) {
            transfer(in, out);
        }
        
        out.pop();
    }

    // Get the front element.
    public int peek() {
        if (!out.isEmpty()) {
            transfer(in, out);
        }

        return out.peek();
    }

    private void transfer(Stack<Integer> from,  Stack<Integer> to){
        while(!from.isEmpty()){
            to.add(from.pop());
        }
    }

    // Return whether the queue is empty.
    public boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }
}
