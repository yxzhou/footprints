package sweepLine.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import util.Misc;

/**
 * _http://www.jiuzhang.com/solution/time-intersection
 * __https://www.lintcode.com/problem/839  
 *
 * Give two users' ordered online time series, and each section records the user's login time point x and offline time
 * point y. Find out the time periods when both users are online at the same time, and output in ascending order.
 *
 * Notes: We guarantee that the length of online time series meet 1 <= len <= 1e6. The lastest outline time y is 1 <= y
 * <= 2e5. We guarantee that the online time series are legal, that is, for a user's online time series, any two
 * intervals of it do not intersect.
 * 
 * Example 1:
 * Input: seqA = [(1,2),(5,100)], seqB = [(1,6)]
 * Output: [(1,2),(5,6)]
 * Explanation: In these two time periods (1,2), (5,6), both users are online at the same time.
 * 
 * Example 2:
 * Input: seqA = [(1,2),(10,15)], seqB = [(3,5),(7,9)]
 * Output: []
 * Explanation: There is no time period, both users are online at the same time.
 * 
 *  Same as Merge2SortedIntervalList.intervalAnd()
 */

public class TimeIntersection {
    
    public List<Interval> timeIntersection(List<Interval> list1, List<Interval> list2) {
        List<Interval> result = new LinkedList<>();

        Interval it1;
        Interval it2;
        for(int i = 0, j = 0, aLen = list1.size(), bLen = list2.size(); i < aLen && j < bLen; ){
            it1 = list1.get(i);
            it2 = list2.get(j);

            if( it1.end < it2.start ){
                i++;
            }else if(it2.end < it1.start){
                j++;
            }else if(it1.end <= it2.end) {  
                i++;
                result.add(new Interval(Math.max(it1.start, it2.start), it1.end));
            }else if(it1.end > it2.end){ 
                j++;
                result.add(new Interval(Math.max(it1.start, it2.start), it2.end));
            }else { // intervalA.end == intervalB.end
                i++;
                j++;
                result.add(new Interval(Math.max(it1.start, it2.start), it2.end));
            }
        }

        return result;
    }
    
    public List<Interval> timeIntersection_n(List<Interval> list1, List<Interval> list2) {
        return new Merge2SortedIntervalList().intervalAnd((Interval[])list1.toArray(), (Interval[])list2.toArray());
    }

    
    public static void main(String[] args){

        
        
    }
}
