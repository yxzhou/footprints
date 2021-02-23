package fgafa.datastructure.interval;

import javafx.util.Pair;

import java.util.*;

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
        if(null == intervals || 0 == intervals.length){
            return 0;
        }

        Arrays.sort(intervals, (a, b) -> a.start - b.start);

        PriorityQueue<Interval> minHeap = new PriorityQueue<>((a, b) -> a.end - b.end);
        minHeap.add(intervals[0]);

        Interval curr;
        Interval top;
        for( int i = 1; i < intervals.length ; i++ ){
            curr = intervals[i];
            top = minHeap.poll();

            if(top.end <= curr.start){
                top.end = curr.end;
            }else{
                minHeap.add(curr);
            }

            minHeap.add(top);
        }

        return minHeap.size();
    }


    
    
    public int minMeetingRooms_n(Interval[] intervals) {
        if(null == intervals || 0 == intervals.length){
            return 0;
        }
    
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        for(Interval interval : intervals){
            list.add(new Pair(interval.start, 1));
            list.add(new Pair(interval.end, -1));
        }
        
        Collections.sort(list, (p1, p2) -> (p1.getKey() == p2.getKey() ? p1.getValue() - p2.getValue() : p1.getKey() - p2.getKey()) );

        int result = 0;
        int count = 0;
        for(Pair p : list){
            count += (int)p.getValue();
            
            result = Math.max(result, count);
        }
        
        return result;
    }

}
