package sweepLine.interval;

import sweepLine.interval.Interval;
import java.util.*;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/156
 * 
 * Given a collection of intervals, merge all overlapping intervals.
 * 
 * Example 1:
 * Input: [(1,3)]
 * Output: [(1,3)]
 * 
 * Example 2:
 * Input:  [(1,3),(2,6),(8,10),(15,18)]
 * Output: [(1,6),(8,10),(15,18)]
 * 
 * Challenge O(n log n) time and O(1) extra space.
 */

public class MergeIntervals {

    /**
     * @param intervals: interval list.
     * @return A new interval list.
     */
    public List<Interval> merge(List<Interval> intervals) {
        if(intervals == null || intervals.size() < 2){
            return intervals;
        }

        Collections.sort(intervals, (a, b) -> Integer.compare(a.start, b.start) ); // order by interval's start

        List<Interval> result = new ArrayList<>();
        Interval pre = intervals.get(0);
        Interval curr;
        for(int i = 1, n = intervals.size() ; i < n; i++ ){
            curr = intervals.get(i);

            if(pre.end < curr.start){
                result.add(pre);
                pre = curr;
            }else { //overlap
                pre.end = Math.max(pre.end, curr.end);
            }
        }

        if(pre != null){
            result.add(pre);
        }

        return result;
    }
    
    public List<Interval> merge_n(List<Interval> intervals) {
        List<Interval> result = new ArrayList<>();

        if (null == intervals || intervals.isEmpty()){
            return result;
        }

        Collections.sort(intervals, (Interval o1, Interval o2) -> o1.start - o2.start ); // order the input intervals by start
        
        Interval pre = intervals.get(0);
        result.add(pre);
        for (Interval curr : intervals) {
            if (pre.end < curr.start) {
                pre = curr;
                result.add(pre);
            } else {
                pre.end = Math.max(pre.end, curr.end);
            }
        }

        return result;
    }
    
    public List<Interval> merge_n2(List<Interval> intervals) {
        LinkedList<Interval> result = new LinkedList<>();

        if (null == intervals || intervals.isEmpty()){
            return result;
        }

        Collections.sort(intervals, (Interval o1, Interval o2) -> o1.start - o2.start ); // order the input intervals by start
        Interval pre;
        for (Interval curr : intervals) {
            
            
            if (result.isEmpty() || result.getLast().end < curr.start) {
                result.add(curr);
            } else {
                pre = result.getLast();
                
                pre.end = Math.max(pre.end, curr.end);
            }
        }

        return result;
    }


    public int[][] merge(int[][] intervals) {
        if(null == intervals || intervals.length < 2){
            return intervals;
        }

        Arrays.sort(intervals, (i1, i2) -> (i1[0] == i2[0] ? i1[1] - i2[1] : i1[0] - i2[0]));

        int i = 0;
        for(int j = 1; j < intervals.length; j++){
            // merge intervals[i] and intervals[j]
            if(intervals[i][1] < intervals[j][0]){
                i++;
                intervals[i] = intervals[j];
            }else{
                intervals[i][1] = Math.max(intervals[i][1], intervals[j][1]);
            }
        }

        return Arrays.copyOfRange(intervals, 0, i + 1);
    }

    public static void main(String[] args){
        MergeIntervals sv = new MergeIntervals();
        
        int[][][][] inputs = {
            //{intervals, expect}
            {
                {{1, 3}, {2, 6}, {8, 10}, {15, 18}},
                {{1,6}, {8,10}, {15,18}}
            },
            {
                {{1, 4}, {4, 6}},
                {{1,6}}
            }
        };
        
        for(int[][][] input : inputs){
            Arrays.stream(sv.merge(input[0])).map(interval -> String.format("[%d, %d], ", interval[0], interval[1])).forEach(System.out::print);
            
            Assert.assertEquals(Misc.array2String(input[1], true), Misc.array2String(sv.merge(input[0]), true) );
            
            Assert.assertEquals(Misc.array2String(input[1], true), Misc.array2String(sv.merge(Arrays.asList(Interval.build(input[0]))), true).toString() );
            Assert.assertEquals(Misc.array2String(input[1], true), Misc.array2String(sv.merge_n(Arrays.asList(Interval.build(input[0]))), true).toString() );
            
            Assert.assertEquals(Misc.array2String(input[1], true), Misc.array2String(sv.merge_n2(Arrays.asList(Interval.build(input[0]))), true).toString() );
        }

        
    }
}
