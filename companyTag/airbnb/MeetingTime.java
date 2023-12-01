/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Assert;
import sweepLine.interval.Interval;

/**
 * Input is a number of meetings (start_time, end_time)。 
 * Output is a number of time intervals (start_time, end_time), where there is no meetings.
 *
 * For example, 
 * input is [[1, 3], [6, 7]], [[2, 4)],  [[2, 3], [9, 12]] ]
 * output [[4, 6], [7, 9]]
 *
 * Thoughts:
 *   similar with sweepLine.MyCalendar
 * 
 */
public class MeetingTime {
    
    /**
     * sweepLine
     * 
     * 
     * @param intervals
     * @return the empty meeting time, at most of k
     */
    public List<Interval> getAvailableIntervals(List<List<Interval>> intervals, int k) {
        if(intervals == null){
            return Collections.EMPTY_LIST;
        }
        
        TreeMap<Integer, Integer> map = new TreeMap<>();//map<the start or end of interval, 1 when start, -1 when end>
        for(List<Interval> person : intervals ){
            for(Interval meeting : person){
                map.put(meeting.start, map.getOrDefault(meeting.start, 0) + 1);
                map.put(meeting.end, map.getOrDefault(meeting.end, 0) - 1);
            }
        }
        
        List<Interval> result = new ArrayList<>();
        int start = 0;
        boolean busy = true;
        int count = 0; // count of meeting
        for(Map.Entry<Integer, Integer> event : map.entrySet()){
            count += event.getValue();
            
            if(count == 0 && busy){
                busy = false;
                start = event.getKey();
            }
            
            if(count > 0){                
                if(!busy){
                    result.add(new Interval(start, event.getKey()));
                    
                    if(result.size() >= k){
                        break;
                    }
                }
                
                busy = true;
            }
        }
        
        return result;
    }
    
    public static void main(String[] args){
        
        int[][][][][] inputs = {
            //{ Intervals, expect }
            {
                {
                    {{1, 3}, {6, 7}}, 
                    {{2, 4}}, 
                    {{2, 3}, {9, 12}}
                },
                {{{4, 6}, {7, 9}}}
            }
        };
        
        final int K = 10;
        
        MeetingTime sv = new MeetingTime();
        
        for(int[][][][] input : inputs){
            
            Assert.assertArrayEquals(Interval.build(input[1][0]), sv.getAvailableIntervals(Interval.build(input[0]), K).toArray());
            
        }
    }
    
    
}
