package todo.foo;

/**
 * Q1 _https://www.jiuzhang.com/solution/maximum-average-subarray/
 *    Given an array with positive and negative numbers,
 *    find the maximum average subarray which length should be greater or equal to given length k.
 *
 * Solution _1:  O(n^2)
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
 *
 * Solution _n:  (similar with the-max-sum-of-subarray )  O(n)
 *    Define f(i) is the max average of a subarray in array(0, i)
 *    f_at(i) is the max average of a subarray that is end at num[i] in array(0, i)
 *    sum[i][j] is the sum of array( from i to j)
 *
 *    f(k - 1) = sum[0][k - 1] / k;
 *    f_at(k - 1) = f(k - 1)
 *
 *    f_at(k) = max{
 *        sum[1][k] / k
 *        (f_at(k -1) * k + num[k]) / (k + 1)
 *    }
 *    f(k) = max{
 *        f(k - 1)
 *        f_at(k)
 *    }
 *
 *
 * Q2 _https://www.jiuzhang.com/solution/maximum-average-subarray-ii/
 *    Given an array with positive and negative numbers,
 *    find the maximum average subarray which length should be greater or equal to given length k.
 *
 *
 * Q3 Moving Average from Data Stream
 *
 *
 */

public class MaximumAverageOfSubArray {

    /**
     *
     * Define n as nums.length,  Time Complexity O(n ^ 2)
     *
     */
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

    /**
     *
     * Define n as nums.length,  Time Complexity O(n)
     *
     */
    public double findMaxAverage_n(int[] nums, int k) {
        //ignore check

        int size = nums.length;
        int[] sums = new int[size - k + 1];

        for(int i = 0; i < k; i++){
            sums[k - 1] += nums[i];
        }
        for(int j = k; j < size; j++){
            sums[j] = sums[j - 1] + nums[j] - nums[j - k];
        }

        AverageSubArray maxAverage = new AverageSubArray(k, sums[k - 1] / k);
        AverageSubArray localMaxAverage = new AverageSubArray(k, sums[k - 1] / k);
        for(int j = k; j < size; j++){
            double localAverageOfK = sums[j] / k;
            double localAverageOfMore  = (localMaxAverage.sum + nums[j]) / (localMaxAverage.width + 1);

            if(localAverageOfMore <= localAverageOfK){
                localMaxAverage.width = k;
                localMaxAverage.sum = sums[j];
            }else{
                localMaxAverage.width += 1;
                localMaxAverage.sum += nums[j];
            }
            localMaxAverage.resetAverage();

            if(maxAverage.average < localMaxAverage.average){
                maxAverage.width = localMaxAverage.width;
                maxAverage.sum = localMaxAverage.sum;
                maxAverage.average = localMaxAverage.average;
            }
        }

        return maxAverage.average;
    }

    private class AverageSubArray{
        int width;
        int sum;
        double average;

        AverageSubArray(int width, int sum){
            this.width = width;
            this.sum = sum;
            resetAverage();
        }

        void resetAverage(){
            this.average = sum / width;
        }
    }
}
