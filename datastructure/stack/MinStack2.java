package datastructure.stack;

import java.util.Stack;

/**
 * Continue on MinStack.  m2
 * 
 *
 */
public class MinStack2 {

    private final Stack<Integer> stack;
    private int min;

    public MinStack2() {
        stack = new Stack<>();
        min = Integer.MAX_VALUE;
    }

    public void push(int x) {
        if(min >= x){
            stack.add(min);
            min = x;
        }
        
        stack.push(x);
    }

    public int pop() {
        int top = stack.pop();

        if (top == min) {
            min =  stack.pop();
        }

        return top;
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
