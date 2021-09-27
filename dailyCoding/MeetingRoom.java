package dailyCoding;

import java.util.*;

/**
 *
 * Given an array of time intervals (start, end) for classroom lectures (possibly overlapping), find the minimum number of rooms required.
 For example, given [(30, 75), (0, 50), (60, 150)], you should return 2.
 *
 * Tags: Snapchat, interval
 *
 */

public class MeetingRoom {

    class Interval{
        int start;
        int end;
    }

    public int minRooms(List<Interval> lectures){

        if(null == lectures || 0 == lectures.size()){
            return 0;
        }

        List<Integer> events = new ArrayList<>(lectures.size() * 2);

        for(Interval lecture : lectures){
            events.add(lecture.start);
            events.add(-lecture.end);
        }

        Collections.sort(events, new Comparator<Integer>(){
            @Override public int compare(Integer o1, Integer o2) {
                int diff = Math.abs(o1) - Math.abs(o2);
                return diff == 0 ? o1 - o2: diff;
            }
        });

        int result = 0;
        int count = 0;
        for(int event : events){
            if(event > 0){
                count ++;

                result = Math.max(result, count);
            }else{
                count --;
            }
        }
        return result;
    }

}
