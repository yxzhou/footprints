/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

import org.junit.Assert;
import util.Misc;

/**
 *
 * Given a 2D binary matrix filled with 0's and 1's.
 * find the largest submatrix which main diagonal elements are all 1 and the other elements are all 0.
 * 
 * You can assume that the answer matrix must be a square matrix.
 * The main diagonal refers to a diagonal line from the upper left corner to the lower right corner.
 * 
 * Example 1:
 * Input:
 *   [[1,0,1,0,0],
 *    [1,0,0,1,0],
 *    [1,1,0,0,1],
 *    [1,0,0,1,0]]
 * Output: 9
 * Explanation: [0,2]->[2,4]
 * 
 * Example 2:
 * Input:
 *   [[1,0,1,0,1],
 *    [1,0,0,1,1],
 *    [1,1,1,1,1],
 *    [1,0,0,1,0]]
 * Output: 4
 * Explanation: [0,2]->[1,3]
 * 
 * Thought
 *   define f[i][j] as the edge length of the max matrix whose bottom right vertex is (i, j)
 *   To 1*1 square, when matrix[i][j] is 1,  f[i][j] = 1
 *   To 2*2 square, when matrix[i][j] is 1, and matrix[i - 1][j] and matrix[i][j - 1] is 0, f[i][j]= f[i - 1][j - 1] + 1
 * 
 *  need define c[i][j] to store how many zero count from (i, j) to (i-1, j) to (i-2, j) ... until to 1
 *  need define r[j] to store how many zero count from (i, j) to (i, j-1), to (i, j-2) ... until to 1
 * 
 */
public class MaxSquareII {
    
    /**
     * 
     * @param matrix
     * @return 
     */
    public int maxSquare2(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0 ){
            return 0;
        }
        
        int max = 0;
        
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] f = new int[n + 1][m + 1];
        int[] downZero = new int[m];
        int rightZero;
        
        for(int r = n - 1; r >= 0; r-- ){
            rightZero = 0;
            for(int c = m - 1; c >= 0; c--){
                if(matrix[r][c] == 0){
                    rightZero++;
                    downZero[c]++;
                }else{
                    f[r][c] = Math.min(f[r + 1][c + 1] , Math.min(rightZero, downZero[c])) + 1;
                            
                    rightZero = 0;
                    downZero[c] = 0;
                    
                    max = Math.max(max, f[r][c]);
                }
            }
        }
        
        return max * max;
    }
    
    
    public static void main(String[] args) {
        MaxSquareII sv = new MaxSquareII();

        int[][][] matrix = {
            {
                {0}
            },
            {
                {1}
            },
            {
                {1, 0},
                {0, 1}
            },
            {
                {1,0,1,0,0},
                {1,0,0,1,0},
                {1,1,0,0,1},
                {1,0,0,1,0}
            },
            {
                {1,0,1,0,1},
                {1,0,0,1,1},
                {1,1,1,1,1},
                {1,0,0,1,0}
            }
        };
        
        int[] expects = {0, 1, 4, 9, 4};

        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Misc.array2String(matrix[i]));
            Assert.assertEquals(expects[i], sv.maxSquare2(matrix[i]));
            
        }
    }
}
