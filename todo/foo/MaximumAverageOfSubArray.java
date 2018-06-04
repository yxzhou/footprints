package fgafa.todo.foo;

/**
 *
 *
 *
 * Solution:
 *  Define:
 *    f(i) is the max average in array( from 0 to i)
 *    sum[i][j] is the sum of array( from i to j)
 *
 *  f(k - 1) = sum[0][k-1] / k;
 *
 *  f(k) = max{
 *      f(k),
 *      (sum[0][k-1] + nums[k]) / (k + 1),
 *      (sum[1][k-1] + nums[k]) / (k)
 *  }
 *
 *  f(k + 1) = max{
 *      f(k + 1),
 *      (sum[0][k] + nums[k+1]) / (k + 2),
 *      (sum[1][k] + nums[k+1]) / (k + 1),
 *      (sum[2][k] + nums[k+1]) / (k)
 *  }
 *
 */

public class MaximumAverageOfSubArray {
    public double findMaxAverage(int[] nums, int k){
        //ignore check

        int size = nums.length;
        int[] sums = new int[size - k + 1];

        for(int i = 0; i < k; i++){
            sums[0] += nums[i];
        }
        double maxAverage = sums[0] / k;

        for(int i = k; i < size; i++){
            int j = 0;
            for( ; j <= i - k; j++){
                sums[j] += nums[i];

                maxAverage = Math.max(maxAverage, sums[j] / (i - j + 1));
            }

            sums[j] = sums[j - 1] - nums[i - k];
            maxAverage = Math.max(maxAverage, sums[j] / k);
        }

        return maxAverage;
    }

}
