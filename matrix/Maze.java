/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

import java.util.LinkedList;
import java.util.Queue;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/787/
 * 
 * There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up, down, left
 * or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.
 *
 * Given the ball's start position, the destination and the maze, determine whether the ball could stop at the
 * destination.
 *
 * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the
 * borders of the maze are all walls. The start and destination coordinates are represented by row and column indexes.
 *
 * Constraints:
 * 1.There is only one ball and one destination in the maze. 
 * 2.Both the ball and the destination exist on an empty space, and they will not be at the same position initially. 
 * 3.The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the 
 * border of the maze are all walls. 
 * 5.The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
 *
 * Thoughts:
 *   1) only on the start and stop points, the ball can change and choose directions.
 *   2) the ball won't stop rolling until hitting a wall or borders.
 *   3) check whether the ball could stop at the destination, it means check if it's the destination only when stopping. 
 * 
 */
public class Maze {
    /**
     * @param maze: the maze
     * @param start: the start
     * @param destination: the destination
     * @return: whether the ball could stop at the destination
     */
    
    final static int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public boolean hasPath(int[][] maze, int[] start, int[] destination) {

        int n = maze.length;
        int m = maze[0].length;

        boolean[][] visited = new boolean[n][m];
        Queue<Integer> queue = new LinkedList<>(); // store the start and all stop point
        queue.add(start[0] * m + start[1]);
        visited[start[0]][start[1]] = true; //for case, the start point is a stop 

        int top;
        int r, c;
        int nr, nc;
        while (!queue.isEmpty()) {
            top = queue.poll();
            r = top / m;
            c = top % m;

            for (int[] diff : diffs) {
                nr = r;
                nc = c;
                
                //rolling until hitting wall or borders
                while(nr >= 0 && nr < n && nc >= 0 && nc < m && maze[nr][nc] == 0){
                    nr += diff[0];
                    nc += diff[1];
                }
                
                //back to the stop point
                nr -= diff[0];
                nc -= diff[1];
                if (!visited[nr][nc]) {
                    if (nr == destination[0] && nc == destination[1]) {
                        return true;
                    }

                    queue.add(nr * m + nc);
                    visited[nr][nc] = true;
                }
            }
        }

        return false;
    }
    
    public static void main(String[] args){
        int[][][][] inputs = {
            {
                { 
                    {0,0,1,0,0},
                    {0,0,0,0,0},
                    {0,0,0,1,0},
                    {1,1,0,1,1},
                    {0,0,0,0,0}
                },
                {{0, 4}, {3, 2}, {0 /*false*/}},
            }, 
            {
                { 
                    {0,0,1,0,0},
                    {0,0,0,0,0},
                    {0,0,0,1,0},
                    {1,1,0,1,1},
                    {0,0,0,0,0}
                },
                {{0, 4}, {4, 4}, {1 /*false*/}}
            }, 
        };
        
        Maze sv = new Maze();
        
        for(int[][][] input : inputs){
            System.out.println(String.format("\n %s \n-- start: %s and end: %s ", Misc.array2String(input[0]).toString(), Misc.array2String(input[1][0]).toString(), Misc.array2String(input[1][1]).toString() ));
            
            Assert.assertEquals( input[1][2][0] == 1 , sv.hasPath(input[0], input[1][0], input[1][1]) );
            
        }
        
    }
    
}
