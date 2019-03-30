package fgafa.datastructure.interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
}
