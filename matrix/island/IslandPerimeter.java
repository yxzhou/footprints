/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.island;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://www.lintcode.com/problem/1225
 * 
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water. Grid
 * cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water, and there
 * is exactly one island (i.e., one or more connected land cells). The island doesn't have "lakes" (water inside that
 * isn't connected to the water around the island). One cell is a square with side length 1. The grid is rectangular,
 * width and height don't exceed 100. Determine the perimeter of the island.
 * 
 * Example
 * [[0,1,0,0],
 *  [1,1,1,0],
 *  [0,1,0,0],
 *  [1,1,0,0]]
 * Answer: 16
 * The boundary of the island is the yellow edge in the figure below, and its perimeter is 16.
 * 
 */
public class IslandPerimeter {
    
    int[][] diffs = {{-1, 0},{1, 0},{0, -1},{0, 1}};
    
    /**
     * @param grid: a 2D array
     * @return: the perimeter of the island
     */
    public int islandPerimeter(int[][] grid) {
        if(grid == null){
            return 0;
        }

        int count = 0;

        int n = grid.length;
        int m = grid[0].length;

        int x;
        int y;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(grid[i][j] == 1){
                    count += 4;
                    for(int[] diff : diffs){
                        x = i + diff[0];
                        y = j + diff[1];

                        if(x >=0 && x < n && y >= 0 && y < m && grid[x][y] == 1 ){
                            count--;
                        }
                    }
                }
            }
        }

        return count;
    }
    
    /**
     * @param grid: a 2D array
     * @return: the perimeter of the island
     */
    public int islandPerimeter_BFS(int[][] grid) {
        if(grid == null){
            return 0;
        }

        int count = 0;

        int n = grid.length;
        int m = grid[0].length;

        Queue<Integer> queue = new LinkedList<>(); //n and m don't exceed 100 
        int top;
        boolean[][] visited = new boolean[n][m];
        int x;
        int y;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(grid[i][j] == 1){
                    queue.add((i << 7) | j);
                    visited[i][j] = true;

                    while(!queue.isEmpty()){
                        top = queue.poll();
                        i = top >> 7;
                        j = top & 0x7f;

                        count += 4;
                        for(int[] diff : diffs){
                            x = i + diff[0];
                            y = j + diff[1];

                            if(x >=0 && x < n && y >= 0 && y < m && grid[x][y] == 1 ){
                                count--;

                                if(!visited[x][y]){
                                    queue.add((x << 7) | y);
                                    visited[x][y] = true;
                                }
                            }
                        }
                    }

                    return count; // only one island
                }
            }
        }

        return count;
    }
    
        /**
     * @param grid: a 2D array
     * @return: the perimeter of the island
     */
    public int islandPerimeter_DFS(int[][] grid) {
        //TODO
        return -1;
    }
}
