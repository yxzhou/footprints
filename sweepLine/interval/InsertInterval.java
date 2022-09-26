package sweepLine.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * _https://www.lintcode.com/problem/30/
 * 
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 * You may assume that the intervals were initially sorted according to their start times.
 * 
 * Example 1:
 * Input: interval list = [(1,2), (5,9)]  new interval = (2, 5)
 * Output: [(1,9)]
 * Explanation: The interval after insertion overlaps and needs to be merged.
 * 
 * Example 2:
 * Input: interval list = [(1,2), (5,9)]  new interval = (3, 4)
 * Output: [(1,2), (3,4), (5,9)]
 * Explanation: Intervals are ordered by starting endpoints.
 * 
 * Example 3:
 * Input: interval list = [1,2],[3,5],[6,7],[8,10],[12,16], new interval = [4,9]  
 * Output: [1,2],[3,10],[12,16].
 * Explanation: This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
 * 
 */
public class InsertInterval {

    
    /**
     * @param intervals: Sorted interval list.
     * @param newInterval: new interval.
     * @return A new interval list.
     */
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if(intervals == null){
            return newInterval == null ? Collections.EMPTY_LIST : Arrays.asList(newInterval);
        }else if( newInterval == null){
            return intervals;
        }

        List<Interval> result = new ArrayList<>(intervals.size());

        Interval curr;
        for(int i = 0, n = intervals.size(); i < n ; i++){
            curr = intervals.get(i);

            if( newInterval == null || curr.end < newInterval.start ){
                result.add(curr);
            }else if( curr.start > newInterval.end ){
                result.add(newInterval);
                newInterval = null;
                result.add(curr);
            }else { // overlap
                newInterval.start = Math.min(curr.start, newInterval.start);
                newInterval.end = Math.max(curr.end, newInterval.end);
            }

        }

        if(newInterval != null){
            result.add(newInterval);
        }

        return result;
    }
    
    /**
     * @param intervals: Sorted interval list.
     * @param newInterval: new interval.
     * @return: A new interval list.
     */
    public List<Interval> insert_2(List<Interval> intervals, Interval newInterval) {
        if(intervals == null){
            return newInterval == null ? Collections.EMPTY_LIST : Arrays.asList(newInterval);
        }else if( newInterval == null){
            return intervals;
        }

        List<Interval> result = new ArrayList<>(intervals.size());

        Interval curr;
        for(int i = 0, n = intervals.size(); i < n; i++){
            curr = intervals.get(i);

            if(curr.end < newInterval.start){
                result.add(curr);
            }else if(newInterval.end < curr.start){
                result.add(newInterval);
                newInterval = null;
                
                while(i < n){
                    result.add(intervals.get(i++));
                }

                break;
            }else{ //overlap
                newInterval.start = Math.min(newInterval.start, curr.start);
                newInterval.end = Math.max(newInterval.end, curr.end);
            }
        }

        if(newInterval != null){
            result.add(newInterval);
        }

        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {


    }

}
