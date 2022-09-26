package sweepLine;

import sweepLine.interval.Interval;

import java.util.*;

/**
 * _https://www.lintcode.com/problem/920/
 * 
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), 
 * determine if a person could attend all meetings.
 * 
 * Notes: (0,8),(8,10) is not conflict at 8
 * 
 * Example1
 * Input: intervals = [(0,30),(5,10),(15,20)]
 * Output: false
 * Explanation:  (0,30), (5,10) and (0,30),(15,20) will conflict
 * 
 * Example2
 * Input: intervals = [(5,8),(9,15)]
 * Output: true
 * Explanation:  Two times will not conflict 
 * 
 * Thoughts:
 *   If there is overlap in the meetings, the person can not attend all meeting. 
 * 
 *   s1: brute force, 
 *   check if there is overlap in every 2 meetings, totally it's O(n * n) 
 *
 *   s2: put all meetings with (start, end) on a timeline image. To find out overlap, only need compare the meetings which are neighbor.
 *   It need be ordered by the start time. 
 *   It's O(n * logn)
 * 
 * 
 */

public class MeetingRoom {

    
    public boolean canAttendMeetings(Interval[] intervals) {
        if(null == intervals){
            return true;
        }
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a.start, b.start));
        
        for(int i = 1; i < intervals.length; i++){
            if( intervals[i].start < intervals[i - 1].end ){
                return false;
            }
        }
        
        return true;
    }
    
    public boolean canAttendMeetings(List<Interval> intervals) {
        if(intervals == null || intervals.isEmpty()){
            return true;
        }

        Collections.sort(intervals, (a, b) -> Integer.compare(a.start, b.start) );

        int end = 0;
        for(Interval it : intervals){

            if(it.start < end){
                return false;
            }

            end = Math.max(end, it.end);
        }

        return true;
    }
    
    public boolean canAttendMeetings_throwException(Interval[] intervals) {
        if(null == intervals){
            return true;
        }
        
        try {
            Arrays.sort(intervals, (Interval a, Interval b) -> {
                if(a.end <= b.start){
                    return -1; // place a ahead of b
                }else if(b.end <= a.start){
                    return 1; // place b ahead of a
                }else{
                    throw new RuntimeException();
                }
            });
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }


}
