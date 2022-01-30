package datastructure.interval;

import java.util.*;

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
 * addNum(1) getIntervals([[1, 1]]) 
 * addNum(3) getIntervals([[1, 1], [3, 3]]) 
 * addNum(7) getIntervals([[1, 1], [3, 3], [7, 7]]) 
 * addNum(2)-merge(1,2,3) getIntervals([[1, 3], [7, 7]]) 
 * addNum(6)->merge(6,7) getIntervals([[1, 3], [6, 7]])
 *
 * 
 * Follow up: 
 *   What if there are lots of merges and the number of disjoint intervals are small compared to the data stream's size?\
 * 
 * Thoughts:
 *   m1) Set<Integer> store every Value 
 *   m2) interval tree 
 *   m3) TreeMap,     Map<value, Interval> 
 *   m4) UnionFind
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
        DataStreamAsDisjointIntervals sv = new DataStreamAsDisjointIntervals();

        //0, add; 1, get
        int[][] input = {{0, 2}, {0, 3}, {0, 6}, {0, 5}, {0, 4}, {1, 1}};

        for(int[] action : input){
            if(action[0] == 0){
                sv.addNum(action[1]);
            }else if(action[0] == 1){
                List<Interval> list = sv.getIntervals();

                list.forEach(i -> System.out.print(String.format("[%d, %d]", i.start, i.end)));
                System.out.println();
            }
        }

    }
}
