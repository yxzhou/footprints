package datastructure.interval;


/**
 * _https://www.lintcode.com/problem/1207
 * Teemo Attacking
 *
 * Description
 * In LOL world, there is a hero called Teemo and his attacking can make his enemy Ashe be in poisoned condition. Now, given the Teemo's attacking ascending time series towards Ashe and the poisoning time duration per Teemo's attacking, you need to output the total time that Ashe is in poisoned condition.
 *
 * You may assume that Teemo attacks at the very beginning of a specific time point, and makes Ashe be in poisoned condition immediately.
 *
 * You may assume the length of given time series array won't exceed 10000.
 * You may assume the numbers in the Teemo's attacking time series and his poisoning time duration per attacking are non-negative integers, which won't exceed 10,000,000.
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
 * Explanation: At time point 1, Teemo starts attacking Ashe and makes Ashe be poisoned.
 * This poisoned status will last 2 seconds until the end of time point 2.
 * However, at the beginning of time point 2, Teemo attacks Ashe again who is already in poisoned status.
 * Since the poisoned status won't add up together, though the second poisoning attack will still work at time point 2, it will stop at the end of time point 3.
 * So you finally need to output 3.
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
 */

public class TeemoAttacking {

    public int findPoisonedDuration(int[] timeSeries, int duration) {
        if(timeSeries == null || timeSeries.length == 0  || duration < 1){
            return 0;
        }

        int res = 0;

        int[] curr = new int[]{timeSeries[0], timeSeries[0] + duration};
        int[] next = new int[2];

        for(int i = 1, n = timeSeries.length; i < n; i++){
            next[0] = timeSeries[i];
            next[1] = timeSeries[i] + duration;

            if(curr[1] < next[0]){
                res += curr[1] - curr[0];
                curr[0] = next[0];
                curr[1] = next[1];
            }else{
                curr[1] = next[1];
            }
        }

        res += curr[1] - curr[0];
        return res;
    }

}
