/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs.distance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1364
 *
 * You are now given a two-dimensional tabular graph , in which each grid contains a integer num.
 *
 * If num is - 2, it means this grid is the starting grid. If num is - 3, it means this grid is the ending grid. If num
 * is - 1, it means this grid has an obstacle on it and you can't move to it. If num is a positive number or 0，you can
 * walk normally on it.
 *
 * In each move you can travel from one grid to another if and only if they're next to each other or they contain the
 * same positive number num (num != 0). The cost of each move is 1.
 *
 * Now you are asked to find the lowest cost of traveling from the starting grid to the ending grid. If the ending grid
 * could not be reached, print -1.
 *
 *
 * It is guaranteed that the maximum number of rows and columns is 400, and the number in each grid will not exceed 50.
 * 
 * Example 
 * Input:
 * [
 *   [1, 0,-1, 1],
 *   [-2,0, 1,-3],
 *   [2, 2, 0, 0]
 * ] 
 * Output:3 
 * Explanation: In this example,you can reach the ending grid through these moves: 
 * First, move up from the starting grid to the grid that contains the number 1. 
 * Second, move to the grid with the same number at the top right. 
 * Finally, move down to the ending grid. 
 * There are three moves in total, so the minimum cost will be 3.
 * 
 */
public class TheMinimumDistance {
    
    /**
     * 
     * m1) BFS
     * 
     * @param mazeMap: a 2D grid
     * @return the minium distance
     */

    final static int[][] diffs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int getMinDistance(int[][] mazeMap) {
        if(mazeMap == null || mazeMap.length == 0 || mazeMap[0].length == 0 ){
            return -1;
        }
        
        int n = mazeMap.length;
        int m = mazeMap[0].length;

        boolean[][] visited = new boolean[n][m];
        Queue<Integer> queue = new LinkedList<>();

        Map<Integer, List<Integer>> map = new HashMap<>(50); //the number in each grid will not exceed 50 

        int nr, nc;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                if(mazeMap[r][c] == -1){
                    continue;
                }

                if(mazeMap[r][c] == -2){
                    visited[r][c] = true;
                    queue.add(r * m + c);
                }else if(mazeMap[r][c] > 0){  // -3, 0, 1+
                    map.computeIfAbsent(mazeMap[r][c], x -> new LinkedList<>()).add(r * m + c);
                }
            }
        }

        int count = 0;
        int top;
        int r, c;
        while(!queue.isEmpty()){

            count++;
            for(int k = queue.size(); k > 0; k--){
                top = queue.poll();
                r = top / m;
                c = top % m;

                //go to nearby cell whose value is not -1
                for(int[] diff : diffs){
                    nr = r + diff[0];
                    nc = c + diff[1];
                    if(nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc] && mazeMap[nr][nc] != -1){
                        if(mazeMap[nr][nc] == -3){
                            return count;
                        }

                        visited[nr][nc] = true;
                        queue.add(nr * m + nc);
                    }
                }
               
                //go to cell whose value equals to mazeMap[r][c]
                if( !map.containsKey(mazeMap[r][c]) ){
                    continue;
                }
                
                for(int next : map.get(mazeMap[r][c])){
                    nr = next / m;
                    nc = next % m;

                    if(!visited[nr][nc]){
                        visited[nr][nc] = true;
                        queue.add(nr * m + nc);
                    }
                }
                
                map.remove(mazeMap[r][c]); // KEY point, jump by value once per value
            }
        }

        return -1;
    }
    
    public static void main(String[] args){
        int[][][][] inputs = {
            {
                {
                    { 1,0,-1, 1},
                    {-2,0, 1,-3},
                    { 2,2, 0, 0}
                },
                {{3}}

            },
            {
                {
                    { 0, 0, 0,2, 6, 0,10, 4,9, 0},
                    {-1, 0, 0,0,-1, 0, 0, 0,3, 2},
                    {-1, 0, 0,8, 0, 0, 0, 0,0, 0},
                    { 0, 7, 0,0, 5, 0, 0,-1,4, 5},
                    { 0, 0, 2,0, 0,10, 3, 1,0, 0},
                    { 3, 0,-1,0, 9, 1, 0, 0,0, 8},
                    {-1, 0, 9,0, 0, 0, 0, 0,0, 4},
                    { 0,10, 3,7, 0,10, 2, 0,0, 0},
                    { 0, 0, 3,0,-3,-1, 0,-2,0,-1},     
                    { 0, 3, 0,1, 0,-1, 0, 5,0, 6}
                },
                {{5}}
            }
        
        };
        
        
        TheMinimumDistance sv = new TheMinimumDistance();
        
        for(int[][][] input : inputs){
            System.out.println(String.format( "\n%s", Misc.array2String(input[0]) ));
            
            System.out.println( sv.getMinDistance(input[0]) );
        }
        
    }
    
    
}
