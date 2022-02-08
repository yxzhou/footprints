/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.binaryIndexedTree;

import org.junit.Assert;

/**
 * _https://www.lintcode.com/problem/817
 * 
 *
 * Given a 2D matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) 
 * and lower right corner (row2, col2). And the elements of the matrix could be changed.
 *
 * You have to implement three functions:
 *   NumMatrix(matrix) The constructor.
 *   sumRegion(row1, col1, row2, col2) Return the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
 *   update(row, col, val) Update the element at (row, col) to val.
 * 
 * Notes:
 * The matrix is only modifiable by update.
 * You may assume the number of calls to update and sumRegion function is distributed evenly.
 * You may assume that row1 ≤ row2 and col1 ≤ col2.
 * 
 * Example 1:
 * Input:
 *   NumMatrix(
 *     [[3,0,1,4,2],
 *      [5,6,3,2,1],
 *      [1,2,0,1,5],
 *      [4,1,0,1,7],
 *      [1,0,3,0,5]]
 *   )
 *   sumRegion(2,1,4,3)
 *   update(3,2,2)
 *   sumRegion(2,1,4,3)
 * Output:
 *   8
 *   10
 * 
 * Example 2:
 * Input:
 *   NumMatrix([[1]])
 *   sumRegion(0, 0, 0, 0)
 *   update(0, 0, -1)
 *   sumRegion(0, 0, 0, 0)
 * Output:
 *   1
 *   -1
 * 
 * 
 * Solution: 
 *   Time O(logn * logm) Space O(n * m),  n and m is row and column of the matrix 
 * 
 *  m1) Binary Index Tree (Fenwich tree) on 2D, continue on RangeSum2DImmutable
 *     to every row, from left to right, it's BIT. 
 *     to every column, from up to down, it's BIT
 * 
 *  Define n = matrix.length, m = matrix[0].length,  
 *    update(),  Time O( logn * logm  )
 *    construction, Time O( m * n * logn * logm  )
 *    sumRegion, Time O( logn * logm  )
 * 
 */
public class RangeSum2DMutable {
    
    int[][] datas; // zero based
    int[][] sums;  // binary indexed tree, 1 based
    
    public RangeSum2DMutable(int[][] matrix){
        int n = matrix.length;
        int m = matrix[0].length;
        
        this.datas = new int[n][m];
        this.sums = new int[n + 1][m + 1];
        
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                update(r, c, matrix[r][c]);
            }
        }
    }
    
    public void update(int row, int col, int val){
        
        int delta = val - datas[row][col];
        datas[row][col] = val;
        
        for( int r = row + 1; r < sums.length; r += lowbit(r)){
            for(int c = col + 1; c < sums[0].length; c += lowbit(c)){
                 sums[r][c] += delta;
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2){
        row2++;
        col2++;
        return getSum(row2, col2) - getSum(row1, col2) - getSum(row2, col1) + getSum(row1, col1);
    }
    
    private int getSum(int row, int col){
        int result = 0;
        
        for(int r = row; r > 0; r -= lowbit(r)){
            for(int c = col; c > 0; c -= lowbit(c)){
                result += sums[r][c];
            }
        }
        
        return result;
    }
    
    private int lowbit(int x){
        return x & -x;
    }
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            // matrix
            {   
                {0,3,1,4},
                {1,4,6,9},
                {5,2,0,8}
            },
            //{int row1, int col1, int row2, int col2}, {expect} 
            {   
                {0, 0, 0, 3}, {8}
            },
            {
                {0, 0, 1, 2}, {15}
            },
            {
                {0, 1, 1, 2}, {14}
            },
            // update matrix[0][0] = 10;
            {   
                {0, 0, 0, 3}, {18}
            },
            {
                {0, 0, 1, 2}, {25}
            },
            {
                {0, 1, 1, 2}, {14}
            }
                
        };
        
        RangeSum2DMutable sv = new RangeSum2DMutable(inputs[0]);
        
        int[][] cases;
        for(int i = 1; i < inputs.length; i++){
            
            if(i == 4){
                sv.update(0, 0, 10);
            }
            
            cases = inputs[i];
            Assert.assertEquals( String.format("sumRange(%d, %d, %d, %d)", cases[0][0], cases[0][1], cases[0][2], cases[0][3]), cases[1][0], sv.sumRegion(cases[0][0], cases[0][1], cases[0][2], cases[0][3]) );
        }    

    }
}
