package fgafa.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * _https://leetcode.com/problems/best-meeting-point/
 * A group of two or more people wants to meet and minimize the total travel distance. 
You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group. The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.

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

    public int minTotalDistance(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
         
        int m = grid.length;
        int n = grid[0].length;
         
        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> colIndex = new ArrayList<>();
         
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    rowIndex.add(i);
                    colIndex.add(j);
                }
            }
        }
         
        int sum = 0;
        int mid = rowIndex.get(rowIndex.size() / 2);
        for (int x : rowIndex) {
            sum += Math.abs(x - mid);
        }
         
        Collections.sort(colIndex);
        mid = colIndex.get(colIndex.size() / 2);
         
        for (int y : colIndex) {
            sum += Math.abs(y - mid);
        }
         
        return sum;
    }
    
}
