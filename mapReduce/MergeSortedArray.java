/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapReduce;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Sort integers by Map Reduce framework.
 * In the mapper, key is the document id which can be ignored, value is the integers.
 * In the reducer, your can specify what the key / value is (this depends on how you implement your mapper class). For the output of the reducer class, the key can be anything you want, the value should be ordered. (the order is depending on when you output it)
 * 
 * Example 1:
 * Input:
 *   1: [14,7,9]
 *   2: [10,1]
 *   3: [2,5,6,3]
 *   4: []
 * Output: [1,2,3,5,6,7,9,10,14]
 * 
 * Example 2:
 * Input:
 *   1: [14,7]
 * Output: 7,14]
 * 
 */
public class MergeSortedArray {
    public static class Map {
        public void map(int key, List<Integer> value,
                        OutputCollector<String, List<Integer>> output) {
            Collections.sort(value);
            output.collect("fun", value);
        }
    }
        
    public static class Reduce {
        class Node{
            Iterator<Integer> datas;
            int value;

            Node(List<Integer> datas){
                this.datas = datas.iterator();
                value = this.datas.next();
            }
        }

        public void reduce(String key, List<List<Integer>> values,
                           OutputCollector<String, List<Integer>> output) {
            
            PriorityQueue<Node> minHeap = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.value, n2.value));

            for(List<Integer> list : values){
                if(list != null && !list.isEmpty()){
                    minHeap.add(new Node(list));
                }
            }

            List<Integer> result = new LinkedList<>();
            Node top;
            while(!minHeap.isEmpty()){
                top = minHeap.poll();
                result.add(top.value);
                if(top.datas.hasNext()){
                    top.value = top.datas.next();
                    minHeap.add(top);
                }
            }

            output.collect(key, result);
        }
    }
}
