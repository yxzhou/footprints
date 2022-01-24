/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/789/
 *
 * There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up (u), down
 * (d), left (l) or right (r), but it won't stop rolling until hitting a wall. When the ball stops, it could choose the
 * next direction. There is also a hole in this maze. The ball will drop into the hole if it rolls on to the hole.
 *
 * Given the position of the ball, the position of the hole and the maze, find out how the ball falls into the hole by
 * moving the shortest distance. The distance is defined by the number of empty spaces the ball passes from the starting
 * position (excluded) to the hole (included). Use "u", "d", "l" and "r" to output the direction of movement. Since
 * there may be several different shortest paths, you should output the shortest method in alphabetical order. If the
 * ball doesn't go into the hole, output "impossible".
 *
 * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the
 * borders of the maze are all walls. The ball and the hole coordinates are represented by row and column indexes.
 * 
 * Constraints{
 * 1.There is only one ball and one destination in the maze. 
 * 2.Both the ball and the destination exist on an empty space, and they will not be at the same position initially. 
 * 3.The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the 
 * border of the maze are all walls.
 * 4.The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
 *
 * Thoughts:
 *   1) the hole exist on an empty space, The ball will drop into the hole, cannot continue
 *   2) not sure if the hole is a stop point. 
 *   3) It need find out all shortest paths, to get the one in alphabetical order
 * 
 */
public class MazeIII {
    
    final static int[] diffs = {0, 1, 0, -1, 0};// 1 move to right, 2 move to up, 3 go to left, 4 go to down
    final static char[] directs = {'0', 'r', 'd', 'l', 'u'}; // see the diffs

    /**
     * Wrong
     * 
     * @param maze: the maze
     * @param ball: the ball position
     * @param hole: the hole position
     * @return the lexicographically smallest way
     */
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        //the start and hole will not be the same position
        assert !(ball[0] == hole[0] && ball[1] == hole[1]);

        int n = maze.length;
        int m = maze[0].length;

        int r = ball[0];
        int c = ball[1];

        int[][] distances = new int[n][m];
        for (int[] row : distances) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        distances[r][c] = 0;

        String[][] ways = new String[n][m];
        ways[r][c] = "";

        Queue<Integer> queue = new LinkedList<>();
        queue.add(r * m + c);

        int top;
        int nr, nc;
        int count;
        int dst;
        while (!queue.isEmpty()) {
            top = queue.poll();
            r = top / m;
            c = top % m;

            f2:
            for (int d = 1; d < directs.length; d++) {
                nr = r;
                nc = c;
                count = 0;

                //roll until hitting a wall or border 
                while (nr >= 0 && nr < n && nc >= 0 && nc < m && maze[nr][nc] == 0) {
                    if (nr == hole[0] && nc == hole[1]) {
                        if ((dst = distances[r][c] + count) < distances[nr][nc] 
                                || (dst == distances[nr][nc] && ways[nr][nc].compareTo(ways[r][c] + directs[d]) > 0)) {
                            distances[nr][nc] = dst;
                            ways[nr][nc] = ways[r][c] + directs[d];
                        }

                        continue f2;
                    }

                    nr += diffs[d - 1];
                    nc += diffs[d];
                    count++;
                }

                //back to the stop
                nr -= diffs[d - 1];
                nc -= diffs[d];
                count--;
                if ((dst = distances[r][c] + count) <= distances[nr][nc]) {
                    if (dst < distances[nr][nc] || ways[nr][nc].compareTo(ways[r][c] + directs[d]) > 0) {
                        ways[nr][nc] = ways[r][c] + directs[d];
                    }

                    if (dst < distances[nr][nc]) {
                        distances[nr][nc] = dst;
                        queue.add(nr * m + nc);
                    }

                }

            }
        }

