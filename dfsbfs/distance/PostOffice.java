package dfsbfs.distance;

import java.util.LinkedList;
import java.util.List;

/**
 * Build Post Office   _https://www.lintcode.com/problem/574/description
 *
 * Given a 2D grid, each cell is either an house 1 or empty 0 (It is represented by the numbers 0, 1),
 * find the place to build a post office, the distance that post office to all the house sum is smallest.
 * Returns the sum of the minimum distances from all houses to the post office, Return -1 if it is not possible.
 *
 * Notes:
 * You can pass through house and empty.
 * You only build post office on an empty.
 * The distance between house and the post office is Manhattan distance
 *
 * Solution #1 brute force
 *    find all house,
 *    to every empty, calculate all the distance to every house.
 *
 *    Space O(n*m),  Time O(n * m * n * m)
 *
 * Solution #21   count the house on every Row and Col
 *    find all house, count house on every Row and Col
 *    to every empty, calculate all the distance to every house.
 *
 *    Space O(Math.max(n, m)),  Time O(n * m * Math.max(n, m))
 *
 * Solution #2n pre-calculate the cost on every Row and Col
 *    find all house, count house on every Row and Col
 *    pre-calculate the cost on every Row and Col
 *    to every empty, calculate all the distance to every house.
 *
 *    Space O(Math.max(n, m)),  Time O( Math.max(n, m) * Math.max(n, m) )
 */

public class PostOffice {

    /**
     * @param grid: a 2D grid
     * @return: An integer
     */
    public int shortestDistance_1(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return -1;
        }

        int n = grid.length;
        int m = grid[0].length;

        //find out all house
        List<int[]> houses = new LinkedList<>();
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(grid[r][c] == 1){
                    houses.add(new int[]{r,c});
                }
            }
        }

        //find all empty, sum the all distance to all the house.
        int min = Integer.MAX_VALUE;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(grid[r][c] == 0){
                    int d = 0;

                     for(int[] h : houses){
                         d += Math.abs(r - h[0]) + Math.abs(c - h[1]);
                     }

                     min = Math.min(min, d);
                }
            }
        }

        return min;
    }


    /**
     * @param grid: a 2D grid
     * @return: An integer
     */
    public int shortestDistance_21(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return -1;
        }

        int n = grid.length;
        int m = grid[0].length;

        //find out all house
        int[] countX = new int[n];
        int[] countY = new int[m];
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(grid[r][c] == 1){
                    countX[r]++;
                    countY[c]++;
                }
            }
        }

        //find all empty, sum the all distance to all the house.
        int min = Integer.MAX_VALUE;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(grid[r][c] == 0){
                    int d = 0;

                    for(int i = 0; i < n; i++){
                        d += Math.abs(i - r) * countX[i];
                    }
                    for(int i = 0; i < m; i++){
                        d += Math.abs(i - c) * countY[i];
                    }

                    min = Math.min(min, d);
                }
            }
        }

        return min;
    }


    /**
     * @param grid: a 2D grid
     * @return: An integer
     */
    public int shortestDistance_2n(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return -1;
        }

        int n = grid.length;
        int m = grid[0].length;

        //find out all house
        int[] countRow = new int[n];
        int[] countCol = new int[m];
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(grid[r][c] == 1){
                    countRow[r]++;
                    countCol[c]++;
                }
            }
        }

        int[] costRow = calCost(countRow);
        int[] costCol = calCost(countCol);

        //find all empty, sum the all distance to all the house.
        int min = Integer.MAX_VALUE;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(grid[r][c] == 0){
                    min = Math.min(min, costRow[r] + costCol[c]);
                }
            }
        }

        return min;
    }

    private int[] calCost(int[] counts){
        int n = counts.length;
        int[] costs = new int[n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                costs[i] += counts[j] * Math.abs(i - j);
            }
        }

        return costs;
    }


}
