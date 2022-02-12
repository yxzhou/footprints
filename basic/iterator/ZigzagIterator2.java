/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basic.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Solution for ZigzagIterator and ZigzagIteratorII 
 * 
 */
public class ZigzagIterator2 {

    Queue<Iterator<Integer>> queue;

    /*
    * @param v1: A 1d vector
    * @param v2: A 1d vector
    */public ZigzagIterator2(List<Integer> v1, List<Integer> v2) {
        queue = new LinkedList<>();

        queue.add(v1.iterator());
        queue.add(v2.iterator());
    }

    /*
     * @return: An integer
     */
    public int next() {
        
        Iterator<Integer> iterator = queue.poll();
        int result = iterator.next();

        queue.add(iterator);
        return result;
    }

    /*
     * @return: True if has next
     */
    public boolean hasNext() {
        
        while(!queue.isEmpty() && !queue.peek().hasNext()){
            queue.poll();
        }

        return !queue.isEmpty() && queue.peek().hasNext();

    }
    
}
