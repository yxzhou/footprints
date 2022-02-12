package datastructure.stack;

import java.util.Stack;

/**
 * _https://www.lintcode.com/problem/12
 * 
 * Design a stack that supports push, pop, top, and retrieving the minimum
 * element in constant time.
 * 
 * push(x) -- Push element x onto stack. 
 * pop() -- Removes the element on top of the stack, and return
 * top() -- Get the top element. 
 * getMin() -- Retrieve the minimum element in the stack.
 *
 * Note:  
 *   min operation will never be called if there is no number in the stack. 
 *   It should support push, pop and min operation all in O(1) cost.
 * 
 * 
 * Example
 * push(1)
 * pop()   // return 1
 * push(2)
 * push(3)
 * min()   // return 2
 * min()   // return 2
 * push(1)
 * min()   // return 1
 * 
 * Thoughts:
 *   m1) two stack, one is to store the full data , the other is to store the min data. 
 *   m2) one stack and one variable, which stores the current min data, store the previous min data in
 *       the stack. 
 *       when new min value appear, store the previous and current min data both in the stack
 * 
 *
 */
public class MinStack {

    private final Stack<Integer> full;
    private final Stack<Integer> min;

    public MinStack() {
        full = new Stack<>();
        min = new Stack<>();
    }

    public void push(int x) {
        full.push(x);

        if (min.isEmpty() || x <= min.peek()) {
            min.push(x);
        }
    }

    public int pop() {
        int top = full.pop();

        if (top == min.peek()) {
            min.pop();
        }

        return top;
    }

    public int pop_2() {
        //if(full.peek() == min.peek()){   //it's wrong
        if (full.peek().equals(min.peek())) {
            min.pop();
        }

        return full.pop();
    }

    public int top() {
        return full.peek();
    }

    public int getMin() {
        return min.peek();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
