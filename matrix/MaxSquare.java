package matrix;

import util.Misc;

import java.util.Stack;
import org.junit.Assert;

/**
 *
 * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing all 1's and return its area.
 *
 * Example 1:
 * Input a matrix:
 *   1 0 1 0 0
 *   1 0 1 1 1
 *   1 1 1 1 1
 *   1 0 0 1 0
 * Output 4.
 * Explanation: [1,2]->[2,3] or [1,3]->[2,4]
 *
 */

public class MaxSquare {


    
    /**
     * Time O(n * m)  Space O(n),  space can be min(m, n)
     * 
     * @param matrix: a matrix of 0 and 1
     * @return: an integer
     */
    public int maxSquare(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return 0;
        }

        int max = 0;

        int n = matrix.length;
        int m = matrix[0].length;

        int[] heights = new int[m];
        Stack<Integer> stack = new Stack<>();
        int top;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){

                if (matrix[r][c] == 1) {
                    heights[c]++;
                } else {
                    heights[c] = 0;
                }

                while(!stack.isEmpty() && heights[c] < heights[stack.peek()] ){
                    top = stack.pop();
                    max = Math.max(max, Math.min(heights[top], stack.isEmpty() ? c : c - stack.peek() - 1 ));
                }
                
                stack.add(c);
            }

            while( !stack.isEmpty() ){
                top = stack.pop();
                max = Math.max(max, Math.min(heights[top], stack.isEmpty() ? m : m - stack.peek() - 1 ));
            }
        }

        return max * max;
    }
    
    public int maxSquare_2(int[][] matrix) {
        if (null == matrix || 0 == matrix.length || 0 == matrix[0].length) {
            return 0;
        }
   
        int n = matrix.length; //row number
        int m = matrix[0].length; //column number

        int[] heights = new int[m + 1]; //note heights[m] is 0
        int max = 0;

        Stack<Integer> stack = new Stack<>();
        int top;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (matrix[r][c] == 1) {
                    heights[c]++;
                } else {
                    heights[c] = 0;
                }
            }
            
            for (int i = 0; i < heights.length; i++) {
                while(!stack.isEmpty() && heights[stack.peek()] > heights[i]){
                    top = stack.pop();
                    max = Math.max(max, Math.min(heights[top], stack.isEmpty()? i : i - stack.peek() - 1));
                }
                
                stack.add(i);
            }
            
            while( !stack.isEmpty() ){ //clear up the stack
                stack.pop();
            }
        }

        return max * max;
    }
   

    /**
     *
     * DP, 
     * define f[i][j] as the edge length of the max square whose bottom right vertex is (i, j)
     * 
     * init,  
     *   To 1 * 1 square, if matrix[i][j] is 1, f[i][j] = 1
     *   To 2 * 2 square, if matrix[i][j] is 1, and f[i - 1][j - 1], f[i - 1][j], f[i][j - 1] all are 1, f[i][j] = 2 
     * 
     * f[i][j] = Math.min(Math.min(f[i - 1][j], f[i - 1][j - 1]),f[i][j - 1]) + 1; 
     * 
     * 
     * Time O(n * m)  Space O(n*m), can be optimized to O(n)
     */
    public int maxSquare_dp(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return 0;            
        }
     
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] f = new int[m][n];//default all are 0
     
        //top row
        for (int i = 0; i < m; i++) {
            f[i][0] = matrix[i][0] == 1? 1 : 0;
        }
     
        //left column
        for (int j = 0; j < n; j++) {
            f[0][j] = matrix[0][j] == 1? 1 : 0;
        }
     
        //cells inside
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 1) {
                    f[i][j] = Math.min(Math.min(f[i - 1][j], f[i - 1][j - 1]),f[i][j - 1]) + 1; //**
                } 
            }
        }
     
        //get maximal length
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                max = Math.max(max, f[i][j]);
            }
        }
     
        return max * max;
    }

    public int maxSquare_dp_n(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return 0;            
        }
     
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] t = new int[m+1][n+1];//default all are 0
     
        //get maximal length
        int max = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == 1) {
                    t[i][j] = Math.min(Math.min(t[i + 1][j], t[i + 1][j + 1]),t[i][j + 1]) + 1; //**
                    max = Math.max(max, t[i][j]);
                } 
            }
        }
     
        return max * max;
    }
    
    public static void main(String[] args) {
        MaxSquare sv = new MaxSquare();

        int[][][] matrix = {
            //{{0}},
            {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1},
                {1, 0, 0}
            },
            {
                {1, 0, 1, 0, 0},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 0, 0, 1, 0}
            },
            {
                {0, 1, 0, 1, 1, 0},
                {1, 0, 1, 0, 1, 1},
                {1, 0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 0},
                {1, 1, 1, 0, 1, 1}
            },
            {
                {1, 1, 1, 1, 1, 0, 1, 1, 0},
                {0, 0, 1, 1, 1, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 0, 0, 1, 1},
                {0, 1, 1, 1, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 0, 1, 1, 0},
                {1, 0, 0, 0, 1, 1, 0, 1, 0}
            }
        };
        
        int[] expects = {9, 4, 9, 4};

        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Misc.array2String(matrix[i]));
            Assert.assertEquals(expects[i], sv.maxSquare(matrix[i]));
            Assert.assertEquals(expects[i], sv.maxSquare_2(matrix[i]));
            Assert.assertEquals(expects[i], sv.maxSquare_dp(matrix[i]));
            Assert.assertEquals(expects[i], sv.maxSquare_dp_n(matrix[i]));
            
        }
    }

}