        return ways[hole[0]][hole[1]] == null ? "impossible" : ways[hole[0]][hole[1]];
    }
    
    public String findShortestWay_Wrong(int[][] maze, int[] ball, int[] hole) {
        if(ball[0] == hole[0] && ball[1] == hole[1]){
            return "";
        }
        
        int n = maze.length;
        int m = maze[0].length;

        int[][] counts = new int[n][m];
        int[][] distances = new int[n][m];
        for(int[] row : distances){
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        distances[ball[0]][ball[1]] = 0;

        int[][] directions = new int[n][m]; //see diffs and backs

        Queue<Integer> queue = new LinkedList<>();
        queue.add(ball[0] * m + ball[1]);

        int top;
        int r, c;
        int nr, nc;
        int count;
        int dst;
        outer: while(!queue.isEmpty()){
            top = queue.poll();
            r = top / m;
            c = top % m;

            f2: for(int i = 1; i < diffs.length; i++){
                nr = r;
                nc = c;
                count = 0;

                //rolling until hitting a wall or border or a hole
                while(nr >= 0 && nr < n && nc >= 0 && nc < m && maze[nr][nc] == 0 ){

                    //found the hole
                    if(nr == hole[0] && nc == hole[1]){
                        //System.out.println(String.format("(%d, %d) to (%d, %d), dst = %d, distances_nrnc = %d, directs[i]-%c, directs[j]-%c", r, c, nr, nc, distances[r][c] + count, distances[nr][nc], directs[i], directs[directions[nr][nc]]));    

                        if((dst = distances[r][c] + count) < distances[nr][nc] || 
                                ( dst == distances[nr][nc] && Character.compare(directs[i], directs[directions[nr][nc]]) > 0)){
                            //Wrong, it's only compare the last character instead of the whole way
                            
                            directions[nr][nc] = i;
                            counts[nr][nc] = count;

                            distances[nr][nc] = dst;
                        }
                       
                        continue f2;
                    }

                    nr += diffs[i - 1];
                    nc += diffs[i];
                    count++;
                }


                //back to the stop point
                nr -= diffs[i - 1];
                nc -= diffs[i];
                count--;

                if( (dst = distances[r][c] + count) <= distances[nr][nc] ){
//                    if((nr ==4 && nc == 2) || (nr ==0 && nc == 2) ){
//                        System.out.println(String.format("--(%d, %d), dst = %d, distances_nrnc = %d, directs[i]-%c, directs[j]-%c", nr, nc, distances[r][c] + count, distances[nr][nc], directs[i], directs[directions[nr][nc]]));  
//                    }                    
                    if (dst < distances[nr][nc]
                            || (dst == distances[nr][nc] && Character.compare(directs[i], directs[directions[nr][nc]]) > 0)) {
                        directions[nr][nc] = i;
                        counts[nr][nc] = count;
                    }

                    if(dst < distances[nr][nc]){
                        distances[nr][nc] = dst;
                        queue.add(nr * m + nc);
                    }
                }

            }
        }

        return retrivePath(maze, ball, hole, directions, counts);
    }

    private String retrivePath(int[][] maze, int[] ball, int[] hole, int[][] directions, int[][] counts){
        StringBuilder sb = new StringBuilder();

        int r = hole[0];
        int c = hole[1];
        int d = directions[r][c]; // direction
        while( d != 0 ){ 
            sb.append(directs[d]);
            
            //rolling back 
            r -= diffs[d - 1] * counts[r][c];
            c -= diffs[d] * counts[r][c];

            if(r == ball[0] && c == ball[1]){
                return sb.reverse().toString();
            }

            d = directions[r][c];
        }

        return "impossible";
    }
    
    public static void main(String[] main){
        
        int[][][][] inputs = {
            {
                {
                    {0,0,0,0,0},
                    {1,1,0,0,1},
                    {0,0,0,0,0},
                    {0,1,0,0,1},
                    {0,1,0,0,0}
                },
                {{4, 3}, {0, 1}}  //"lul", it's better than "ul"
            },
            
        };
        
        String[] expects = {"lul"}; 
        
        MazeIII sv = new MazeIII();
        
        for( int i = 0; i < inputs.length; i++ ){
            int[][][] input = inputs[i];
            
            System.out.println(String.format("\n %s \n-- start: %s and end: %s ", Misc.array2String(input[0]).toString(), Misc.array2String(input[1][0]).toString(), Misc.array2String(input[1][1]).toString() ));
            
            Assert.assertEquals("shortestDistance : ", expects[i], sv.findShortestWay(input[0], input[1][0], input[1][1]) );
            
            Assert.assertEquals("shortestDistance : ", expects[i], sv.findShortestWay_Wrong(input[0], input[1][0], input[1][1]) );
        }
        
    }
    
}
