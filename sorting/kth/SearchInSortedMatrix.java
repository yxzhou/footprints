package sorting.kth;

import junit.framework.Assert;

/**
 * 
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 * Integers in each row are sorted from left to right. The first integer of each row is greater than the last integer of
 * the previous row. 
 * 
 * For example,
 * Consider the following matrix:
 * [ 
 *   [1, 3, 5, 7], 
 *   [10, 11, 16, 20], 
 *   [23, 30, 34, 50] 
 * ] 
 * Given target = 3, return true.
 * 
 *
 */
public class SearchInSortedMatrix {


    /** 
     *  binary search, O(log(rows) + log(cols))
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (null == matrix) {
            return false;
        }

        int low = 0;
        int high = matrix.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (matrix[mid][0] == target) {
                return true;
            } else if (matrix[mid][0] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        int row = high; // here, matrix[low][0] > target > matrix[high][0]

        if (row < 0) {
            return false;
        }

        low = 0;
        high = matrix[0].length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (matrix[row][mid] == target) {
                return true;
            } else if (matrix[row][mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return false;

    }
	
	
    /**
     *  binary search,  array index is from 0 to rows*clos - 1
     * 
     *  Time O( log(n*m) ) = O( logn + logm )
     */
    public boolean searchMatrix_n(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        int low = 0;
        int high = rows * cols - 1;
        int mid;
        int midValue;
        while (low <= high) {
            mid = (low + high) / 2;
            
            midValue = matrix[mid / cols][mid % cols];
            
            if (midValue == target) {
                return true;
            } else if (midValue < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }
    
	
    public static void main(String[] args) {
        SearchInSortedMatrix sv = new SearchInSortedMatrix();

        int[][][][] inputs = {
            //{ matric, target, expect }   when true, expect == 1; when false, expect == 0
            {null,{{0}}, {{0}}},
            {{{1}}, {{0}}, {{0}} },
            {{{1}}, {{1}}, {{1}} },
            {{{1}}, {{2}}, {{0}} },
            {{{1,3}}, {{0}}, {{0}} },
            {{{1,3}}, {{1}}, {{1}} },
            {{{1,3}}, {{2}}, {{0}} },
            {{{1,3}}, {{3}}, {{1}} },
            {{{1,3}}, {{4}}, {{0}} },
            {{{11,13}, {21, 23}}, {{10}}, {{0}} },
            {{{11,13}, {21, 23}}, {{11}}, {{1}} },
            {{{11,13}, {21, 23}}, {{12}}, {{0}} },
            {{{11,13}, {21, 23}}, {{13}}, {{1}} },
            {{{11,13}, {21, 23}}, {{14}}, {{0}} },
            {{{11,13}, {21, 23}}, {{20}}, {{0}} },
            {{{11,13}, {21, 23}}, {{21}}, {{1}} },
            {{{11,13}, {21, 23}}, {{22}}, {{0}} },
            {{{11,13}, {21, 23}}, {{23}}, {{1}} },
            {{{11,13}, {21, 23}}, {{24}}, {{0}} }
        };

        int[][] matrix;
        int target;
        boolean expect;

        for(int[][][] input : inputs ){
            matrix = input[0];
            target = input[1][0][0];
            expect = (input[2][0][0] == 1);
            
            System.out.println(String.format("expect = %b, %b %b", 
                    expect, 
                    sv.searchMatrix(matrix, target), 
                    sv.searchMatrix_n(matrix, target) ));
            
            if(expect){
                Assert.assertTrue(sv.searchMatrix(matrix, target));
                
                Assert.assertTrue(sv.searchMatrix_n(matrix, target));
            }else{
                Assert.assertFalse(sv.searchMatrix(matrix, target));
                
                Assert.assertFalse(sv.searchMatrix_n(matrix, target));
                
            }
        }

    }

}
