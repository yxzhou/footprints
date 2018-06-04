package fgafa.datastructure.stack;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;


/**
 * 
 * Implement the following operations of a stack using queues.
 * 
 * push(x) -- Push element x onto stack. 
 * pop() -- Removes the element on top of the stack. 
 * top() -- Get the top element. 
 * empty() -- Return whether the stack is empty. 
 * 
 * Notes: 
 * 1 You must use only standard operations of a queue -- which means only push to
 * back, peek/pop from front, size, and is empty operations are valid. 
 * 2 Depending on your language, queue may not be supported natively. You may 
 * simulate a queue by using a list or deque (double-ended queue), as long as 
 * you use only standard operations of a queue. 
 * 3 You may assume that all operations are valid (for example, no pop or top 
 * operations will be called on an empty stack).
 *
 */
public class MyStack {
	Queue<Integer> queue = new LinkedList<>();
	//
    // Push element x onto stack.
    public void push(int x) {
    	queue.offer(x);
    }

    // Removes the element on top of the stack.
	public void pop() {
		if (queue.isEmpty()) {
			return;
		}
		rotate(queue.size() - 1);
		queue.poll();
	}

    // Get the top element.
	public int top() {
		if (queue.isEmpty()) {
			throw new EmptyStackException();
		}
		rotate(queue.size() - 1);
		int result = queue.peek();
		rotate(1);
		return result;
	}

    private void rotate(int times){
    	for(int i= 0; i < times; i++){
    		queue.offer(queue.poll());
    	}
    }
    
    // Return whether the stack is empty.
    public boolean empty() {
        return queue.isEmpty();
    }
}
