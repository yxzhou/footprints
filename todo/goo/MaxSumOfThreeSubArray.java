package fgafa.todo.goo;

public class MaxSumOfThreeSubArray {

    //Todo
    public int[] getMaxSumOfSubArray(int[] nums, int k, int m){
        int[] result = new int[3];

        if(null == nums || k < 1 || nums.length < 3 * k){
            return result;
        }

        int[] leftMaxSum = new int[nums.length];
        for(int i = 0, j = k - 1; i < k; i++){
            leftMaxSum[j] += nums[i];
        }
        for(int i = k; i < nums.length; i++){
            leftMaxSum[i] = leftMaxSum[i - 1] + nums[i] - nums[i - k];
        }

        int[] rightMaxSum = new int[nums.length];

        long maxSum = Long.MIN_VALUE;
        long[][] maxSumOfSUbArray = new long[nums.length + 1][k];
        long start = 0;

        for(int j = 0, i = j - 1; j< k; j++, i+=j){
            start += leftMaxSum[i];
            maxSumOfSUbArray[i][j] = start;

            for( i++; i < nums.length; i++){
                maxSumOfSUbArray[i][j] = Math.max(maxSumOfSUbArray[i - 1][j], maxSumOfSUbArray[i - k][j - 1] + leftMaxSum[i] );
            }
        }


        return result;
    }


    public int[] getMaxSumOfThreeSubArray(int[] nums, int k) {
        int[] result = new int[3];

        if (null == nums || k < 1 || nums.length < 3 * k) {
            return new int[]{-1, -1, -1};
        }

        long[] sumOfLeftK = new long[nums.length];//define sumOfLeftK[i] as the sum from nums[i - k + 1] to nums[i]
        int[] leftMaxSumPosition = new int[nums.length];
        for(int i = 0; i < k; i++){

        }



        int rightMaxSumPositon;



        return result;
    }

}
