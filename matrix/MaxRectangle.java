/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

import java.util.Stack;
import org.junit.Assert;
import util.Misc;

/**
 *
 * Given a 2D boolean matrix filled with False and True, find the largest rectangle containing all True and return its area.
 * 
 * Example 1
 * Input:
 * [
 *   [1, 1, 0, 0, 1],
 *   [0, 1, 0, 0, 1],
 *   [0, 0, 1, 1, 1],
 *   [0, 0, 1, 1, 1],
 *   [0, 0, 0, 0, 1]
 * ]
 * Output: 6
 * 
 * Example 2
 * Input:
 * [
 *     [0,0],
 *     [0,0]
 * ]
 * Output: 0
 * 
 * 
 * 
 * 
 */
public class MaxRectangle {
    /**
     * @param matrix: a boolean 2D matrix
     * @return: an integer
     */
    public int maximalRectangle(boolean[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int n = matrix.length;
        int m = matrix[0].length;

        int result = 0;

        int[] heights = new int[m];
        Stack<Integer> stack = new Stack<>();
        int top;
        int area;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (matrix[r][c]) {
                    heights[c]++;
                } else {
                    heights[c] = 0;
                }
                
                while (!stack.isEmpty() && heights[c] < heights[stack.peek()]) {
                    top = stack.pop();
                    area = heights[top] * (stack.isEmpty() ? c : c - stack.peek() - 1);
                    result = Math.max(result, area);
                }

                stack.add(c);
            }

            while (!stack.isEmpty()) {
                top = stack.pop();
                area = heights[top] * (stack.isEmpty() ? m : m - stack.peek() - 1);

                result = Math.max(result, area);
            }
        }

        return result;
    }
    
    
    public static void main(String[] args) {
        MaxRectangle sv = new MaxRectangle();

        int[][][] matrix = {
            {
                {0}
            },
            {
                {1}
            },
            {
                {0, 0},
                {0, 0}
            },
            {
                {1, 1},
                {1, 1}
            },
            {
                {1, 1, 0, 0, 1},
                {0, 1, 0, 0, 1},
                {0, 0, 1, 1, 1},
                {0, 0, 1, 1, 1},
                {0, 0, 0, 0, 1}
            },
            {
                {1,0,1,0,1},
                {1,0,0,1,1},
                {1,1,1,1,1},
                {1,0,0,1,0}
            }
        };
        
        int[] expects = {0, 1, 0, 4, 6, 5};

        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Misc.array2String(matrix[i]));
            Assert.assertEquals(expects[i], sv.maximalRectangle(sv.convert(matrix[i])));
            
        }
    }
    
    private boolean[][] convert(int[][] matrix){
        int n = matrix.length;
        int m = matrix[0].length;
        boolean[][] result = new boolean[n][m];
        
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                result[r][c] = (matrix[r][c] == 1);
            }
        }
        
        return result;
    }
}
