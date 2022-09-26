package sweepLine.interval;

import java.util.Map;
import java.util.TreeMap;
import junit.framework.Assert;


/**
 * _https://www.lintcode.com/problem/1207
 *
 * In LOL world, there is a hero called Teemo and his attacking can make his enemy Ashe be in poisoned condition. Now,
 * given the Teemo's attacking ascending time series towards Ashe and the poisoning time duration per Teemo's attacking,
 * you need to output the total time that Ashe is in poisoned condition.
 *
 * You may assume that Teemo attacks at the very beginning of a specific time point, and makes Ashe be in poisoned
 * condition immediately.
 *
 * You may assume the length of given time series array won't exceed 10000. 
 * You may assume the numbers in the Teemo's attacking time series and his poisoning time duration per attacking are 
 * non-negative integers, which won't exceed 10,000,000.
 *
 * Example 1:
 * Input: [1,4], 2
 * Output: 4
 * Explanation: At time point 1, Teemo starts attacking Ashe and makes Ashe be poisoned immediately.
 * This poisoned status will last 2 seconds until the end of time point 2.
 * And at time point 4, Teemo attacks Ashe again, and causes Ashe to be in poisoned status for another 2 seconds.
 * So you finally need to output 4.
 *
 *
 * Example 2:
 * Input: [1,2], 2
 * Output: 3
 * Explanation: At time point 1, Teemo starts attacking Ashe and makes Ashe be
 * poisoned. This poisoned status will last 2 seconds until the end of time point 2. However, at the beginning of time
 * point 2, Teemo attacks Ashe again who is already in poisoned status. Since the poisoned status won't add up together,
 * though the second poisoning attack will still work at time point 2, it will stop at the end of time point 3. So you
 * finally need to output 3.
 *
 *
 * Solution:
 *   simulate the processing.
 *
 *    timeSeries[i]     duration in poisoned condition        duration time
 *   input [1,2, 3]  3
 *          1,          [1, 4)
 *          2,          [2, 5)    merge to [1, 5)
 *          3,          [3, 6).   merge to [1, 6)
 *                                                            6 - 1 = 5
 *   input [1, 4] 2
 *          1,          [1, 3)
 *          4,          [4, 6)    no overlap                  3 - 1 = 2
 *                                                            6 - 4 = 2,  total 4
 * 
 * Thoughts:
 *   Define n as the length of time seri. 
 * 
 *   s1, each poisoned status is a interval {start, end}, merge all intervals. 
 *   Time complexity O(2n + n * lgn)  space O(n)
 * 
 *   s2, two point, greedy, similar with JumpGame
 *   Time complexity O(n)  space O(1)
 * 
 *   
 */

public class TeemoAttacking {

    /**
     * 
     * 
     * @param timeSeries: the Teemo's attacking ascending time series towards Ashe
     * @param duration: the poisoning time duration per Teemo's attacking
     * @return the total time that Ashe is in poisoned condition
     */
    public int findPoisonedDuration_TwoPoints_n(int[] timeSeries, int duration) {
        if(timeSeries == null || duration < 1){
            return 0;
        }

        int sum = 0;
        int start = 0;
        int end = 0;
        for(int i = 0, n = timeSeries.length; i < n; i++){

            if( timeSeries[i] > end){
                sum += end - start;

                start = timeSeries[i];
            }
                
            end = timeSeries[i] + duration;
        }

        sum += end - start;
        return sum;
    }
    
    public int findPoisonedDuration_SinglePoint(int[] timeSeries, int duration) {
        if(timeSeries == null || duration < 1){
            return 0;
        }
        
        int sum = 0;
        int end = 0;
        for (int i = 0; i < timeSeries.length; ++i) {
            if (timeSeries[i] >= end) {
                sum += duration;
            } else {
                sum += timeSeries[i] + duration - end;
            }
            end = timeSeries[i] + duration;
        }
        return sum;
    }
    
    public int findPoisonedDuration_Interval(int[] timeSeries, int duration) {
        if(timeSeries == null || duration < 1){
            return 0;
        }
        
        TreeMap<Integer, Integer> changes = new TreeMap<>();//map: time to count
        int end;
        for(int start : timeSeries ){
            changes.put(start, changes.getOrDefault(start, 0) + 1);
            
            end = start + duration;
            changes.put(end, changes.getOrDefault(end, 0) - 1);
        }

        int sum = 0;
        int py = 0;
        int count = 0;
        for(Map.Entry<Integer, Integer> entry : changes.entrySet()){
            if(count > 0){
                sum += entry.getKey() - py;
            }

            py = entry.getKey();
            count += entry.getValue();
        }

        return sum;
    }

    
    public static void main(String[] args){
        TeemoAttacking sv = new TeemoAttacking();
        
        int[][][] inputs = {
            {
                {1, 4},
                {2},
                {4}
            },
            {
                {1, 2},
                {2},
                {3}
            },
        };
        
        for(int[][] input : inputs){
            Assert.assertEquals(input[2][0], sv.findPoisonedDuration_TwoPoints_n(input[0], input[1][0]));
            
            Assert.assertEquals(input[2][0], sv.findPoisonedDuration_SinglePoint(input[0], input[1][0]));
            
            
            Assert.assertEquals(input[2][0], sv.findPoisonedDuration_Interval(input[0], input[1][0]));
        }
    }
}
