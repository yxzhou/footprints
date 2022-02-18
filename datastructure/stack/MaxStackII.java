/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.stack;

import java.util.Stack;

/**
 * _https://www.lintcode.com/problem/859
 * 
 * Design a max stack that supports push, pop, top, peekMax and popMax.
 *   push(x) -- Push element x onto stack.
 *   pop() -- Remove the element on top of the stack and return it.
 *   top() -- Get the element on the top.
 *   peekMax() -- Retrieve the maximum element in the stack.
 *   popMax() -- Retrieve the maximum element in the stack, and remove it. If you find more than one maximum elements, 
 *               only remove the top-most one.
 * 
 * Constraints:
 *   -1e7 <= x <= 1e7
 *   Number of operations won't exceed 10000.
 *   The last four operations won't be called when stack is empty.
 * 
 * Example
 * Input:
 * push(5)
 * push(1)
 * push(5)
 * top()
 * popMax()
 * top()
 * peekMax()
 * pop()
 * top()
 * 
 * Output:
 * 5
 * 5
 * 1
 * 5
 * 1
 * 5
 * 
 * Thoughts:
 *    m1) two stack, one is to store all nums, the other is to store the max.
 *    push, pop, top, peekMax,  Time O(1)
 *    popMax  Time O(n)
 * 
 *    m2) TreeSet<Node>, Node is DoubleLinkedNode, it's to store the sequence. TreeSet is to store the max.
 *    top peekMax,  Time O(1)
 *    push, pop, proMax  Time O(logn) 
 *    
 */
public class MaxStackII {
    Stack<Integer> datas;
    Stack<Integer> max;

    public MaxStackII() {
        datas = new Stack<>();
        max = new Stack<>();
    }

    /*
     * @param number: An integer
     * @return: nothing
     */    
    public void push(int x) {
        if(datas.isEmpty() || x >= max.peek() ){
            max.add(x);
        }

        datas.add(x);
    }

    public int pop() {
        int top = datas.pop();
        if(top == max.peek()){
            max.pop();
        }

        return top;
    }

    /*
     * @return: An integer
     */    
    public int top() {
        return datas.peek();
    }

    /*
     * @return: An integer
     */    
    public int peekMax() {
        return max.peek();
    }

    /*
     * @return: An integer
     */    
    public int popMax() {
        int top = max.pop();

        Stack<Integer> tmp = new Stack<>();
        while(datas.peek() != top){
            tmp.add(datas.pop());
        }

        datas.pop(); // pop the max

        while(!tmp.isEmpty()){
            push(tmp.pop());
        }

        return top;
    }
}
