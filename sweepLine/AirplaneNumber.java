package sweepLine;

import sweepLine.interval.Interval;
import java.util.*;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/391
 * 
 * Given an list interval, which are taking off and landing time of the flight. How many airplanes are there at most at
 * the same time in the sky?
 *
 * If landing and taking off of different planes happen at the same time, we consider landing should happen at first.
 *
 *
 * Example 1:
 * Input: [(1, 10), (2, 3), (5, 8), (4, 7)] 
 * Output: 3 
 * Explanation: 
 * The first airplane takes off at 1 and lands at 10.
 * The second ariplane takes off at 2 and lands at 3. 
 * The third ariplane takes off at 5 and lands at 8. 
 * The forth ariplane takes off at 4 and lands at 7. 
 * During 5 to 6, there are three airplanes in the sky. 
 * 
 * Example 2:
 * Input: [(1, 2), (2, 3), (3, 4)] 
 * Output: 1 
 * Explanation: Landing happen before taking off.
 */

public class AirplaneNumber {

    /**
     * @param airplanes: An interval array
     * @return Count of airplanes are in the sky.
     */
    public int countOfAirplanes(List<Interval> airplanes) {
        if(airplanes == null){
            return 0;
        }

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();// map: time to change value
        for(Interval plane : airplanes){
            treeMap.put(plane.start, treeMap.getOrDefault(plane.start, 0) + 1);
            treeMap.put(plane.end, treeMap.getOrDefault(plane.end, 0) - 1);
        }

        int max = 0;
        int sum = 0;
        for(Integer change : treeMap.values()){
            sum += change;

            max = Math.max(max, sum);
        }

        return max;
    }
    
    public int countOfAirplanes_2(List<Interval> airplanes) {
       if (null == airplanes) {
            return 0;
        }

        //int[] changes = new int[25]; //the time is in hours, default all are 0
        int[] changes = new int[24 * 60 + 1]; //the time is minutes, default all are 0
        for (Interval it : airplanes) {
            changes[it.start]++;
            changes[it.end]--;
        }

        int max = 0;
        int sum = 0;
        for (int num : changes) {
            sum += num;
            max = Math.max(max, sum);
        }

        return max;
    }
    

    public static void main(String[] args) {
        AirplaneNumber sv = new AirplaneNumber();
        
        int[][][][] inputs = {
            {
                {{1, 10}, {2, 3}, {5, 8}, {4, 7}},
                {{3}}
            },
            {
                {{1, 2}, {2, 3}, {3, 4}},
                {{1}}
            }
        };
        
        for(int[][][] input : inputs){
            Assert.assertEquals(input[1][0][0], sv.countOfAirplanes(Arrays.asList(Interval.build(input[0]))));
        }
        
    }

}
