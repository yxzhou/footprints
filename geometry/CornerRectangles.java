/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

/**
 *
 * Given a grid with only 0 and 1, find the number of corner rectangles.

 * Note that only the corners need to have the value 1. Also, all four 1s used must be distinct.
 *   The number of rows and columns of grid will each be in the range [1,200].
 *   Each grid[i][j] will be either 0 or 1.
 *   The number of 1s in the grid will be at most 6000.
 * 
 * Example 1:
 * Input:
 *   [
 *     [1, 0, 0, 1, 0],
 *     [0, 0, 1, 0, 1],
 *     [0, 0, 0, 1, 0],
 *     [1, 0, 1, 0, 1]
 *   ]
 * Output: 1
 * Explanation: There is only one corner rectangle, with corners grid[1][2], grid[1][4], grid[3][2], grid[3][4].
 * 
 * Example 2:
 * Input:
 *   [
 *     [1, 1, 1],
 *     [1, 1, 1],
 *     [1, 1, 1]
 *   ]
 * Output: 9
 * Explanation: There are four 2x2 rectangles, four 2x3 and 3x2 rectangles, and one 3x3 rectangle.
 * 
 * Example 3:
 * Input: [[1,1,1,1]]
 * Output: 0
 * Explanation: Rectangles must have four distinct corners.
 * 
 * Solution #1 
 *   to every row, find all the edges, store it in array[the column id of edge right point][the edge length]
 *   
 *   Time O(n * m + m * m), space O(m * m)find
 * 
 */
public class CornerRectangles {
    /**
     * @param grid: the grid
     * @return: the number of corner rectangles
     */
    public int countCornerRectangles(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[] ones = new int[m]; //to every row, store the 1's postion
        int tail;
        int[][] edges = new int[m][m]; //[column id][edge length]  
        for(int r = 0; r < n; r++){
            tail = 0;
            for(int c = 0; c < m; c++){
                if(grid[r][c] == 1){
                    ones[tail++] = c;
                }
            }

            for(int j = 1; j < tail; j++){
                for(int i = 0; i < j; i++){
                    edges[ones[j]][ones[j] - ones[i]]++;
                }
            }
        }

        int count = 0;
        for(int c = 1; c < m; c++){
            for(int len = 1; len < m; len++){
                if(edges[c][len] > 1){
                    count += edges[c][len] * (edges[c][len] - 1) / 2; //
                }
            }
        }
        return count;
    }    
}
