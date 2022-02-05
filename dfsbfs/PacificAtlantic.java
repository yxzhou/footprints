/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * _https://www.lintcode.com/problem/778
 *
 * Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific
 * ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.
 *
 * Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or
 * lower.
 *
 * Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.
 *
 * Constraints:
 *   1.The order of returned grid coordinates does not matter. 
 *   2.Both m and n are less than 150.
 *
 * Example 1:
 * Input:
 * matrix = 
    [[1,2,2,3,5],
    [3,2,3,4,4],
    [2,4,5,3,1],
    [6,7,1,4,5],
    [5,1,1,2,4]]
 * Output:
    [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
 * Explanation:
    Pacific ~ ~ ~ ~ ~
            ~ 1 2 2 3 5 *
            ~ 3 2 3 4 4 *
            ~ 2 4 5 3 1 *
            ~ 6 7 1 4 5 *
            ~ 5 1 1 2 4 *
                * * * * * Atlantic
        
 * Example 2:
 * Input:
 * matrix =
    [[1,2],
    [4,3]]
 * Output:
    [[0,1],[1,0],[1,1]]
 * 
 * Thoughts:
 *   How to find the grid coordinates where water can flow to both the Pacific and Atlantic ocean?
 *   See example #1, the Pacific is the first row and column, the Atlantic is the last row and column. 
 *   From Pacific and Atlantic, "flow back" to see which cells are connected to. 
 * 
 *   m1) dfs, find out the cells from Pacific and Atlantic, return the overlap
 *   m2) bfs, 
 *   
 * 
 */
public class PacificAtlantic {
    
    final int[][] diffs = {{1, 0}, {-1, 0}, {0,1}, {0, -1} };
    
    /**
     * @param matrix: the given matrix
     * @return The list of grid coordinates
     */
    public List<List<Integer>> pacificAtlantic_DFS(int[][] matrix) {
        if(matrix == null){
            return Collections.EMPTY_LIST;
        }

        int n = matrix.length;
        int m = matrix[0].length;

        boolean[][] pacifics = new boolean[n][m];
        boolean[][] atlantics = new boolean[n][m];
        
        for(int c = 0, lastRow = n - 1; c < m; c++){
            pacifics[0][c] = true; //first row
            dfs(matrix, 0, c, pacifics);

            atlantics[lastRow][c] = true; //last row
            dfs(matrix, lastRow, c, atlantics);
        }

        for(int r = 0, lastColumn = m - 1; r < n; r++){
            pacifics[r][0] = true; //first column
            dfs(matrix, r, 0, pacifics);

            atlantics[r][lastColumn] = true; //last column
            dfs(matrix, r, lastColumn, atlantics);
        }

        List<List<Integer>> result = new LinkedList<>();
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(pacifics[r][c] && atlantics[r][c]){
                    result.add(Arrays.asList(r, c));
                }
            }
        }

        return result;
    }

    private void dfs(int[][] matrix, int r, int c, boolean[][] visited){
        
        int n = matrix.length;
        int m = matrix[0].length;
        int nr;
        int nc;
        for(int[] diff : diffs ){
            nr = r + diff[0];
            nc = c + diff[1];

            if(nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc] && matrix[r][c] <= matrix[nr][nc]  ) {
                visited[nr][nc] = true;
                dfs(matrix, nr, nc, visited);
            }
        }

    }
    
    /**
     * @param matrix: the given matrix
     * @return The list of grid coordinates
     */
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        
        if(matrix == null){
            return Collections.EMPTY_LIST;
        }

        int n = matrix.length;
        int m = matrix[0].length;

        boolean[][] pacifics = new boolean[n][m];
        Queue<Integer> pacificQueue = new LinkedList<>();

        boolean[][] atlantics = new boolean[n][m];
        Queue<Integer> atlanticQueue = new LinkedList<>();
        
        for(int c = 0, lastRow = n - 1; c < m; c++){
            pacifics[0][c] = true; //first row
            pacificQueue.add(c);

            atlantics[lastRow][c] = true; //last row
            atlanticQueue.add(lastRow * m + c);
        }

        for(int r = 0, lastColumn = m - 1; r < n; r++){
            pacifics[r][0] = true; //first column
            pacificQueue.add(r*m);

            atlantics[r][lastColumn] = true; //last column
            atlanticQueue.add(r * m + lastColumn);
        }

        bfs(matrix, pacificQueue, pacifics);
        bfs(matrix, atlanticQueue, atlantics);

        List<List<Integer>> result = new LinkedList<>();
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(pacifics[r][c] && atlantics[r][c]){
                    result.add(Arrays.asList(r, c));
                }
            }
        }

        return result;
    }

    private void bfs(int[][] matrix, Queue<Integer> queue, boolean[][] visited){
        
        int n = matrix.length;
        int m = matrix[0].length;
        int r, c;
        int nr, nc;
        int top;
        while(!queue.isEmpty()){
            top = queue.poll();
            r = top / m;
            c = top % m;

            for(int[] diff : diffs ){
                nr = r + diff[0];
                nc = c + diff[1];

                if(nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc] && matrix[r][c] <= matrix[nr][nc]  ) {
                    visited[nr][nc] = true;
                    queue.add(nr * m + nc);
                }
            }
        }

    }
    
}
