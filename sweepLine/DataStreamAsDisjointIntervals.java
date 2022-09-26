package sweepLine;

import sweepLine.interval.Interval;
import java.util.*;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1280
 *
 * Given a data stream input of non-negative integers a1, a2, ..., an, ..., summarize the numbers seen so far as a list
 * of disjoint intervals.
 *
 * Example 1:
 *   Input：                Output： 
 *   addNum(1) 
 *   getIntervals()         [[(1,1)],
 *   addNum(3) 
 *   getIntervals()         [(1,1),(3,3)],
 *   addNum(7) 
 *   getIntervals()         [(1,1),(3,3),(7,7)],
 *   addNum(2) 
 *   getIntervals()         [(1,3),(7,7)],
 *   addNum(6)
 *   getIntervals()         [(1,3),(6,7)]]
 * Explanation： 
 *   addNum(1) getIntervals([[1, 1]]) 
 *   addNum(3) getIntervals([[1, 1], [3, 3]]) 
 *   addNum(7) getIntervals([[1, 1], [3, 3], [7, 7]]) 
 *   addNum(2)-merge(1,2,3) getIntervals([[1, 3], [7, 7]]) 
 *   addNum(6)->merge(6,7) getIntervals([[1, 3], [6, 7]])
 *
 * 
 * Follow up: 
 *   What if there are lots of merges and the number of disjoint intervals are small compared to the data stream's size?
 * 
 * Thoughts:
 *   m1) TreeMap,     Map<value, Interval> 
 *   m2) UnionFind
 *   m3) interval tree if it knows the min and max
 *
 */

public class DataStreamAsDisjointIntervals {

     Map<Integer, Integer> groupIdMap;
     Map<Integer, Interval> intervalMap;

    /** Initialize your data structure here. */
    public DataStreamAsDisjointIntervals() {
        groupIdMap = new HashMap<>();
        intervalMap = new HashMap<>();
    }

    public void addNum(int val) {
        if(groupIdMap.containsKey(val)){
            return;
        }

        /** union find,  find the left side, and enlarge the interval scope */
        int left = val - 1;
        while(groupIdMap.containsKey(left) && left != groupIdMap.get(left)){
            //path compression
            groupIdMap.put(left, groupIdMap.get(groupIdMap.get(left)));

            left = groupIdMap.get(left);
        }

        if(groupIdMap.containsKey(left)){
            groupIdMap.put(val, left);

            intervalMap.get(left).end = val;
        }

        /**  */
        int right = val + 1;
        if(groupIdMap.containsKey(right) ){

            if(intervalMap.containsKey(left)){
                intervalMap.get(left).end = intervalMap.get(right).end;

                groupIdMap.put(right, left);
            }else{
                groupIdMap.put(val, val);
                groupIdMap.put(right, val);
                intervalMap.get(right).start = val;
                intervalMap.put(val, intervalMap.get(right));
            }

            intervalMap.remove(right);
        }

        /** when left and right both not found, create a new interval */
        if(!groupIdMap.containsKey(val)){
            groupIdMap.put(val, val);

            intervalMap.put(val, new Interval(val, val));
        }

    }

    public List<Interval> getIntervals() {

        List<Integer> groupIds = new ArrayList<>(intervalMap.keySet());
        Collections.sort(groupIds);

        List<Interval> result = new ArrayList<>(groupIds.size());

        for(Integer i : groupIds){
            result.add(intervalMap.get(i));
        }

        return result;
    }

    /**
     * Your SummaryRanges object will be instantiated and called as such:
     * SummaryRanges obj = new SummaryRanges();
     * obj.addNum(val);
     * List<Interval> param_2 = obj.getIntervals();
     */


    public static void main(String[] args){

        int[][][][] testcases = {
            //{ the val for addNum, the expect of getIntervals }
            {
                {{1}},
                {{1, 1}},
                {{3}},
                {{1, 1}, {3, 3}},
                {{7}},
                {{1, 1}, {3, 3}, {7, 7}},
                {{2}},
                {{1, 3}, {7, 7}},
                {{6}},
                {{1, 3}, {6, 7}}
            },
            {
                {{2}},
                {{2, 2}},
                {{3}},
                {{2, 3}},
                {{6}},
                {{2, 3}, {6, 6}},
                {{5}},
                {{2, 3}, {5, 6}},
                {{4}},
                {{2, 6}}
            }
        };
       

        for(int[][][] testcase : testcases){
            DataStreamAsDisjointIntervals sv1 = new DataStreamAsDisjointIntervals();
            
            for(int i = 0; i < testcase.length; i += 2){
                sv1.addNum(testcase[i][0][0]);
            
                List<Interval> list = sv1.getIntervals();

                //list.forEach(i -> System.out.print(String.format("[%d, %d]", i.start, i.end)));
                Assert.assertEquals(String.format("addNum(%d) ", testcase[i][0][0]), Misc.array2String(testcase[i + 1], true), Misc.array2String(sv1.getIntervals()).toString());
            }
            
            DataStreamAsDisjointIntervals2 sv2 = new DataStreamAsDisjointIntervals2();
            for(int i = 0; i < testcase.length; i += 2){
                sv2.addNum(testcase[i][0][0]);
            
                List<Interval> list = sv1.getIntervals();

                //list.forEach(i -> System.out.print(String.format("[%d, %d]", i.start, i.end)));
                Assert.assertEquals(String.format("addNum(%d) ", testcase[i][0][0]), Misc.array2String(testcase[i + 1], true), Misc.array2String(sv2.getIntervals()).toString());
            }
        }

    }
}
