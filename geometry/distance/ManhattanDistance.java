package geometry.distance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * _https://leetcode.com/problems/best-meeting-point/
 * A group of two or more people wants to meet and minimize the total travel distance. 
 * You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group. 
 * The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.

    For example, given three people living at (0,0), (0,4), and (2,2):
    1 - 0 - 0 - 0 - 1
    |   |   |   |   |
    0 - 0 - 0 - 0 - 0
    |   |   |   |   |
    0 - 0 - 1 - 0 - 0
    The point (0,2) is an ideal meeting point, as the total travel distance of 2+2+2=6 is minimal. So return 6.

    Hint:
    Try to solve it in one dimension first. How can this solution apply to the two dimension case?
 *
 */

/*
 * 1, to one dimension, 
 *   1.1,  to two points (x1 and x2), define the meeting point as x0, 
 *   the Manhattan Distance is D = |x1 - x0| + |x2 - x0|, 
 *   when x0 = (x1+x2) / 2,  D is minimum. 
 *   1.2,  to three points (x1 and x2 and x3), the meeting point is x0,
 *   the Manhattan Distance is D = |x1 - x0| + |x2 - x0| + |x3 - x0|, 
 *   when x0 is the median of (x1, x2, x3),  D is minimum. 
 *   ---  so the meeting point is the median of ( x1, x2, ---, xn )
 *   
 * 2, to two dimensions, 
 *   2.1, to tow points ( x1-y1 and x2-y2 ), define the meeting point as x0-y0, 
 *   the Manhattan Distance is D = |x1 - x0| + |x2 - x0| + |y1 - y0| + |y2 - y0|
 *   when x0 is the median of (x1, x2) and y0 is the median of (y1, y2), D is minimum.
 *  
 */

public class ManhattanDistance {

    /**
     * 
     * Time O(mnlogmn), Space O(mn)
     */
    public int minTotalDistance(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int sum = 0;
         
        List<Integer> rows = new ArrayList<>(); // the y coordinate
        List<Integer> cols = new ArrayList<>(); // the x coordinate
         
        for (int r = 0, m = grid.length; r < m; r++) { // r is added in the order (from 0 to --)
            for (int c = 0, n = grid[0].length; c < n; c++) {
                if (grid[r][c] == 1) {
                    rows.add(r);
                    cols.add(c);
                }
            }
        }
         
        int median = rows.get(rows.size() / 2);
        for (int r : rows) {
            sum += Math.abs(r - median);
        }
         
        Collections.sort(cols);
        median = cols.get(cols.size() / 2);
        for (int c : cols) {
            sum += Math.abs(c - median);
        }
         
        return sum;
    }
    
    
    /**
     * 
     * Time O(mn), Space O(mn)
     */
    public int minTotalDistance_n(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int sum = 0;
         
        List<Integer> rows = new ArrayList<>(); // the y coordinate
        for (int r = 0, m = grid.length; r < m; r++) { // r is added in the order (from 0 to --)
            for (int c = 0, n = grid[0].length; c < n; c++) {
                if (grid[r][c] == 1) {
                    rows.add(r);
                }
            }
        }
         
        int median = rows.get(rows.size() / 2);
        for (int r : rows) {
            sum += Math.abs(r - median);
        }
         
        List<Integer> cols = new ArrayList<>(); // the x coordinate
        for (int c = 0, n = grid[0].length; c < n; c++) {// c is added in the order (from 0 to --)
            for (int r = 0, m = grid.length; r < m; r++) { 
                if (grid[r][c] == 1) {
                    cols.add(c);
                }
            }
        }        
        
        median = cols.get(cols.size() / 2);
        for (int c : cols) {
            sum += Math.abs(c - median);
        }
         
        return sum;
    }
    
    /**
     * 
     * Time O(mn), Space O(mn)
     */
    public int minTotalDistance_n_2(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int sum = 0;
         
        List<Integer> rows = new ArrayList<>(); // the y coordinate
        for (int r = 0, m = grid.length; r < m; r++) { // r is added in the order (from 0 to --)
            for (int c = 0, n = grid[0].length; c < n; c++) {
                if (grid[r][c] == 1) {
                    rows.add(r);
                }
            }
        }
         
        for (int l = 0, r = rows.size() - 1; l < r; l++, r-- ) {
            sum += rows.get(r) - rows.get(l);
        }
         
        List<Integer> cols = new ArrayList<>(); // the x coordinate
        for (int c = 0, n = grid[0].length; c < n; c++) {// c is added in the order (from 0 to --)
            for (int r = 0, m = grid.length; r < m; r++) { 
                if (grid[r][c] == 1) {
                    cols.add(c);
                }
            }
        }        
        
        for (int l = 0, r = cols.size() - 1; l < r; l++, r-- ) {
            sum += cols.get(r) - cols.get(l);
        }
         
        return sum;
    }
    
}
