/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/788
 * Continue on Maze
 * 
 * Here it requires the shortest distance. 
 * 
 * Thoughts:
 *   m1) do it as same as the way in Maze, BFS, replace the boolean[][] visited with int[][] distances, which to store 
 *       the distance from the start to the stop. 
 *       The stop will be visited multiple times with different distance value. If the new distance is smaller, it need
 *       re-start from the stop. 
 *       The distance is shortest only when all new smaller distance  
 *       
 *       
 *   m2) To avoid re-start from a stop again and again, it can be with Dijkstra algorithm. 
 *   Time O(ElogV) -> O( mn*logmn )
 *       
 * 
 */
public class MazeII {
    
    final static int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
    /**
     * @param maze: the maze
     * @param start: the start
     * @param destination: the destination
     * @return the shortest distance for the ball to stop at the destination
     */
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int n = maze.length;
        int m = maze[0].length;

        int[][] distances = new int[n][m];
        for (int[] row : distances) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        //BFS
        Queue<Integer> queue = new LinkedList<>(); // store the start and all stop point
        queue.add(start[0] * m + start[1]);

        distances[start[0]][start[1]] = 0;

        int top;
        int r, c, nr, nc;
        int count;
        int dst;
        while (!queue.isEmpty()) {
            top = queue.poll();
            r = top / m;
            c = top % m;

            for (int[] diff : diffs) {
                nr = r;
                nc = c;
                count = 0;
                
                //rolling until hitting the walls or borders
                while(nr >= 0 && nr < n && nc >= 0 && nc < m && maze[nr][nc] == 0){
                    nr += diff[0];
                    nc += diff[1];
                    count++;
                }

                //back to the stop point
                nr -= diff[0];
                nc -= diff[1];
                count--;

                if ( (dst = distances[r][c] + count) < distances[nr][nc]) {
                    distances[nr][nc] = dst;

                    queue.add(nr * m + nc);
                }
              
            }
        }

        return distances[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : distances[destination[0]][destination[1]];
    }
    
    /**
     * Dijkstra 
     * 
     * @param maze: the maze
     * @param start: the start
     * @param destination: the destination
     * @return the shortest distance for the ball to stop at the destination
     */
    public int shortestDistance_2(int[][] maze, int[] start, int[] destination) {
        int n = maze.length;
        int m = maze[0].length;
        
        int[][] distances = new int[n][m];
        for(int[] row : distances){
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        
        PriorityQueue<int[]> minHeap = new PriorityQueue<>( (a, b) -> Integer.compare(distances[a[0]][a[1]], distances[b[0]][b[1]]) );
        minHeap.add(start);
        
        distances[start[0]][start[1]] = 0;
        
        int[] curr;
        int nr, nc;
        int count;
        int dst;
        while(!minHeap.isEmpty()){
            curr = minHeap.poll();
            
            if(curr[0] == destination[0] && curr[1] == destination[1]){
                return distances[curr[0]][curr[1]];
            }
            
            for(int[] diff : diffs){
                nr = curr[0];
                nc = curr[1];
                count = 0;
                
                //rolling until hitting the walls or borders
                while(nr >= 0 && nr < n && nc >= 0 && nc < m && maze[nr][nc] == 0){
                    nr += diff[0];
                    nc += diff[1];
                    count++;
                }

                //back to the stop point
                nr -= diff[0];
                nc -= diff[1];
                count--;
                
                if( (dst = distances[curr[0]][curr[1]] + count) < distances[nr][nc] ){// not only happened when distances[nr][nc] is Integer.MAX_VALUE                                    
                    distances[nr][nc] = dst;
                    minHeap.add(new int[]{nr, nc});
                }

            }            
        }
        
        return -1;
    }
    
    
    public static void main(String[] args){
        int[][][][] inputs = { //{ maze, {start, destination, the shortest distance} }
            {
                {   
                    {0,0,1,0,0},
                    {0,0,0,0,0},
                    {0,0,0,1,0},
                    {1,1,0,1,1},
                    {0,0,0,0,0}
                },
                {{0, 4}, {4, 4}, {12 /*the shortest distance*/}},
            }, 
            {
                { 
                    {0,0,1,0,0},
                    {0,0,0,0,0},
                    {0,0,0,1,0},
                    {1,1,0,1,1},
                    {0,0,0,0,0}
                },
                {{0, 4}, {0, 0}, {6 }}
            }, 
            {
                {
                    {0,0,0,0,0,0},
                    {0,1,1,1,1,0},
                    {0,0,0,0,0,0}
                },
                {{0,1}, {2,5}, {6} }
            },
        };
        
        MazeII sv = new MazeII();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\n %s \n-- start: %s and end: %s ", Misc.array2String(input[0]).toString(), Misc.array2String(input[1][0]).toString(), Misc.array2String(input[1][1]).toString() ));
            
            Assert.assertEquals("shortestDistance : ", input[1][2][0], sv.shortestDistance(input[0], input[1][0], input[1][1]) );
            Assert.assertEquals("shortestDistance_2", input[1][2][0], sv.shortestDistance_2(input[0], input[1][0], input[1][1]) );
        }
        
    }
    
}
