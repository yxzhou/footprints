package fgafa.matrix;

import java.util.LinkedList;
import java.util.Queue;

import fgafa.util.Misc;

/**
 * 
 * You are given a m x n 2D grid initialized with these three possible values.
    -1 - A wall or an obstacle.
    0 - A gate.
    INF - Infinity means an empty room. We use the value 2^31 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
    
    Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
    For example, given the 2D grid:
    INF  -1  0  INF
    INF INF INF  -1
    INF  -1 INF  -1
      0  -1 INF INF
    After running your function, the 2D grid should be:
      3  -1   0   1
      2   2   1  -1
      1  -1   2  -1
      0  -1   3   4
 *
 */

public class WallsAndGates {

    public void wallsAndGates_dfs(int[][] rooms) {
        if(null == rooms || 0 == rooms.length || 0 == rooms[0].length){
            return;
        }
        
        int m = rooms.length;
        int n = rooms[0].length;
        for(int rowNum = 0; rowNum < m; rowNum++){
            for(int colNum = 0; colNum < n; colNum++){
                if(0 == rooms[rowNum][colNum]){
                    wallsAndGates_dfs(rooms, rowNum, colNum, 0);
                }
            }
        }
    }
    
    private void wallsAndGates_dfs(int[][] rooms, int rowNum, int colNum, int distance) {
        if(rowNum < 0 || rowNum >= rooms.length || colNum < 0 || colNum >= rooms[0].length){
            return;
        }
        
        if(rooms[rowNum][colNum] < distance || (distance != 0 && rooms[rowNum][colNum] == distance)){
            return;
        }
        
        rooms[rowNum][colNum] = distance;
        
        wallsAndGates_dfs(rooms, rowNum + 1, colNum, distance + 1);
        wallsAndGates_dfs(rooms, rowNum - 1, colNum, distance + 1);
        wallsAndGates_dfs(rooms, rowNum, colNum + 1, distance + 1);
        wallsAndGates_dfs(rooms, rowNum, colNum - 1, distance + 1);
    }
    
    public void wallsAndGates_n(int[][] rooms) {
        if(null == rooms || 0 == rooms.length || 0 == rooms[0].length){
            return;
        }
        
        int m = rooms.length;
        int n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        for(int rowNum = 0; rowNum < m; rowNum++){
            for(int colNum = 0; colNum < n; colNum++){
                if(0 == rooms[rowNum][colNum]){
                    queue.add(new int[]{rowNum, colNum});
                }
            }
        }
        
        int[] r;
        int distance = 0;
        while(!queue.isEmpty()){

            for(int i = queue.size(); i > 0; i--){
                r = queue.poll();
                
                if(r[0] < 0 || r[0] >= m || r[1] < 0 || r[1] >= n){
                    continue;
                }
                
                if(rooms[r[0]][r[1]] < distance || ( distance != 0 && rooms[r[0]][r[1]] == distance )){
                    continue;
                }
                
                rooms[r[0]][r[1]] = distance;
                
                queue.add(new int[]{r[0] + 1, r[1]});
                queue.add(new int[]{r[0] - 1, r[1]});
                queue.add(new int[]{r[0], r[1] + 1});
                queue.add(new int[]{r[0], r[1] - 1});
            }
            
            distance++;
        }
    }
    
    public static void main(String[] main){
        WallsAndGates sv = new WallsAndGates();
        
        int INF = Integer.MAX_VALUE;
        int[][] input = {
                    {INF,  -1,  0,  INF},
                    {INF, INF, INF,  -1},
                    {INF,  -1, INF,  -1},
                    {  0,  -1, INF, INF}
        };
        
        System.out.println(" Input:  ");
        System.out.println(Misc.array2String(input));
        
        //sv.wallsAndGates_dfs(input);
        sv.wallsAndGates_n(input);
        
        System.out.println("Output:  ");
        System.out.println(Misc.array2String(input));
        
        
    }
}
