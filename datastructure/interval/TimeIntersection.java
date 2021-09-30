package datastructure.interval;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * _http://www.jiuzhang.com/solution/time-intersection
 *
 * Give two users' ordered online time series, and each section records the user's login time point x and offline time point y.
 * Find out the time periods when both users are online at the same time, and output in ascending order.
 *  
 * Notes:
 * We guarantee that the length of online time series meet 1 <= len <= 1e6.
 * The lastest outline time y is 1 <= y <= 2e5.
 * We guarantee that the online time series are legal, that is, for a user's online time series, any two intervals of it do not intersect.
 * 
 * Example 1:
 * Input: seqA = [(1,2),(5,100)], seqB = [(1,6)]
 * Output: [(1,2),(5,6)]
 * Explanation: In these two time periods (1,2), (5,6), both users are online at the same time.
 * 
 * Example 2:
 * 
 * Input: seqA = [(1,2),(10,15)], seqB = [(3,5),(7,9)]
 * Output: []
 * Explanation: There is no time period, both users are online at the same time.
 * 
 *  S: interval merge
 */

public class TimeIntersection {
    
    public List<Interval> timeIntersection(List<Interval> seqA, List<Interval> seqB) {
        List<Interval> result = new LinkedList<>();

        Interval intervalA;
        Interval intervalB;
        for(int i = 0, j = 0, aLen = seqA.size(), bLen = seqB.size(); i < aLen && j < bLen; ){
            intervalA = seqA.get(i);
            intervalB = seqB.get(j);

            if( intervalA.end <= intervalB.start ){
                i++;
            }else if(intervalB.end <= intervalA.start){
                j++;
            }else if(intervalA.end < intervalB.end) {  
                i++;
                result.add(new Interval(Math.max(intervalA.start, intervalB.start), intervalA.end));
            }else if(intervalA.end > intervalB.end){ 
                j++;
                result.add(new Interval(Math.max(intervalA.start, intervalB.start), intervalB.end));
            }else { // intervalA.end == intervalB.end
                i++;
                j++;
                result.add(new Interval(Math.max(intervalA.start, intervalB.start), intervalB.end));
            }
        }

        return result;
    }


    class Interval{
        int start;
        int end;

        Interval(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
}
