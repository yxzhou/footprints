package fgafa.datastructure.stack;

import java.util.Stack;
/**
 * 
 Design a stack that supports push, pop, top, and retrieving the minimum
 * element in constant time.
 * 
 * push(x) -- Push element x onto stack. 
 * pop() -- Removes the element on top of the stack, and return
 * top() -- Get the top element. 
 * getMin() -- Retrieve the minimum element in the stack.
 *
 * Implement a stack with min() function, which will return the smallest number in the stack.
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
 * Note
 * min operation will never be called if there is no number in the stack. It should support push, pop and min operation all in O(1) cost.
 *
 */
public class MinStack
{

	private Stack<Integer> full = new Stack<>();
	private Stack<Integer> min = new Stack<>();
	
    public void push(int x) {
        full.push(x);
        
        if(min.isEmpty() || x <= min.peek()){
        	min.push(x);
        }
    }

    public int pop() {
        int top = full.pop();
        
        if(top == min.peek()){
        	min.pop();
        }
        
        return top;
    }
    
    public int pop_2() {
        //if(full.peek() == min.peek()){   //it's wrong
        if(full.peek().equals(min.peek())){
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
