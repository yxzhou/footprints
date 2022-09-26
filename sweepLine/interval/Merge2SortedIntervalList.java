package sweepLine.interval;


/**
 * _https://www.lintcode.com/problem/839 
 *
 * Merge two sorted (ascending) lists of interval and return it as a new sorted list. The new sorted list should be made
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
 * Followup:
 *   the above it's OR. How about AND. 
 *   Example, 
 *   Input: list1 = [[0, 2], [5, 10], [16, 20]] and list2 = [[1, 5], [10, 18], [20, 23]]
 *   the output of OR is [0, 23] 
 *   the output of AND is [[1, 2], [5, 5], [10, 10], [16, 18], [20, 20]]
 * 
 *   similar with TimeIntersection __https://www.lintcode.com/problem/839  
 * 
 * 
 * 
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import util.Misc;

public class Merge2SortedIntervalList {

    /**
     * @param list1: one of the given list
     * @param list2: another list
     * @return the new sorted list of interval
     */
    public List<Interval> mergeTwoInterval(List<Interval> list1, List<Interval> list2) {
        if(list1 == null || list1.isEmpty()){
            return list2 == null || list2.isEmpty()? Collections.EMPTY_LIST : list2;
        }else if(list2 == null || list2.isEmpty()){
            return list1;
        }


        List<Interval> result = new ArrayList<>();

        Interval it1 = list1.get(0);
        Interval it2 = list2.get(0);
        int min = Math.min(it1.start, it2.start);
        Interval pre = new Interval(min, min); //  
        Interval curr;
        for( int i = 0, j = 0, n = list1.size(), m = list2.size(); i < n || j < m; ){
            it1 = null;
            if(i < n){
                it1 = list1.get(i);
            }
            it2 = null;
            if(j < m){
                it2 = list2.get(j);
            }

            //compare 
            if(it1 == null || (it2 != null && it1.start > it2.start) ){
                curr = it2;
                j++;
            }else{
                curr = it1;
                i++;
            }

            //merge pre and curr
            if(pre.end < curr.start){
                result.add(pre);
                pre = curr;
            }else{ //overlap, 
                pre.end = Math.max(pre.end, curr.end);
            }
        }

        if(pre != null){
            result.add(pre);
        }

        return result;
    }
    
    public List<Interval> mergeTwoInterval_2(List<Interval> list1, List<Interval> list2) {

        if(list1 == null || list1.isEmpty()){
            return list2 == null || list2.isEmpty()? Collections.EMPTY_LIST : list2;
        }else if(list2 == null || list2.isEmpty()){
            return list1;
        }

        List<Interval> result = new LinkedList<>();
        
        Interval it1 = list1.get(0);
        Interval it2 = list2.get(0);
        int min = Math.min(it1.start, it2.start);
        Interval pre = new Interval(min, min);
        result.add(pre);

        Interval curr; //candidate
        for( int i = 0, j = 0, n = list1.size(), m = list2.size(); i < n || j < m; ){
            it1 = null;
            if(i < n){
                it1 = list1.get(i);
            }
            it2 = null;
            if(j < m){
                it2 = list2.get(j);
            }

            if(it1 == null || (it2 != null && it2.start < it1.start)){
                curr = it2;
                j++;
            }else {
                curr = it1;
                i++;
            }

            if(pre.end < curr.start ){
                pre = curr;
                result.add(pre);
            }else{
                pre.end = Math.max(pre.end, curr.end);
            }
        }

        return result;
    }
    
    public List<Interval> intervalAnd(Interval[] list1, Interval[] list2) {
        if (list1 == null || list2 == null) {
            return Collections.EMPTY_LIST;
        }
        
        List<Interval> result = new LinkedList<>();

        Interval it1;
        Interval it2;
        for (int i = 0, j = 0; i < list1.length && j < list2.length; ) {
            it1 = list1[i];
            it2 = list2[j];

            if (it1.end < it2.end) { // it's "end" better than "start" 
                i++;
            } else if (it1.end > it2.end) {
                j++;
            } else { // interval1.end == interval2.end
                i++;
                j++;
            }
            
            if ((it1.end >= it2.start && it1.end <= it2.end) || (it2.end >= it1.start && it2.end <= it1.end)) { //overlap
                result.add(new Interval(Math.max(it1.start, it2.start), Math.min(it1.end, it2.end)));
            }
        }

        return result;
    }
    
    
    public static void main(String[] args){
        Merge2SortedIntervalList sv = new Merge2SortedIntervalList();
        TimeIntersection timeIntersection = new TimeIntersection();
        
        int[][][][] inputs = {
            {
                {{1, 2}, {3, 4}},
                {{2, 3}, {5, 6}},
                {{1, 4}, {5, 6}},
                {{2, 2}, {3, 3}}
            },
            {
                {{1, 2}, {3, 4}},
                {{4, 5}, {6, 7}},
                {{1, 2}, {3, 5}, {6, 7}},
                {{4, 4}}
            },
            {
                {{0, 2}, {5, 10}, {16, 20}},
                {{1, 5}, {10, 18}, {20, 23}},
                {{0, 23}},
                {{1, 2}, {5, 5}, {10, 10}, {16, 18}, {20, 20}}
            },
            {
                {{1, 2}, {5, 100}},
                {{1, 6}},
                {{1, 100}},
                {{1, 2}, {5, 6}}
            },
            {
                {{1, 2}, {10, 15}},
                {{3, 5}, {7, 9}},
                {{1, 2}, {3, 5}, {7, 9}, {10, 15}},
                {}
            }
        };
        
        for(int[][][] input : inputs){
            
            Assert.assertEquals(Misc.array2String(input[2], true), Misc.array2String(sv.mergeTwoInterval(Arrays.asList(Interval.build(input[0])), Arrays.asList(Interval.build(input[1]))), true).toString());
            Assert.assertEquals(Misc.array2String(input[2], true), Misc.array2String(sv.mergeTwoInterval_2(Arrays.asList(Interval.build(input[0])), Arrays.asList(Interval.build(input[1]))), true).toString());
        
            Assert.assertEquals(Misc.array2String(input[3], true), Misc.array2String(sv.intervalAnd(Interval.build(input[0]), Interval.build(input[1])), true).toString() );
            Assert.assertEquals(Misc.array2String(input[3], true), Misc.array2String(timeIntersection.timeIntersection(Arrays.asList(Interval.build(input[0])), Arrays.asList(Interval.build(input[1]))), true).toString() );
        }
        
        
    }

}

