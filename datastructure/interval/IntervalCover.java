package datastructure.interval;

import java.util.*;

import util.Misc;
import org.junit.Test;

/**
 *
 * Given a set of closed intervals (ranges of integers), find the smallest set of integers that covers all the intervals. If there are multiple smallest sets, return any of them.
 *
 * For example,
 *   given the intervals [0, 3], [2, 6], [3, 4], [6, 9],  return {3, 6} or {3, 7} etc
 *
 *   given [0, 4], [1, 2], [5, 7], [6, 7], [6, 9], [8, 10], return { 1, 6, 8 }, { 2, 7, 9 }, { 1, 7, 8 } etc
 *
 *
 * refer: https://stackoverflow.com/questions/15005008/given-some-integer-ranges-finding-a-smallest-set-containing-at-least-one-intege
 *
 */

public class IntervalCover {
    class Interval{
        int start;
        int end;

        Interval(int start, int end){
            this.start = start;
            this.end = end;
        }
    }

    public List<Integer> intervalCover(Interval[] intervals){

        if(null == intervals || 0 == intervals.length){
            return new ArrayList<>();
        }

        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override public int compare(Interval o1, Interval o2) {
                return o1.end == o2.end ? o1.start - o2.start : o1.end - o2.end;
            }
        });

        Stack<Integer> result = new Stack<>();
        result.push(intervals[0].end);
        for(Interval interval : intervals){
            if(result.peek() < interval.start ){
                result.push(interval.end);
            }
        }

        return result;
    }

    @Test public void test(){

        Misc.printList(intervalCover(buildIntervals(new int[][]{{0,3},{2,6},{3,4},{6,9}})));

        Misc.printList(intervalCover(buildIntervals(new int[][]{{0,4},{1,2},{5,7},{6,7},{6,9},{8,10}})));
    }

    private Interval[] buildIntervals(int[][] ranges){

        Interval[] result = new Interval[ranges.length];

        int i = 0;
        for(int[] range : ranges){
            result[i++] = new Interval(range[0], range[1]);
        }

        return result;
    }

}
