package matrix;

import util.Misc;

/** 
 * _https://www.lintcode.com/problem/381
 * 
 * Given an integer n, generate a square matrix filled with elements from 1 to n^2 in spiral order.
 *
 * (The spiral rotates clockwise from the outside to the inside, referring to examples)
 * 
 * Example 1:
 * input: 2
 * output:
 * [
 *   [1, 2],
 *   [4, 3]
 * ]
 * Example 2:
 * input: 3
 * output:
 * [
 *   [ 1, 2, 3 ],
 *   [ 8, 9, 4 ],
 *   [ 7, 6, 5 ]
 * ]
 * 
 *
 * testcases:
 * 
 * #1  n < 1
 * new int[0][0]
 * 
 * #2  n = 1
 * {{1}}
 * 
 * #3  n = 2
 * {{1, 2},
 *  {4, 3}
 * }
 * 
 * #4  n = 3
 * {{1, 2, 3},
 *  {8, 9, 4},
 *  {7, 6, 5},
 * }
 * 
 */
public class SpiralMatrixII {

    public int[][] generateMatrix(int n) {
        if (n < 1) {
            return new int[0][];
        }

        int[][] matrix = new int[n][n];

        int low = 0; // top-left
        int high = n - 1; // bottom-right;
        int x = 1;

        int r;
        int c;
        for (; low < high; low++, high--) {
            //the first row from left to right, both sides inclusive
            for (c = low; c <= high; c++) {
                matrix[low][c] = x++;
            }

            //the last column from up to down, both sides exclusive
            for (r = low + 1; r < high; r++) {
                matrix[r][high] = x++;
            }

            //the first row from right to left, both sides inclusive
            for (c = high; c >= low; c--) {
                matrix[high][c] = x++;
            }

            //the last column from down to up, both sides exclusive
            for (r = high - 1; r > low; r--) {
                matrix[r][low] = x++;
            }
        }

        if (low == high) {
            matrix[low][low] = x++;
        }

        return matrix;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("------------Start-------------- ");

        SpiralMatrixII creator = new SpiralMatrixII();
        
        SpiralMatrix printer = new SpiralMatrix();

        for (int i = 0; i < 8; i++) {
            int[][] matrix = creator.generateMatrix(i);

            System.out.println(String.format("\n--%d-\n%s", i, Misc.array2String(printer.spiralOrder(matrix)).toString()));
        }

        System.out.println("\n------------End-------------- \n");
    }

}
