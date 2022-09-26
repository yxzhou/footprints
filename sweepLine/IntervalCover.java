package sweepLine;

import sweepLine.interval.Interval;
import java.util.*;
import junit.framework.Assert;

import util.Misc;
import org.junit.Test;

/**
 *
 * Given a set of closed intervals (ranges of integers), find the smallest set of integers that covers all the
 * intervals. If there are multiple smallest sets, return any of them.
 *
 * Example 1,
 *   given the intervals [0, 3], [2, 6], [3, 4], [6, 9] 
 *   return {3, 6} or {3, 7} etc
 * Example 2,
 *   given [0, 4], [1, 2], [5, 7], [6, 7], [6, 9], [8, 10]
 *   return { 1, 6, 8 }, { 2, 7, 9 }, { 1, 7, 8 } etc
 *
 *
 * refer: https://stackoverflow.com/questions/15005008/given-some-integer-ranges-finding-a-smallest-set-containing-at-least-one-intege
 *
 * 
 * 
 */

public class IntervalCover {

    public List<Integer> intervalCover_n(Interval[] intervals) {

        if (null == intervals || 0 == intervals.length) {
            return Collections.EMPTY_LIST;
        }

        Arrays.sort(intervals, (a, b) -> a.end == b.end ? a.start - b.start : a.end - b.end);

        Stack<Integer> result = new Stack<>();
        result.push(intervals[0].end);
        for (Interval interval : intervals) {
            if (result.peek() < interval.start) {
                result.push(interval.end);
            }
        }

        return result;
    }
    
    public List<Integer> intervalCover(Interval[] intervals) {

        if (null == intervals || 0 == intervals.length) {
            return Collections.EMPTY_LIST;
        }
        
        TreeMap<Integer, Set<Integer>> starts = new TreeMap<>();//map< the start, interval id>
        TreeMap<Integer, Set<Integer>> ends = new TreeMap<>();//map< the end, interval id>
        
        for(int i = 0; i < intervals.length; i++){
            starts.computeIfAbsent(intervals[i].start, o -> new HashSet<>()).add(i);
            ends.computeIfAbsent(intervals[i].end, o -> new HashSet<>()).add(i);
        }
        
        HashSet<Integer> visited = new HashSet<>();

        Set<Integer> result = new HashSet<>();
        
        int left = -1; // left line
        int right; // right line
        for(Map.Entry<Integer, Set<Integer>> endSet : ends.entrySet()){
            
            right = endSet.getKey();
            
            for(Integer i : endSet.getValue()){
                if(!visited.contains(i) ){
                    result.add(right);
                    
                    visited.add(i);
                }
            }

            if(result.contains(right)){
                for(Set<Integer> startSet : starts.subMap(left, false, right, true).values()){
                    visited.addAll(startSet);
                }
                
                left = right;
            }
        }
        
        return new ArrayList<>(result);
    }


    public static void main(String[] args) {
        
        int[][][][] inputs = {
            {
                {{0, 3}, {2, 6}, {3, 4}, {6, 9}},
                {{3, 9}}
            },
            {
                {{0, 3}, {2, 6}, {3, 4}, {8, 9}},
                {{3, 9}}
            },
            {
                {{0, 4}, {1, 2}, {5, 7}, {6, 7}, {6, 9}, {8, 10}},
                {{2, 7, 10}}
            }
        };
        
        IntervalCover sv = new IntervalCover();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("-Input-: intervals: %s \n-Output-\nintervalCover_n: %s\nintervalCover: %s", Misc.array2String(input[0]), 
                    Misc.array2String(sv.intervalCover_n(Interval.build(input[0]))).toString(),
                    Misc.array2String(sv.intervalCover(Interval.build(input[0]))).toString() 
            ));
            
            //Assert.assertEquals(input[1][0], sv.intervalCover(Interval.build(input[0])).stream().mapToInt(Integer::intValue).toArray() );
            //Assert.assertEquals(input[1][0], sv.intervalCover_SweepLine(Interval.build(input[0])).stream().mapToInt(Integer::intValue).toArray() );
        }
    }


}
