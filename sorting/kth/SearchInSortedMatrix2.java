package sorting.kth;

import algs4.stdlib.In;
 
import util.Misc;
import util.TimeCost;

/**
 * _https://www.lintcode.com/problem/38/
 * 
 * Write an efficient algorithm that searches for a value in an m x n matrix, return The number of occurrence of it.
 *
 * This matrix has the following properties:
 *   Integers in each row are sorted from left to right. 
 *   Integers in each column are sorted from up to bottom. 
 *   No duplicate integers in each row or column
 *
 * Thoughts:
 * Because the rows is sorted, 
 * m1) check row by row with binary search, 
 * Time O(n * logn)
 *
 * Because the rows and column are sorted, the up-left is the minimum, the bottom-right is the maximum, 
 * m2) start from up-right (or bottom-left), if k is bigger, exclude the row, or exclude the column. 
 * Time O(n + n) 
 * 
 * m3) binary search on the whole matrix, check the elements on diagonal, Every time it can exclude half Element 
 * Time O(logn)
 *
 * 
 *   
 */

public class SearchInSortedMatrix2 {

  
    /**
     * Step-wise Linear Search
     *
     * Steps: Start search from the top-right. 
     * 1)if it == target, return successfully 
     * 2)If it < target,  move to left.
     * 3)else ( it > target ), it move to down.
     *
     * define n as row number and m as column number.
     * Time O(n + m)
     *
     */
    public boolean isExisted_stepwise(int[][] matrix, int target) {
        if (null == matrix || 0 == matrix.length ) {
            return false;
        }

        int n = matrix.length;
        int m = matrix[0].length;

        // the start point is top-right. 
        int diff;
        for ( int r = 0, c = m - 1; r < n && c >= 0; ) {
            
            diff = matrix[r][c] - target;
            if (diff == 0) {
                return true;
            } else if (diff > 0) {
                c--;
            } else {
                r++;
            }
        }

        return false;
    }
  
    /**
     * @param matrix: A list of lists of integers
     * @param: A number you want to search in the matrix
     * @return An integer indicate the occurrence of target in the given matrix
     */
    public int count(int[][] matrix, int target) {
        if (null == matrix || 0 == matrix.length ) {
            return 0;
        }

        int n = matrix.length; //how many rows
        int m = matrix[0].length; //how many columns

        int count = 0;
        
        int diff;
        for (int r = 0, c = m - 1; r < n && c >= 0; ) {// the start point is top-right. 
            diff = matrix[r][c] - target;
                    
            if (diff == 0) {
                count++;

                r++;
                c--;
            } else if (diff < 0) {
                r++;
            } else {
                c--;
            }
        }

        return count;
    }

    /**
     * Step-wise Linear Search + binary search in the line (row and col)
     *
     * Steps: 
     * Start search from the top-right. 
     * 1)if it == target, return successfully 
     * 2)If it < target,  it move to left, binary search on the row
     * 3)else ( it > target ), it move to down, binary search on the column
     *
     * O(log(rows) + log(cols)) ?? 
     * worst case O( rows*log(rows) + cols*log(cols) ) ??
     *
     */
    public boolean isExisted_Binarysearch(int[][] arr, int elem) {
        int n = arr.length;
        int m = arr[0].length;

        int row = 0;
        int col = m - 1;   // the start point is top-right. 

        int middle, right, left, top, bottom;
        while (row < n && col >= 0) {
            if (elem == arr[row][col]) {
                return true;
            } else if (elem < arr[row][col]) {
                //col --;
                left = 0;
                right = col;

                while (right > left + 1) {
                    middle = (left + right) / 2;

                    if (elem == arr[row][middle]) {
                        return true;
                    } else if (elem > arr[row][middle]) {
                        left = middle;
                    } else {
                        right = middle;
                    }
                }

                col = left;
                if (elem < arr[row][col]) {
                    return false;
                }

            } else {
                //row ++;
                bottom = n - 1;
                top = row;

                while (bottom > top + 1) {
                    middle = (top + bottom) / 2;

                    if (elem == arr[middle][col]) {
                        return true;
                    } else if (elem > arr[middle][col]) {
                        top = middle;
                    } else {
                        bottom = middle;
                    }
                }

                row = bottom;
                if (elem > arr[row][col]) {
                    return false;
                }
            }
        }

        return false;
    }
  
    public static void main(String[] args) {
        SearchInSortedMatrix2 m = new SearchInSortedMatrix2();
        TimeCost tc = TimeCost.getInstance();

        /*-------------simple test start --------------- */
        int[][] aInt = {
            {1, 4, 7, 11, 15},
            {2, 5, 8, 12, 19},
            {3, 6, 9, 16, 22},
            {10, 13, 14, 17, 24},
            {18, 21, 23, 26, 30},
            {22, 25, 27, 28, 31},
            {29, 31, 32, 34, 35}};

        int[] elems = {9, 10, 23, 33, 40};

        System.out.println(Misc.array2String(aInt));

        tc.init();

        for (int i = 0; i < elems.length; i++) {

            System.out.println("\nIs " + elems[i] + " in? "
                    + m.isExisted_stepwise(aInt, elems[i]) + " timeCost:" + tc.getTimeCost());

            System.out.println("Is " + elems[i] + " in? "
                    + m.isExisted_Binarysearch(aInt, elems[i]) + " timeCost:" + tc.getTimeCost());

        }

        //-------------simple test end ---------------
        //*-------------performance test start-------------
//    int n = 1000; // n*n matrix
//
//    int[][] aInt = new int[n][n];
//    int tmp = 1;
//    aInt[0][0] = 1;
//    for (int i = 0; i < n; i++) {
//      aInt[i][0] = ((i / 2) * 2000) + 1;
//
//      for (int j = 1; j < n; j++) {
//        aInt[i][j] = aInt[i][j - 1] + 2;
//      }
//
//    }
//
//    int[] elems = {503886, 517885};
        String filename = "./src/matrix_search_performance1000_input.txt";

        In in = new In(filename);

        int rowsNum = in.readInt();
        int colsNum = in.readInt();
        System.out.println("--rows:" + rowsNum + " --cols:" + colsNum);

        int[][] aInt_big = new int[rowsNum][colsNum];

        for (int i = 0; i < rowsNum; i++) {
            for (int j = 0; j < colsNum; j++) {
                aInt_big[i][j] = in.readInt();
            }
        }

        int caseNum = in.readInt();
        int[] elems_big = new int[caseNum];
        for (int i = 0; i < caseNum; i++) {
            elems_big[i] = in.readInt();
        }

        //compare result
//    for (int i = 0; i < elems_big.length; i++) {
//        System.out.printf("\n %b, %b, %b \n", m.stepWise(aInt_big, elems_big[i]), m.stepWise_Binary(aInt_big, elems_big[i]), m.quadPartition(aInt_big, elems_big[i]) );        
//    }
        //compare timecost
        tc.init();
        for (int i = 0; i < elems_big.length; i++) {
            m.isExisted_stepwise(aInt_big, elems_big[i]);
        }
        System.out.println(" stepWise timeCost:" + tc.getTimeCost());

        for (int i = 0; i < elems_big.length; i++) {
            m.isExisted_Binarysearch(aInt_big, elems_big[i]);
        }
        System.out.println(" stepWise_Binary timeCost:" + tc.getTimeCost());

        //-------------performance test end-------------*/
    }
    
}
