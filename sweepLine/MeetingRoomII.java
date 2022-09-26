/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sweepLine;

import sweepLine.interval.Interval;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/919
 *
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the
 * minimum number of conference rooms required.)
 *
 * Notes: (0,8),(8,10) is not conflict at 8
 *
 * Example1
 * Input: intervals = [(0,30),(5,10),(15,20)] 
 * Output: 2 
 * Explanation: We need two meeting rooms room1: (0,30) room2: (5,10),(15,20) 
 * 
 * Example2
 * Input: intervals = [(2,7)] 
 * Output: 1 
 * Explanation: Only need one meeting room
 * 
 * 
 * Thoughts:
 *   Finding the minimum number of conference rooms required, It is equals to find out the maximum overlapping in meetings
 *   s1: 
 * 
 * Follow-up
 *   1 how to return the period {start time, end time} which have most meeting
 * 
 */
public class MeetingRoomII {
    /**
     * @param intervals: an array of meeting time intervals
     * @return the minimum number of conference rooms required
     */
    public int minMeetingRooms_TreeMap(List<Interval> intervals) {
        if(intervals == null){
            return 0;
        }

        TreeMap<Integer, Integer> map = new TreeMap<>();//Map: time to count
        for(Interval it : intervals){
            map.put(it.start, map.getOrDefault(it.start, 0) + 1);
            map.put(it.end, map.getOrDefault(it.end, 0) - 1);
        }

        int result = 0;
        int sum = 0;

        for(Integer count : map.values()){
            sum += count;

            result = Math.max(result, sum);
        }

        return result;
    }
    
    /**
     * @param intervals: an array of meeting time intervals
     * @return the minimum number of conference rooms required
     */
    public int minMeetingRooms_Sorting(List<Interval> intervals) {
        if(intervals == null){
            return 0;
        }

        List<int[]> pairs = new ArrayList<>();
        for(Interval it : intervals){
            pairs.add(new int[]{it.start, 1});
            pairs.add(new int[]{it.end, -1});
        }

        Collections.sort(pairs, (a, b) -> a[0] == b[0]? a[1] - b[1] : a[0] - b[0] ); //to events {{3, 1}, {3, -1}}, do {3, -1} before {3, 1}

        int result = 0;
        int sum = 0;
        for(int[] pair : pairs){
            sum += pair[1];

            result = Math.max(result, sum);
        }

        return result;
    }
    
    public Interval minMeetingRooms_followup(List<Interval> intervals) {
        if(intervals == null){
            return new Interval(0, 0);
        }

        TreeMap<Integer, Integer> map = new TreeMap<>();//Map: time to count
        for(Interval it : intervals){
            map.put(it.start, map.getOrDefault(it.start, 0) + 1);
            map.put(it.end, map.getOrDefault(it.end, 0) - 1);
        }

        Interval result = new Interval();
        boolean flag = false;
        int max = 0;
        int sum = 0;

        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            sum += entry.getValue();

            if(sum > max){
                max = sum;
                
                result.start = entry.getKey();
                flag = true;
            }else if(sum < max){

                if(flag){
                    result.end = entry.getKey();
                }
                
                flag = false;
            }
        }

        return result;
    }
    
    public static void main(String[] args){
        
        MeetingRoomII sv = new MeetingRoomII();
        
        int[][][][] inputs = {
            {
                {{0, 30}, {5, 10}, {15, 20,}},
                {{2}}
            }, {
                {{2, 7}},
                {{1}}
            }
        };
        
        for(int[][][] input : inputs){
            Assert.assertEquals(0, input[1][0][0], sv.minMeetingRooms_TreeMap(Arrays.asList(Interval.build(input[0]))));
            
            Assert.assertEquals(0, input[1][0][0], sv.minMeetingRooms_Sorting(Arrays.asList(Interval.build(input[0]))));
            
            
            System.out.println(Interval.toString(sv.minMeetingRooms_followup(Arrays.asList(Interval.build(input[0])))));
        }
        
        
        
    }
}
