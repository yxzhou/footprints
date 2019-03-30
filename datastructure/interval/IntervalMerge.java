package fgafa.datastructure.interval;


/*
Given 2 sets of intervals.

Interval is defined with left and right border and discrete points, like [2, 3], [0, 0], etc.

Set of intervals is non intersected set of sorted intervals, for example: [0, 0], [2, 2], [5, 10] is a valid set of intervals, but [0, 0], [1, 2] is not valid, because you can write it as [0, 2]. [0, 2], [1, 5] is not valid as well, since these two intervals intersect.

You need to find the AND or OR operation of these two sets. For example:

1st set: [0, 2], [5, 10], [16, 20]
2nd set: [1, 5], [10, 18], [20, 23]

AND Result: [1, 2], [5, 5], [10, 10], [16, 18], [20, 20]
OR Result: [0, 23]

1: [0, 1]
2: [4, 5]

1 [0, 0]
2 [1, 1]
*/


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntervalMerge {

    class Interval {
        int start;
        int end;

        Interval(int s, int e) {
            this.start = s;
            this.end = e;
        }
    }

    public List<Interval> intervalAnd(Interval[] list1, Interval[] list2) {
        List<Interval> result = new ArrayList<>();

        if (list1 == null || list2 == null) {
            return result;
        }

        for (int i = 0, j = 0; i < list1.length && j < list2.length; ) {
            Interval interval1 = list1[i];
            Interval interval2 = list2[j];

            if ((interval1.end >= interval2.start && interval1.end <= interval2.end) || (interval2.end >= interval1.start && interval2.end <= interval1.end)) {
                result.add(new Interval(Math.max(interval1.start, interval2.start), Math.min(interval1.end, interval2.end)));
            }

            if (interval1.end < interval2.end) {
                i++;
            } else if (interval1.end > interval2.end) {
                j++;
            } else { // interval1.end == interval2.end
                i++;
                j++;
            }
        }

        return result;
    }

    public List<Interval> intervalOr(Interval[] list1, Interval[] list2) {
        List<Interval> result = new ArrayList<>();

        if(list1 == null && list2 == null){
            return result;
        }
        if (list1 == null) {
            return Arrays.asList(list2);
        }
        if (list2 == null) {
            return Arrays.asList(list1);
        }

        int i = 0, j = 0;

        Interval interval1 = list1[0];
        Interval interval2 = list2[0];
        Interval tail;
        Interval candidate;

        if (interval1.start <= interval2.start) {
            tail = interval1;
            i++;
        } else {
            tail = interval2;
            j++;
        }

        result.add(tail);

        for ( ; i < list1.length || j < list2.length; ) {
            interval1 = null;
            if (i < list1.length) {
                interval1 = list1[i];
            }

            interval2 = null;
            if (j < list2.length) {
                interval2 = list2[j];
            }

            if (interval1 == null) {
                candidate = interval2;
            } else if (interval2 == null) {
                candidate = interval1;
            } else {
                if (interval1.start <= interval2.start) {
                    candidate = interval1;
                    i++;
                } else {
                    candidate = interval2;
                    j++;
                }
            }

            if (tail.end < candidate.start - 1) {
                tail = candidate;
                result.add(tail);

            } else {
                tail.end = Math.max(tail.end, candidate.end);
            }
        }

        return result;
    }

}

