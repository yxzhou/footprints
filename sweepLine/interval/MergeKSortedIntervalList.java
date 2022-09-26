/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sweepLine.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/577
 *
 * Merge k sorted (ascending) lists of interval and return it as a new sorted list. The new sorted list should be made
 * by splicing together the intervals of the two lists and sorted in ascending order.
 * 
 * Notes:
 *   The intervals in the given list do not overlap.
 *   The intervals in different lists may overlap.
 * 
 * Example1
 * Input: list1 = [(1,2),(3,4)] and list2 = [(2,3),(5,6)] 
 * Output: [(1,4),(5,6)] 
 * Explanation: 
 *   (1,2),(2,3),(3,4) --> (1,4)
 *   (5,6) --> (5,6)
 *
 * Example2
 * Input: list1 = [(1,2),(3,4)] and list2 = [(4,5),(6,7)] 
 * Output: [(1,2),(3,5),(6,7)] 
 * Explanation: 
 *   (1,2) --> (1,2)
 *   (3,4),(4,5) --> (3,5) 
 *   (6,7) --> (6,7)
 * 
 * Example3
 * Input: list1 = [(1,3),(4,7),(6,8)] and list2 = [(1,2),(9,10)] 
 * Output: [(1,3), (4,8), (9,10)] 
 * Explanation: 
 *   (1,2),(2,3),(3,4) --> (1,4)
 *   (5,6) --> (5,6)
 * 
 */
public class MergeKSortedIntervalList {
    
    class Entry{
        private List<Interval> list;
        private int i; // the index of list
        
        Entry(List<Interval> list, int i){
            this.list = list;
            this.i = i;
        }
        
        boolean isFull(){
            return i == list.size();
        }
        
        Interval get(){
            if(isFull()){
                return null;
            }
            
            return list.get(i);
        }
        
        int getStart(){           
            Interval top = get();
                    
            return top == null ? -1 : top.start;
        }
        
        
    }
    
    public List<Interval> mergeKIntervals(List<List<Interval>> lists) {

        if(lists == null || lists.isEmpty()){
            return Collections.EMPTY_LIST;
        }

        List<Interval> result = new LinkedList<>();
        
        PriorityQueue<Entry> minHeap = new PriorityQueue<>( (a, b) -> Integer.compare(a.getStart(), b.getStart()) );
        for(List<Interval> list : lists){
            if(list == null || list.isEmpty()){
                continue;
            }
            
            minHeap.add(new Entry(list, 0));
        }
        
        int min = minHeap.peek().getStart();
        Interval pre = new Interval(min, min);
        result.add(pre);

        Entry top;
        Interval curr; //candidate
        while( !minHeap.isEmpty() ){
            top = minHeap.poll();
            curr = top.get();
            
            if(pre.end < curr.start ){
                pre = curr;
                result.add(pre);
            }else{
                pre.end = Math.max(pre.end, curr.end);
            }
            
            top.i++;
            
            if(!top.isFull()){
                minHeap.add(top);
            }
        }

        return result;
    }
    
    public static void main(String[] args){
        MergeKSortedIntervalList sv = new MergeKSortedIntervalList();
        
        int[][][][][] inputs = {
            //{ a list of intervalList, expect of merge  }
            {
                {
                    {{1, 2}, {3, 4}},
                    {{2, 3}, {5, 6}}
                },
                {{{1, 4}, {5, 6}}}

            },
            {
                {
                    {{1, 2}, {3, 4}},
                    {{4, 5}, {6, 7}}
                },
                {{{1, 2}, {3, 5}, {6, 7}}}
            },
            {
                {
                    {{0, 2}, {5, 10}, {16, 20}},
                    {{1, 5}, {10, 18}, {20, 23}}
                },
                 {{{0, 23}}}

            },
            {
                {
                    {{1, 3}, {4, 7}, {6, 8}},
                    {{1, 2}, {9, 10}}
                },
                {{{1, 3}, {4, 8}, {9, 10}}}
            }
        };
        
        for(int[][][][] input : inputs){
            List<List<Interval>> lists = Arrays.stream(input[0])
                    .map(a -> Arrays.asList(Interval.build(a)))
                    .collect(Collectors.toCollection(ArrayList::new));
            
            //Misc.printListList(lists);
            
            Assert.assertEquals(Misc.array2String(input[1][0], true), Misc.array2String(sv.mergeKIntervals(lists), true).toString());
        
            //Misc.printList(sv.mergeKIntervals(lists));
            
        }
        
        
    }
    
}
