package datastructure.interval;


/**
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


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Merge2SortedIntervalList {

    class Interval {
        int start;
        int end;

        Interval(int s, int e) {
            this.start = s;
            this.end = e;
        }
    }

    public List<Interval> intervalAnd(Interval[] list1, Interval[] list2) {
        List<Interval> result = new LinkedList<>();

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
        List<Interval> result = new LinkedList<>();

//        if(list1 == null && list2 == null){
//            return result;
//        }
//        if (list1 == null) {
//            return Arrays.asList(list2);
//        }
//        if (list2 == null) {
//            return Arrays.asList(list1);
//        }
        if(list1 == null || list1.length == 0){
            return list2 == null ? result : Arrays.asList(list2);
        }else if(list2 == null || list2.length == 0){
            return Arrays.asList(list1);
        }

        //list1 and list2 both are not Null and Empty
        Interval it1 = list1[0];
        Interval it2 = list2[0];
        int min = Math.min(it1.start, it2.start);
        Interval tail = new Interval(min, min);
        result.add(tail);

        Interval next; // candidate
        int i = 0, j = 0;
        int n = list1.length, m = list2.length;

        while ( i < n || j < m) {
            it1 = null;
            if (i < n) {
                it1 = list1[i];
            }
            it2 = null;
            if (j < m) {
                it2 = list2[j];
            }

            if (it1 == null) {
                next = it2;
                j++;
            } else if (it2 == null) {
                next = it1;
                i++;
            } else {
                if (it1.start <= it2.start) {
                    next = it1;
                    i++;
                } else {
                    next = it2;
                    j++;
                }
            }

            if (tail.end < next.start - 1) {
                tail = next;
                result.add(tail);
            } else {
                tail.end = Math.max(tail.end, next.end);
            }
        }

        return result;
    }


    /**
     * _https://www.lintcode.com/problem/839
     * Merge two sorted Interval lists
     *
     * Description
     * Merge two sorted (ascending) lists of interval and return it as a new sorted list. The new sorted list should be made by splicing together the intervals of the two lists and sorted in ascending order.
     *
     * The intervals in the given list do not overlap.
     * The intervals in different lists may overlap.
     *
     * Input: list1 = [(1,2),(3,4)] and list2 = [(2,3),(5,6)]
     * Output: [(1,4),(5,6)]
     * Explanation:
     * (1,2),(2,3),(3,4) --> (1,4)
     * (5,6) --> (5,6)
     *
     * Input: list1 = [(1,2),(3,4)] and list2 = [(4,5),(6,7)]
     * Output: [(1,2),(3,5),(6,7)]
     * Explanation:
     * (1,2) --> (1,2)
     * (3,4),(4,5) --> (3,5)
     * (6,7) --> (6,7)
     *
     * @return
     */
    public List<Interval> mergeTwoInterval(List<Interval> list1, List<Interval> list2) {
        List<Interval> res = new LinkedList<>();
        if(list1 == null || list1.isEmpty()){
            return list2 == null? res : list2;
        }else if(list2 == null || list2.isEmpty()){
            return list2;
        }

        //list1 and list2 both are not Null and empty
        Interval i1 = list1.get(0);
        Interval i2 = list2.get(0);
        int min = Math.min(i1.start, i2.start);
        Interval tail = new Interval(min, min);
        res.add(tail);

        Interval next; //candidate
        int i = 0;
        int j = 0;
        int n = list1.size();
        int m = list2.size();

        while(i < n || j < m){
            i1 = null;
            if(i < n){
                i1 = list1.get(i);
            }
            i2 = null;
            if(j < m){
                i2 = list2.get(j);
            }

            if(i1 == null || (i2 != null && i2.start < i1.start)){
                next = i2;
                j++;
            }else {
                next = i1;
                i++;
            }

            if(tail.end < next.start ){
                tail = next;
                res.add(tail);
            }else{
                tail.end = Math.max(tail.end, next.end);
            }
        }

        return res;
    }

}

