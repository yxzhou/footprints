/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.foo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * There is a brick wall in front of you. The wall is rectangular and has several rows of bricks. The bricks have the 
 * same height but different width. You want to draw a vertical line from the top to the bottom and cross the least bricks.
 * 
 * The brick wall is represented by a list of rows. Each row is a list of integers representing the width of each brick in this row from left to right.
 * 
 * If your line go through the edge of a brick, then the brick is not considered as crossed. You need to find out how to
 * draw the line to cross the least bricks and return the number of crossed bricks.
 * 
 * You cannot draw a line just along one of the two vertical edges of the wall, in which case the line will obviously cross no bricks. 
 * 
 * Notes:
 * The width sum of bricks in different rows are the same and won't exceed INT_MAX.
 * The number of bricks in each row is in range [1,10,000]. 
 * The height of wall is in range [1,10,000]. Total number of bricks of the wall won't exceed 20,000.

 * Example1
 * Input: 
 * [[1,2,2,1],
 *  [3,1,2],
 *  [1,3,2],
 *  [2,4],
 *  [3,1,2],
 *  [1,3,1,1]]
 * Output: 2
 * 
 * Example 2:
 * Input: wall = [[1],[1],[1]]
 * Output: 3
 * 
 */
public class BrickWall {
    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer, Integer> edges = new HashMap<>(); // <x-coordinate, count>

        int max = 0; // the max edge in a line
        int x ;// the x-coordiante, The width sum of bricks in different rows are the same and won't exceed INT_MAX
        for(List<Integer> row : wall){
            x = 0;
            for(int i = 0, end = row.size() - 1; i < end; i++){
                x += row.get(i);
                edges.put(x, edges.getOrDefault(x, 0) + 1 );

                max = Math.max(max, edges.get(x));
            }
        }

        return wall.size() - max; 
    }
}
