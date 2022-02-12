package game.waterTrap;

import java.util.*;

/**
 * _https://www.lintcode.com/problem/364
 * 
 * Given n x m non-negative integers representing an elevation map 2d where the area of each cell is 1 x 1,
 * compute how much water it is able to trap after raining.
 *
 *Example Given 5*4 matrix
 * [
 *  [12,13,0,12]
 *  [13,4,13,12]
 *  [13,8,10,12]
 *  [12,13,12,12]
 *  [13,13,13,13]
 * ]
 *
 * return 14.
 *
 */
public class WaterTrap2D {

    /**
     * flooding
     * 
     * Time O(m * n * log(m + n) ), space O(m * n)
     * 
     * @param heights: a matrix of integers
     * @return water it is able to trap after raining
     */
    public int trapRainWater(int[][] heights) {

        if (heights == null || heights.length < 3 || heights[0].length < 3) {
            return 0;
        }

        int n = heights.length;
        int m = heights[0].length;

        boolean[][] visited = new boolean[n][m];

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(heights[a[0]][a[1]], heights[b[0]][b[1]]));
        
        //add all the grids on the border into minHeap
        int lastRow = n - 1;
        int lastCol = m - 1;
        for (int c = 0; c < m; c++) {
            minHeap.add(new int[]{0, c});  //first row
            minHeap.add(new int[]{lastRow, c}); //last row

            visited[0][c] = true;
            visited[lastRow][c] = true;
        }

        for (int r = 1; r < lastRow; r++) {
            minHeap.add(new int[]{r, 0});  //first column
            minHeap.add(new int[]{r, lastCol}); //last column

            visited[r][0] = true;
            visited[r][lastCol] = true;
        }

        int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // down, up, right, left,
        int sum = 0;

        int delta;
        int[] grid;
        int r;
        int c;
        while (!minHeap.isEmpty()) {
            grid = minHeap.poll();

            for (int[] diff : diffs) {
                r = grid[0] + diff[0];
                c = grid[1] + diff[1];

                if (r >= 0 && r < n && c >= 0 && c < m && !visited[r][c]) {

                    delta = heights[grid[0]][grid[1]] - heights[r][c];
                    if (delta > 0) {
                        sum += delta;
                        heights[r][c] = heights[grid[0]][grid[1]];
                    }

                    minHeap.add(new int[]{r, c});
                    visited[r][c] = true;
                }
            }
        }

        return sum;
    }
    
    
    public static void main(String[] args) {
        int[][] matric = {
            {12, 13, 0, 12},
            {13, 4, 13, 12},
            {13, 8, 10, 12},
            {12, 13, 12, 12},
            {13, 13, 13, 13}
        };

        WaterTrap2D sv = new WaterTrap2D();

        System.out.println(sv.trapRainWater(matric));

    }

}
