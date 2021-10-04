/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.binaryIndexedTree;

/**
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
 *   #1 binary indexed tree.
 *    
 *   Time O(logn * logm) Space O(n * m),  n and m is row and column of the matrix 
 */
public class RangeSum2D_mutable {
    int rowNum;
    int colNum;
    int[][] datas;// the copy of the original array, start from [1, 1]
    int[][] sums; // the BinaryIndexedTree array, start from [1, 1]
    
    public RangeSum2D_mutable(int[][] matrix){
        if(null == matrix || 0 == matrix.length || 0 == matrix[0].length){
            return;
        }
        rowNum = matrix.length;
        colNum = matrix[0].length;
        
        datas = new int[rowNum][colNum]; 
        sums = new int[rowNum + 1][colNum + 1]; 
        
        for(int r = 0; r < rowNum; r++){
            for(int c = 0; c < colNum; c++){
                update(r, c, matrix[r][c]);
            }
        }
    }
    
    public void update(int r, int c, int value){
        int diff = value - datas[r][c];
        datas[r][c] = value;
        
        for( int i = r + 1; i <= rowNum; i += lowbit(i)){
            for(int j= c + 1; j <= colNum; j += lowbit(j)){
                sums[i][j] += diff; 
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sumRegion(row2, col2) - sumRegion(row2, col1 - 1) - sumRegion(row1 - 1, col2) + sumRegion(row1 - 1, col1 - 1);
                
    }
    
    private int sumRegion(int r, int c){
        int result = 0;
        for( int i = r + 1; i > 0; i -= lowbit(i)){
            for(int j = c + 1; j > 0; j -= lowbit(j)){
                result += sums[i][j];
            }
        }
        return result;
    }
    
    private int lowbit(int x){
        return x & -x;
    }
    
    
    public static void main(String[] args){
//        int[][] matrix = {
//            {3,0,1,4,2},
//            {5,6,3,2,1},
//            {1,2,0,1,5},
//            {4,1,0,1,7},
//            {1,0,3,0,5}
//        };
//        
//        RangeSum2D_mutable sv = new RangeSum2D_mutable(matrix);
//        
//        System.out.println( sv.sumRegion(2, 1, 4, 3) );
//        sv.update(3, 2, 2);
//        System.out.println( sv.sumRegion(2, 1, 4, 3) );
        
        int[][] matrix = {
            {1}
        };
        
        RangeSum2D_mutable sv = new RangeSum2D_mutable(matrix);
        
        System.out.println( sv.sumRegion(0, 0, 0, 0) );
        sv.update(0, 0, -1);
        System.out.println( sv.sumRegion(0, 0, 0, 0) );
        
    }
}
