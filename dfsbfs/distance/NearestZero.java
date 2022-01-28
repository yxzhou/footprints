/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfsbfs.distance;

import java.util.LinkedList;
import java.util.Queue;

/**
 * _https://www.lintcode.com/problem/974
 * 
 * Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.
 * 
 * The distance between two adjacent cells is 1.
 *
 * Notes:
 *   The number of elements of the given matrix will not exceed 10,000. 
 *   There are at least one 0 in the given matrix. 
 *   The cells are adjacent in only four directions: up, down, left and right.
 * 
 * Thoughts:
 *   The matrix only consists of 0 and 1, when it's 0, the distance is 0, when it's 1, it need find the nearest 0.
 *   So flood fill from cell 0. 
 * 
 *   To save the space, it can do it in place, and with BFS instead of DFS 
 *   
 * 
 */
public class NearestZero {
    
    final int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
    /**
     * BFS, update it in place
     * 
     * @param matrix: a 0-1 matrix
     * @return return a matrix
     */
    public int[][] updateMatrix(int[][] matrix) {
        if(matrix == null){
            return null;
        }

        int n = matrix.length;
        int m = matrix[0].length;
        int max = n + m;

        Queue<Integer> queue = new LinkedList<>();

        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(matrix[r][c] == 0 ){
                    queue.add(r * m + c);
                }else{
                    matrix[r][c] = max;
                }
            }
        }

        int top;
        int r;
        int c;
        int nr;
        int nc;
        int d = 0;
        while(!queue.isEmpty()){
            d++;
            for(int k = queue.size(); k > 0; k--){
                top = queue.poll();
                r = top / m;
                c = top % m;

                for(int[] diff : diffs){
                    nr = r + diff[0];
                    nc = c + diff[1];

                    if(nr >= 0 && nr < n && nc >= 0 && nc < m && matrix[nr][nc] > d ){
                        matrix[nr][nc] = d;
                        queue.add(nr * m + nc);
                    }
                }
            }

        }

        return matrix;
    }


}
