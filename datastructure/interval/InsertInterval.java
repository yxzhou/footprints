package datastructure.interval;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 * You may assume that the intervals were initially sorted according to their start times.
 * Example 1:
 * Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].
 * <p>
 * Example 2:
 * Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].
 * This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
 */
public class InsertInterval {

    public class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    public List<Interval> insert_n(List<Interval> intervals, Interval newInterval) {
        List<Interval> res = new LinkedList<>();
//        if(newInterval == null && intervals == null){
//            return res;
//        }

//        if (newInterval == null) {
//            return intervals;
//        }

        if (intervals == null) {
            res.add(newInterval);
            return res;
        }

        for(Interval interval : intervals){
            if(interval.end < newInterval.start){
                res.add(interval);
            }else if(newInterval.end < interval.start){
                res.add(newInterval);
                newInterval = interval;
            }else{ //overlap
                newInterval.start = Math.min(newInterval.start, interval.start);
                newInterval.end = Math.max(newInterval.end, interval.end);
            }
        }

        if(newInterval != null){
            res.add(newInterval);
        }

        return res;
    }

    public List<Interval> insert_n2(List<Interval> intervals, Interval newInterval) {
        List<Interval> res = new LinkedList<>();

        if (intervals == null) {
            res.add(newInterval);
            return res;
        }

        Interval interval;
        for(int i = 0, n = intervals.size(); i < n; i++){
            interval = intervals.get(i);

            if(interval.end < newInterval.start){
                res.add(interval);
            }else if(newInterval.end < interval.start){
                res.add(newInterval);
                newInterval = null;

                while(i < n){
                    res.add(intervals.get(i++));
                }

                break;
            }else{ //overlap
                newInterval.start = Math.min(newInterval.start, interval.start);
                newInterval.end = Math.max(newInterval.end, interval.end);
            }
        }

        if(newInterval != null){
            res.add(newInterval);
        }

        return res;
    }


    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new LinkedList<>();
        int index = 0;
        result.add(index, newInterval);

        //check
        if (intervals == null || intervals.size() == 0)
            return result;

        Interval tmp;
        for (int i = 0; i < intervals.size(); i++) {
            tmp = intervals.get(i);

            if (tmp.end < newInterval.start) {
                result.add(index, tmp);
                index++;
            } else if (tmp.start > newInterval.end) {
                result.add(tmp);
            } else {
                merge(tmp, newInterval);
            }

        }

        return result;
    }

    /**
     * merge it1 into it2
     *
     * @param it1
     * @param it2
     */
    private void merge(Interval it1, Interval it2) {
        if ((it1.start >= it2.start && it1.start <= it2.end) || (it2.start >= it1.start && it2.start <= it1.end)) {
            it2.start = Math.min(it1.start, it2.start);
            it2.end = Math.max(it1.end, it2.end);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {


    }

}
