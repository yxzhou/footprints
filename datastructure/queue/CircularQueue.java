/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.queue;

/**
 * _https://www.lintcode.com/problem/955
 * 
 * Implement queue by circulant array. You need to support the following methods:
 *
 * CircularQueue(n): initialize a circular array with size n to store elements 
 * boolean isFull(): return true if the array is full 
 * boolean isEmpty(): return true if there is no element in the array 
 * void enqueue(element): add an element to the queue 
 * int dequeue(): pop an element from the queue
 *
 * Notes:
 * it's guaranteed in the test cases we won't call enqueue if it's full and we also won't call dequeue if it's empty. So
 * it's ok to raise exception in scenarios described above.
 *
 * Example 1:
 * Input:  CircularQueue(5) 
 * isFull()              
 * isEmpty() 
 * enqueue(1) 
 * enqueue(2) 
 * dequeue() 
 * Output: ["false","true","1"]
 *
 *  similar with QueueWithFixedSizeOfArrays
 *   
 */
public class CircularQueue {
    
    int[] datas;
    int head; 
    int tail; 
    int size;

    public CircularQueue(int n) {
        this.datas = new int[n];
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }
    /**
     * @return return true if the array is full
     */
    public boolean isFull() {
        return size == datas.length;
    }

    /**
     * @return return true if there is no element in the array
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param element: the element given to be added
     */
    public void enqueue(int element) {
        datas[tail] = element;
        tail = (tail + 1) % datas.length;
        size++;
    }

    /**
     * @return pop an element from the queue
     */
    public int dequeue() {
        size--;
        int result = datas[head];
        head = (head + 1) % datas.length;
        return result;
    }
    
}
