package geometry.distance;

import java.util.LinkedList;
import java.util.Queue;

/**
 * _https://leetcode.com/problems/shortest-distance-from-all-buildings/
 * You want to build a house on an empty land which reaches all buildings in the shortest amount of distance. 
 * You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:
 * 
    Each 0 marks an empty land which you can pass by freely.
    Each 1 marks a building which you cannot pass through.
    Each 2 marks an obstacle which you cannot pass through.
    For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):
    1 - 0 - 2 - 0 - 1
    |   |   |   |   |
    0 - 0 - 0 - 0 - 0
    |   |   |   |   |
    0 - 0 - 1 - 0 - 0
    The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal. So return 7.
    
    Note:
    There will be at least one building. If it is not possible to build such house according to the above rules, return -1.
 *
 */

public class ManhattanDistanceII {

    public int shortestDistance_dfs(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        int[][] sum = new int[m][n]; //default all are 0
        int[][] minDistance = new int[m][n]; //default all are 0
        
        for(int row = 0; row < m; row++){
            for(int col = 0; col < n; col++){
                if(grid[row][col] == 1){
                    for(int i = 0; i < m; i++){
                        for(int j = 0; j < n; j++){
                            if(minDistance[i][j] != Integer.MAX_VALUE && sum[i][j] != Integer.MAX_VALUE){
                                sum[i][j] += minDistance[i][j];
                            }else{
                                sum[i][j] = Integer.MAX_VALUE;
                            }
                            
                            minDistance[i][j] = Integer.MAX_VALUE;
                        }
                    }
                    
                    dfs(grid, row, col, minDistance, 0);
                }
            }
        }
        
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(minDistance[i][j] != Integer.MAX_VALUE && sum[i][j] != Integer.MAX_VALUE && grid[i][j] == 0){
                    sum[i][j] += minDistance[i][j];
                    min = Math.min(min, sum[i][j]);
                }
            }
        }
        
        return min == Integer.MAX_VALUE ? -1 : min;
    }
    
    private void dfs(int[][] grid, int row, int col, int[][] minDistance, int distance){
        if(row < 0 || row >= grid.length || col < 0 || col >= grid[0].length){
            return;
        }
        
        if(grid[row][col] == 2 || (grid[row][col] == 1 && distance != 0)){
            return;
        }
        
        if( minDistance[row][col] <= distance){
            return;
        }
        minDistance[row][col] = distance;

        distance++;
        dfs(grid, row + 1, col, minDistance, distance);
        dfs(grid, row - 1, col, minDistance, distance);
        dfs(grid, row, col + 1, minDistance, distance);
        dfs(grid, row, col - 1, minDistance, distance);
        
    }
        
    public int shortestDistance_bfs(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        int[][] sum = new int[m][n]; //default all are 0
        int[][] count = new int[m][n]; //default all are 0
        int buildingsNum = 0;
        
        for(int row = 0; row < m; row++){
            for(int col = 0; col < n; col++){
                if(grid[row][col] == 1){
                    buildingsNum++;
                    
                    bfs(grid, row, col, sum, count, new boolean[m][n]);
                }
            }
        }
        
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(count[i][j] == buildingsNum && grid[i][j] == 0){
                    min = Math.min(min, sum[i][j]);
                }
            }
        }
        
        return min == Integer.MAX_VALUE ? -1 : min;
    }
    
    private void bfs(int[][] grid, int row, int col, int[][] sum, int[][] count, boolean[][] isVisited){
        int m = grid.length;
        int n = grid[0].length;
        
        Queue<Integer> queue = new LinkedList<>();
        int p = row * n + col;
        queue.add(p);
        
        int distance = 0;
        
        int[][] diffs = {{1, 0},{-1, 0},{0, 1},{0, -1}};
        int x, y;
        while(!queue.isEmpty()){
            for(int i = queue.size(); i > 0; i--){
                p = queue.poll();
                
                row = p / n;
                col = p % n;
                
                sum[row][col] += distance;
                count[row][col]++;
                
                for(int[] next : diffs){
                    x = row + next[0];
                    y = col + next[1];
                    
                    if(x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 0 && !isVisited[x][y]){
                        p = x * n + y;
                        queue.add(p);
                        
                        isVisited[x][y] = true;
                    }
                }

            }
            
            distance++;
        }
        
    }
    
    public static void main(String[] args) { 
        ManhattanDistanceII sv = new ManhattanDistanceII();
        
        int[][] matrix = {
                    {1,0,2,0,1},
                    {0,0,0,0,0},
                    {0,0,1,0,0}
        };

        System.out.println(sv.shortestDistance_dfs(matrix));
        System.out.println(sv.shortestDistance_bfs(matrix));
    }

}
