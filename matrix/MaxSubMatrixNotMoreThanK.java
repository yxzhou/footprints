package fgafa.matrix;

import java.util.TreeSet;

import fgafa.util.Misc;

/**
 * 
 * Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix such that its sum is no larger than k.

    Example:
    Given matrix = [
      [1,  0, 1],
      [0, -2, 3]
    ]
    k = 2
    The answer is 2. Because the sum of rectangle [[0, 1], [-2, 3]] is 2 and 2 is the max number no larger than k (k = 2).
    
    Note:
    The rectangle inside the matrix must have an area > 0.
    What if the number of rows is much larger than the number of columns?
 *
 */

public class MaxSubMatrixNotMoreThanK {
    public int maxSumSubmatrix(int[][] matrix, int k) {
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            throw new IllegalArgumentException();
        }
        
        int rows = matrix.length;
        int columns = matrix[0].length;
        int minDiff = Integer.MAX_VALUE;
        int diff;
        int[][] subSumMatrix = new int[rows][columns]; //default all are 0

        for(int row = 0; row < rows; row++){
            for(int i = 0; i <= row; i++){
                for(int col = 0; col < columns; col++){
                    subSumMatrix[i][col] += matrix[row][col];
                }
                
                diff = maxSumSubArray(subSumMatrix[i], k);
                
                if(0 == diff){
                    return k;
                }else if(diff < minDiff){
                    minDiff = diff;
                }
            }
        }
        
        return k - minDiff;
    }
    
    private int maxSumSubArray(int[] arr, int k){
        int m = arr.length;
        int minDiff = Integer.MAX_VALUE;
        int[] subSums = new int[m]; //default all are 0
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j <= i; j++){
                subSums[j] += arr[i];
                
                //no larger than k
                if( subSums[j] == k){
                    return 0;
                }else if( subSums[j] < k){
                    minDiff = Math.min(minDiff, k - subSums[j]);
                }
            }
        }
        
        return minDiff;
    }

    /*Time O(n*n*m*m), Space O(n*m)*/
    public int maxSumSubmatrix_n(int[][] matrix, int k) {
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            throw new IllegalArgumentException();
        }
        
        int rows = matrix.length;
        int columns = matrix[0].length;
        
        long minDiff = Integer.MAX_VALUE;
        long[][] subSumMatrix = new long[rows][columns]; //default all are 0

        for(int row = 0; row < rows; row++){
            for(int i = 0; i <= row; i++){
                long[] subSums = new long[columns]; //default all are 0
                
                for(int col = 0; col < columns; col++){
                    subSumMatrix[i][col] += matrix[row][col];
                    
                    for(int j = 0; j <= col; j++){
                        subSums[j] += subSumMatrix[i][col];
                        
                        if(subSums[j] == k){
                            return k;
                        }else if(subSums[j] < k){
                            minDiff = Math.min(minDiff, k - subSums[j]);
                        }
                    }
                }

            }
        }
        
        return (int)(k - minDiff);
    }
    
    /*let's assume the number of rows is much smaller than the number of columns,
     *
     *2D Kadane's algorithm + 1D maxSum problem with sum limit k 
     *2D subarray sum solution
     *
     * Time O( rows * rows * ( cols * log(cols)) ), Space O(cols)*/
    public int maxSumSubmatrix_n2(int[][] matrix, int k) {
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            throw new IllegalArgumentException();
        }
        
        int rows = matrix.length;
        int columns = matrix[0].length;
        int result = Integer.MIN_VALUE;

        //outer loop should use smaller axis
        //now we assume we have more cols than rows, therefore outer loop will be based on rows 
        for(int row = 0; row < rows; row++){
            int[] sums = new int[columns]; 
            for(int i = row; i < rows; i++){
                for(int col = columns - 1; col >= 0; col--){
                    sums[col] += matrix[i][col];
                }
                
                //use TreeSet to help us find the rectangle with maxSum <= k with O(logN) time
                TreeSet<Integer> set = new TreeSet<Integer>();
                //add 0 to cover the single row case
                set.add(0);
                int currSum = 0;
                
                for(int sum : sums){
                    currSum += sum;
                    //we use sum subtraction (curSum - sum) to get the subarray with sum <= k
                    //therefore we need to look for the smallest sum >= currSum - k
                    Integer num = set.ceiling(currSum - k);
                    if(num != null){
                        result = Math.max( result, currSum - num );
                    }
                    set.add(currSum);
                }

            }
        }
        
        return result;
    }
    
    public static void main(String[] args){
        int[][][] input = {
                    {
                        {2,2,-1}
                    }, 
                    {
                        {1,  0, 1},
                        {0, -2, 3}
                    }
        };
        
        int[] kk = {0, 2};
        
        MaxSubMatrixNotMoreThanK sv = new MaxSubMatrixNotMoreThanK();
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("\nInput: %s, k=%d", Misc.array2String(input[i]), kk[i]));
            System.out.println(String.format("Output: %d, %d, %d", sv.maxSumSubmatrix(input[i], kk[i]), sv.maxSumSubmatrix_n(input[i], kk[i]), sv.maxSumSubmatrix_n2(input[i], kk[i])));
        }
    }
}
