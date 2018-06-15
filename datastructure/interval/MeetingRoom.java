package fgafa.datastructure.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * Given an array of meeting time intervals consisting of start and end times [s1,e1],[s2,e2],..., 
 * 
 * Q1, determine if a person could attend all meetings. 
 * For example, Given [[0, 30],[5, 10],[15, 20]],  return false.
 *
 * Q2,  find the minimum number of conference rooms required. 
 * For example, Given [[0, 30],[5, 10],[15, 20]], return 2.
 *
 */

public class MeetingRoom {

    public boolean canAttendMeetings(Interval[] intervals) {
        //check
        if(null == intervals || 0 == intervals.length){
            return true;
        }
        
        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval a, Interval b) {
                int diff = a.start - b.start;
                
                return diff != 0 ? diff : a.end - b.end; 
            }
        });
        
        for(int i = 1; i < intervals.length; i++){
            if( intervals[i].start > intervals[i - 1].end ){
                return false;
            }
        }
        
        return true;
    }

    public int minMeetingRooms(Interval[] intervals) {
        //check
        if(null == intervals || 0 == intervals.length){
            return 0;
        }
        
        int result = 0;
        
        //get 2 arrays, the startTimes and endTimes
        int size = intervals.length;
        int[] starts = new int[size];
        int[] ends = new int[size];
        int i = 0;
        for(Interval it : intervals){
            starts[i] = it.start;
            ends[i++] = it.end;
        }
        
        //sort
        Arrays.sort(starts);
        Arrays.sort(ends);
        
        //main
        i = 0;
        int j = 0;
        while( i < size && j < size ){
            if(starts[i] < ends[i]){
                result++;
                i++;
            }else if(starts[i] > ends[i]){ // 
                result--;
                j++;
            }else{ // ==
                i++;
                j++;
            }
        }

        return result;
    }

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
    
    
    public int minMeetingRooms_n(Interval[] intervals) {
        //check
        if(null == intervals || 0 == intervals.length){
            return 0;
        }
    
        List<Pair> list = new ArrayList<>();
        for(Interval interval : intervals){
            list.add(new Pair(interval.start, 1));
            list.add(new Pair(interval.end, -1));
        }
        
        Collections.sort(list);
        
        int result = 0;
        int count = 0;
        for(Pair p : list){
            count += p.value;
            
            result = Math.max(result, count);
        }
        
        return result;
    }
    
    private class Pair implements Comparable<Pair> {
        int x; //x position
        int value;
        
        Pair(int x, int value){
            this.x = x;
            this.value = value;
        }
        
        @Override
        public int compareTo(Pair o) {
            int diff = this.x - o.x;
            return diff == 0 ? this.value - o.value : diff;
        } 
        
    }
}
