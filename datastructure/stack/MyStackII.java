/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.stack;

import java.util.LinkedList;

/**
 *
 * Implement a stack. You can use any data structure inside a stack except stack itself to implement it.
 * 
 * Example 1:
 * Input:
 * push(1)
 * pop()
 * push(2)
 * top()  // return 2
 * pop()
 * isEmpty() // return true
 * push(3)
 * isEmpty() // return false
 * 
 * 
 * @author yuanxi
 */
public class MyStackII {
    LinkedList<Integer> list = new LinkedList<>();

    /*
     * @param x: An integer
     * @return nothing
     */
    public void push(int x) {
        list.add(x);
    }

    /*
     * @return nothing
     */
    public void pop() {
        if(isEmpty()){
            //throw new IllegalAccessException();
            return;
        }

        list.remove(list.size() - 1);
    }

    /*
     * @return An integer
     */
    public int top() {
        if(isEmpty()){
            //throw new IllegalAccessException();
            System.out.println(" stack is empty. ");
        }

        return list.get(list.size() - 1);
    }

    /*
     * @return True if the stack is empty
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

}
