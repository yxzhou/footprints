package matrix;

import java.util.TreeSet;
import junit.framework.Assert;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/1278
 * 
 * Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix such that its sum
 * is no larger than k.

 * Example 1:
   Input: matrix = [
      [1,  0, 1],
      [0, -2, 3]
    ], k = 2
    Output: 2
* Explanation: Because the sum of rectangle [[0, 1], [-2, 3]] is 2 and 2 is the max number no larger than k (k = 2).
* 
* Example 2: 
* Input: [[2,2,-1]], 3
* Output: 3
* Explanation: sum of rectangle [[2,2,-1]] is 3 and 3 is the max number no larger than k
* 
    
* Note:
    The rectangle inside the matrix must have an area > 0.
    What if the number of rows is much larger than the number of columns?
 *
 * Thoughts:
 *   Define n and m as the matrix row number and column number
 * 
 *   m1) brute force with prefix sum. 
 *      Total it's n*m * n*m / 4 rectangle, 
 *      Define prefixSum[i][j] as the sum of rectangle (0, 0) to (i, j)  
 *      The sum of rectangle (upLeft, upRight) to (bottomLeft, bottomRight),
 *        sum(ul, ur, bl, br) = prefixSum[bl][br] - prefixSum[bl][ur] - prefixSum[ul][br] + prefixSum[ul][ur]
 *      
 *      Time complexity O(n*n*m*m) Space O(n*m)
 * 
 *   m2) 2D -> 1D
 *       To row 0 {a1, a2, ..., }, it's 1D
 *       To row 1 {b1, b2, ..., }, it's 1D
 *       To row 0 and row 1, 
 *         {
 *            {a1, a2, ..., }
 *            {b1, b2, ..., }
 *         }
 *       Think (a1, b1), (a2, b2) as one , it's 1D
 * 
 *       To row i, 
 *         from row i to row i, it's 1D
 *         from row i-1 to row i, it's 1D
 *         ...
 *         from row 0 to row i, it's 1D
 *  
 *    m21) to 1D，{s1, s2, ...}  
 *       prefixSum = 0;
 *       for each s in 1D
 *          prefixSum += s;
 * 
 *       prefixSum[i] - prefixSum[x] <= k,  how to find the max prefixSum[x]?
 *       store previous prefixSum in a TreeSet, get the max( prefixSum[i] - TreeSet.ceiling(prefixSum[i] - k) )
 *
 *    without TreeSet, Time O(n*n*m*m), Space O(n*m)
 *    with TreeSet, Time O( n * n * m * log(m) ), Space O(m), ( n <= m )
 * 
 */

public class MaxSubMatrixNotMoreThanK {
    
    
    /*Time O(n*n*m*m), Space O(n*m)*/
    public int maxSumSubmatrix_m21(int[][] matrix, int k) {
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
    
    public int maxSumSubmatrix_m22(int[][] matrix, int k) {
        if(matrix == null || matrix.length == 0 ){
            return k;
        }

        int max = Integer.MIN_VALUE;

        int n = matrix.length;
        int m = matrix[0].length;

        //int[] sumsOnColumn = new int[m];
        int sum;
        //TreeSet<Integer> treeSet;

        Integer ceiling;
        for(int r = 0; r < n; r++){
            //Arrays.fill(sumsOnColumn, 0);
            int[] sumsOnColumn = new int[m];

            //use TreeSet to help us find the rectangle with maxSum <= k with O(logN) time
            for(int i = r; i >= 0; i--){

                TreeSet<Integer> treeSet = new TreeSet<>();
                treeSet.add(0);

                sum = 0;
                for(int c = 0; c < m; c++){
                    sumsOnColumn[c] += matrix[i][c];

                    sum += sumsOnColumn[c];

                    ceiling = treeSet.ceiling(sum - k);
                    if(ceiling != null){
                        max = Math.max(max, sum -  ceiling);
                    }

                    treeSet.add(sum);
                }
            }
        }

        return max;
    }
    
    public static void main(String[] args){
        int[][][] input = {
            {
                {2, 2, -1},
            },
            {
                {1, 0, 1},
                {0, -2, 3}
            },
            {
                {5, -4, -3, 4},
                {-3, -4, 4, 5},
                {5, 1, 5, -4}
            }
        };
        
        int[] kk = {0, 2, 8};
        int[] results = {-1, 2, 8};
        
        MaxSubMatrixNotMoreThanK sv = new MaxSubMatrixNotMoreThanK();
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("\nInput: %s, k=%d", Misc.array2String(input[i]), kk[i]));
            
            //System.out.println(String.format("Output: %d, %d, %d", sv.maxSumSubmatrix(input[i], kk[i]), sv.maxSumSubmatrix_n(input[i], kk[i]), sv.maxSumSubmatrix_n2(input[i], kk[i])));
            
            Assert.assertEquals(results[i], sv.maxSumSubmatrix_m21(input[i], kk[i]));
            Assert.assertEquals(results[i], sv.maxSumSubmatrix_m22(input[i], kk[i]));
        }
    }
}
