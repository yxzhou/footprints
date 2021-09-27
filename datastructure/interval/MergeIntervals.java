package datastructure.interval;

import org.junit.Test;

import java.util.*;

/**
 * Given a collection of intervals, merge all overlapping intervals.
 * 
 * For example,
 * Given [1,3],[2,6],[8,10],[15,18],
 * return [1,6],[8,10],[15,18].
 * 
 * The related issue see InsertInterval.java
 */

public class MergeIntervals {
    public class Interval {
        int start;
        int end;
    }

    public List<Interval> merge_n(List<Interval> intervals) {
        List<Interval> result = new ArrayList<>();

        // check
        if (null == intervals || 0 == intervals.size()){
            return result;
        }

        // order the input intervals by start
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1,
                               Interval o2) {
                return o1.start - o2.start; // ascend order
            }
        });

        // insert one by one
        Interval top = intervals.get(0);
        result.add(top);
        for (Interval next : intervals) {
            if (top.end < next.start) {
                top = next;
                result.add(top);
            } else {
                top.end = Math.max(top.end, next.end);
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

    @Test
    public void test(){
        Arrays.stream(merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})).map(interval -> String.format("[%d, %d], ", interval[0], interval[1])).forEach(System.out::print);
        System.out.println("\n[[1,6],[8,10],[15,18]]\n");

        Arrays.stream(merge(new int[][]{{1, 4}, {4, 6}})).map(interval -> String.format("[%d, %d], ", interval[0], interval[1])).forEach(System.out::print);
        System.out.println("\n[[1,6]]\n");
    }
}
