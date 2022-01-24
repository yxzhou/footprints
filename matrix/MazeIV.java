/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * Give you a map where ‘S’ indicates the starting point and 'T' indicates the ending point. ‘#’ means that the wall is
 * unable to pass, and '.’ means that the road can take a minute to pass. Please find the minimum time it takes to get
 * from the start point to the end point. If the end point cannot be reached, please output -1.
 *
 * Example
 *   input:map=[['S','.'],['#','T']]
 *   output:t=2
 * 
 * Note: here every point can go 4 directions,  instead of only on stop point 
 *  Back to the BFD, flood-fill
 * 
 * 
 */
public class MazeIV {
    final static int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * 
     * @param maps: 
     * @return the minimum time from the start point to the end point, -1 if cannot be reached
     */
    public int theMazeIV(char[][] maps) {
        if(maps == null){
            return -1;
        }

        int n = maps.length;
        int m = maps[0].length;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(maps[i][j] == 'S'){
                    return shortestPath(maps, i, j);
                }
            }
        }

        return -1;
    }

    private int shortestPath(char[][] maps, int r, int c ){
        int n = maps.length;
        int m = maps[0].length;

        boolean[][] visited = new boolean[n][m];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(r * m + c);
        visited[r][c] = true;

        int top;
        int nr, nc;
        int count = 0;
        while(!queue.isEmpty()){
            count++;

            for(int i = queue.size(); i > 0; i--){
                top = queue.poll();
                r = top / m;
                c = top % m;

                for(int[] diff : diffs){
                    nr = r + diff[0];
                    nc = c + diff[1];

                    if( nr >= 0 && nr < n && nc >= 0 && nc < m && maps[nr][nc] != '#' && !visited[nr][nc] ){
                        if(maps[nr][nc] == 'T' ){
                            return count;
                        }

                        queue.add(nr * m + nc);
                        visited[nr][nc] = true;
                    }
                }

            }
        }

        return -1;
    }
    
    public static void main(String[] args){
        
    }
}
