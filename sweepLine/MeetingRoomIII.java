package sweepLine;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * _https://www.lintcode.com/problem/1897/
 *
 * Q3,  determine if a meeting can be placed in the current meeting list
 *
 * you have a list intervals of current meetings, and some meeting rooms with start and end timestamp. When a stream of
 * new meeting ask coming in, judge one by one whether they can be placed in the current meeting list without
 * overlapping. A meeting room can only hold one meeting at a time. Each inquiry is independent.
 *
 * Notes:
 *   Ensure that Intervals can be arranged in rooms meeting rooms
 *   Ensure that the end time of any meeting is not greater than 50000
 *   |Intervals|<=50000
 *   |ask|<=50000
 *   rooms<=20
 *
 * Example 1:
 *   Input:  Intervals:[[1,2],[4,5],[8,10]], rooms = 1, 
 *   ask: [[2,3],[3,4],[4,5],[5,6]]
 *   Output: [true, true, false, true]
 *   Explanation:
 *   For the ask of [2,3], we can arrange a meeting room room0.
 *   The following is the meeting list of room0: [[1,2], [2,3], [4,5], [8,10]]
 *   For the ask of [3,4], we can arrange a meeting room room0.
 *   The following is the meeting list of room0: [[1,2], [3,4], [4,5], [8,10]]
 *
 * Example 2:
 *   Input:Intervals:[[1,2],[4,5],[8,10]], rooms = 1
 *   ask: [[4,5],[5,6]]
 *   Output: [false,true]
 * 
 * Thoughts:
 *   The scenario is to put all meetings with (start, end) on a timeline image, keep asking for a new meeting, to see if
 *   it exceeds the room limitation. 
 *   Firstly it need know the number of occupied rooms in ask(start time, end time)
 * 
 *   Define n as the number of intervals, m as the number of ask.
 *   
 *   S1: TreeMap
 *     Store the meeting timeline as a TreeMap<start time or end time, the number of occupied rooms after the time>  
 * 
 *     To a new ask(start, end), check every entry in subMap(floorKey(start), end)
 * 
 *     Time O(m * n) space O(n)
 * 
 *   s2: TreeMap + intervalTree
 *     in S1, it need O(n) to check every entry in subMap(floorKey(start), end) for a new ask(start, end). 
 *     Optimize, Define int[] binaryIndexedTree, 
 *        tree[1] stores the max room need in [0, 50000], 
 *        tree[2] stores the max room need in [0, 25000]
 *        tree[3] stores the max room need in (25000, 50000]
 *      ----
 * 
 *   Time O(n + m*logn) space O(n)
 * 
 *   S3: prefixSum
 *     Define int[] need, int[] exceeds 
 *     need[i] store the number that room needs at time i.
 *     exceeds[end] - exceeds[start] store the times of it exceeds limitation in (start, end) 
 * 
 *     Time O( n + m ) space O(n)
 * 
 */

public class MeetingRoomIII {
    
    /**
     * S1, Time O(m * n) space O(n)
     * 
     * @param intervals: the intervals
     * @param rooms: the sum of rooms
     * @param ask: the ask
     * @return: true or false of each meeting
     */
    public boolean[] meetingRoomIII_TreeMap(int[][] intervals, int rooms, int[][] ask) {
        
        if(intervals == null || ask == null ){
            return new boolean[0];
        }

        TreeMap<Integer, Integer> timeline = new TreeMap<>(); 
        
        //timeline stores <time, room change on the time>
        for(int[] pair : intervals){
            timeline.put(pair[0], timeline.getOrDefault(pair[0], 0) + 1);
            timeline.put(pair[1], timeline.getOrDefault(pair[1], 0) - 1);
        }

        //timeline stores <time, room need after the time >    
        int need = 0;
        for(Map.Entry<Integer, Integer> entry : timeline.entrySet()){
            need += entry.getValue();

            timeline.put(entry.getKey(), need);
        }

        boolean[] result = new boolean[ask.length];

        Integer pre;
        int[] pair;
        for(int i = 0; i < ask.length; i++){
            pair = ask[i];

            pre = timeline.floorKey(pair[0]);
            pre = (pre == null? pair[0] : pre);

            result[i] = true;
            for(int x : timeline.subMap(pre, pair[1]).values() ){
                if(x >= rooms){
                    result[i] = false;
                    break;
                }
            }
        }

        return result;
    }
    
    /**
     * S3 Time O( n + m ) space O(n)
     */

    public boolean[] meetingRoomIII_prefixSum(int[][] intervals, int rooms, int[][] ask) {
        final int N = 50001; //the start and end time of meeting is [1, 50000]
        int[] timeline = new int[N];

        //timeline[i] stores the room change at time i. +1 when need a new room, -1 when free a room.
        for (int[] interval : intervals) {
            timeline[interval[0]]++;
            timeline[interval[1]]--;
        }
        //timeline[i] stores the number of rooms need at time i.
        for (int i = 1; i < N; i++) {
            timeline[i] += timeline[i - 1];
        }

        int[] times = new int[N]; // times[end] - time[start] stores the times of rooms full hold.
        for (int i = 1; i < N; i++) {
            if (timeline[i] == rooms) {
                times[i] = times[i - 1] + 1;
            } else {
                times[i] = times[i - 1];
            }
        }

        int m = ask.length;
        boolean[] results = new boolean[m];
        for (int i = 0; i < m; i++) {
            results[i] = (timeline[ask[i][0]] < rooms && times[ask[i][0]] == times[ask[i][1] - 1]);
        }

        return results;
    }
    


}
