/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.island;

import java.util.LinkedList;
import java.util.Queue;

/**
 * _https://www.lintcode.com/problem/1080
 * 
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected
 * 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
 *
 * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)
 *
 * The length of each dimension in the given grid does not exceed 50.
 * 
 */
public class MaxAreaIsland {
    /**
     * @param grid: a 2D array
     * @return: the maximum area of an island in the given 2D array
     */
    int[][] diffs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int maxAreaOfIsland(int[][] grid) {
        if(grid == null){
            return 0;
        }

        int n = grid.length;
        int m = grid[0].length;

        boolean[][] visited = new boolean[n][m];
        int max = 0;

        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(grid[r][c] == 1 && !visited[r][c]){
                    max = Math.max(max, bfs(grid, r, c, visited));
                }
            }
        }

        return max;
    }

    private int bfs(int[][] grid, int r, int c, boolean[][] visited){
        Queue<Integer> queue = new LinkedList<>();
        queue.add( (r << 6) | c ); // the grid's length and width don't exceed 50
        visited[r][c] = true;
        int count = 1;

        int top;
        int nr;
        int nc;
        while(!queue.isEmpty()){
            top = queue.poll();
            r = (top >> 6);
            c = top & 0x3f;

            for(int[] diff : diffs){
                nr = r + diff[0];
                nc = c + diff[1];

                if(nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && grid[nr][nc] == 1 && !visited[nr][nc] ){
                    count++;

                    visited[nr][nc] = true;
                    queue.add( (nr << 6) | nc );
                }
            }
        }

        return count;
    }
}
