/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.heap;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 *
 *  Implement a data structure, provide two interfaces:
 *    add(number). Add a new number in the data structure.
 *    topk(). Return the top k largest numbers in this data structure. k is given when we create the data structure.
 * 
 * Example1
 * Input: 
 *   s = new Solution(3);
 *   s.add(3)
 *   s.add(10)
 *   s.topk()
 *   s.add(1000)
 *   s.add(-99)
 *   s.topk()
 *   s.add(4)
 *   s.topk()
 *   s.add(100)
 *   s.topk()
 * Output: 
 *   [10, 3]
 *   [1000, 10, 3]
 *   [1000, 10, 4]
 *   [1000, 100, 10]

 * 
 */
public class TopKLargetNumbersII {
    PriorityQueue<Integer> minHeap;
    int k;

    /*
    * @param k: An integer
    */
    public TopKLargetNumbersII(int k) {
        this.k = k;
        minHeap = new PriorityQueue<>();
    }

    /*
     * @param num: Number to be added
     * @return: nothing
     */
    public void add(int num) {
        if(minHeap.size() < k){
            minHeap.add(num);
        }else if(minHeap.peek() < num){
            minHeap.poll();
            minHeap.add(num);
        }
    }

    /*
     * @return: Top k element
     *
     */
    public List<Integer> topk() {
        //Note: The iterator of PriorityQueue does not return the elements in any particular order.
        /**- method 1 -
        LinkedList<Integer> result = new LinkedList<>();
        
        while(!minHeap.isEmpty()){
            result.addFirst(minHeap.poll());
        }

        for(int x : result){
            minHeap.add(x);
        }

        return result;
        */
        
        /**- method 1 -
        List<Integer> result = new ArrayList<>(pq);
        Collections.sort(result, Collections.reverseOrder());
        return result;
        */
        
        /**- method 1 -*/
        return minHeap.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }
}
