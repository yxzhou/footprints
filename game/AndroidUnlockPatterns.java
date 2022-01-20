/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import util.TimeCost;

/**
 * _https://www.lintcode.com/problem/909
 * 
 *  A smart phone lock screen is a grid[3][3] dots. 
 *    1  2  3
 *    4  5  6
 *    7  8  9
 *  count the total number of unlock patterns, which consist of minimum of keys and maximum n keys
 * 
 * It's a valid pattern when 
 *  1 Each pattern must connect at least m keys and at most n keys
 *  2 All the keys in a pattern must be distinct.
 *  3 If the line connecting two consecutive keys in the pattern passes through any other keys, the other keys must have
 *    previously selected in the pattern. No jumps through non selected key is allowed.
 * 
 * Thoughts:
 *   Think about how do we unlock a phone 
 *     Select 1, then it can select 2 and 5 and 8 and 4. if you want to select 3, you have to select 2 or 5 or 8 at first. 
 *     If the selection is 1 - 5 - 9 - 5 - 3, the pattern is 1 - 5 - 3. 
 * 
 * Found, 
 *   1) the key 1 and 3 and 9 and 7 are similar, the pattern that from them are same. 
 *      the key 2 and 6 and 8 and 4 are similar.
 *   2) the blocker is 3 rows, 3 columns and 2 diagonal. 
 * 
 * 
 */
public class AndroidUnlockPatterns {
    static int[][] bridges = new int[10][10];
    static int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};
    
    static {       
        for(int[] block : blocks){
            bridges[block[0]][block[2]] = block[1];
            bridges[block[2]][block[0]] = block[1];
        }
    }
    
    /**
     * @param m: an integer
     * @param n: an integer
     * @return the total number of unlock patterns of the Android lock screen
     */
    public int numberOfPatterns(int m, int n) {
       
        boolean[] visited = new boolean[10];

 //       int sum = 0;
//        for(int i = 1; i < 10; i++){
//            sum += dfs(m, n, visited, i, cache);
//        }
        int sum = dfs(m, n, visited, 1, 1) * 4 
                + dfs(m, n, visited, 2, 1) * 4
                + dfs(m, n, visited, 5, 1);

        return sum;
    }

    private int dfs(int m, int n, boolean[] visited, int from, int count){
        if(count >= n){
            return 1;
        }
        
        visited[from] = true;
        
        int sum = (count >= m? 1 : 0);

        for(int to = 1; to < 10; to++){  
            if(!visited[to] && (bridges[from][to] == 0 || visited[bridges[from][to]]) ){
                sum += dfs(m, n, visited, to, count + 1);
            }
        }

        visited[from] = false;
        
        return sum;
    }
    
    /**
     * @param m: an integer
     * @param n: an integer
     * @return the total number of unlock patterns of the Android lock screen
     */
    public int numberOfPatterns_n(int m, int n) {
        boolean[] visited = new boolean[10];
        Map<Integer, Integer> cache = new HashMap<>();//< status, number of patterns>

        int sum = dfs(m, n, visited, 1, 0, 1, cache) * 4 
                + dfs(m, n, visited, 2, 0, 1, cache) * 4
                + dfs(m, n, visited, 5, 0, 1, cache);

        return sum;
    }

    private int dfs(int m, int n, boolean[] visited, int from, int includes, int count, Map<Integer, Integer> cache){
        int status = (includes << 4) | from;
        
        if(cache.containsKey(status)){
            return cache.get(status);
        }

        if( count >= n){
            return 1;
        }
        
        visited[from] = true;
        
        int sum = (count >= m? 1 : 0);
        int newIncludes = includes | ( 1 << from );

        for(int to = 1; to < 10; to++){  
            if( !visited[to] && ( bridges[from][to] == 0 || visited[bridges[from][to]] ) ){
                sum += dfs(m, n, visited, to, newIncludes, count + 1, cache);
            }
        }

        cache.put(status, sum);
        visited[from] = false;
        
        return sum;
    }
    
    /**
     * @param m: an integer
     * @param n: an integer
     * @return the total number of unlock patterns of the Android lock screen
     */
    public int numberOfPatterns_x(int m, int n) {
        
        Map<Integer, Integer> cache = new HashMap<>();//< status, number of patterns>

        int sum = dfs(m, n, 0, 1, 1, cache) * 4 
                + dfs(m, n, 0, 2, 1, cache) * 4
                + dfs(m, n, 0, 5, 1, cache);

        return sum;
    }

    private int dfs(int m, int n, int visited, int from, int count, Map<Integer, Integer> cache){
        int status = (visited << 4) | from;
        
        if(cache.containsKey(status)){
            return cache.get(status);
        }

        if( count >= n){
            return 1;
        }
        
        visited |= (1 << from);
        
        int sum = (count >= m? 1 : 0);

        for(int to = 1; to < 10; to++){  
            if( ( visited & (1 << to) ) == 0 && (bridges[from][to] == 0 || ( visited & (1 << bridges[from][to])) > 0  ) ){
                sum += dfs(m, n, visited, to, count + 1, cache);
            }
        }

        cache.put(status, sum);

        visited &= ~(1 << from);
        
        return sum;
    }
    
    public static void main(String[] args){
        
        
        int[][] inputs = {
            {1, 1, 9},
            {2, 2, 56},
            {3, 3, 320},
            {4, 4, 1624},
            {5, 5, 7152},
            {6, 6, 26016},
            {7, 7, 72912},
            {8, 8, 140704},
            {9, 9, 140704},
            {4, 9, 389112}
        };
        
        AndroidUnlockPatterns sv = new AndroidUnlockPatterns();
        
        System.out.println("---main: -- " + bridges[3][1]);   
        
        for(int[] input : inputs){
            System.out.println(String.format("-- m:%d, n:%d --", input[0], input[1]));
            
            Assert.assertEquals(input[2], sv.numberOfPatterns(input[0], input[1]));
            Assert.assertEquals(input[2], sv.numberOfPatterns_n(input[0], input[1]));
            Assert.assertEquals(input[2], sv.numberOfPatterns_x(input[0], input[1]));            
        }
        
       /** check the performance **/
        TimeCost tc = TimeCost.getInstance();
        tc.init();
        
        for(int i = 1; i < 10; i++){
            for(int j = i; j < 10; j++){
                sv.numberOfPatterns(i, j);
            }
        }   
        System.out.println("\nThe numberOfPatterns_WithoutCache   timeCost:" + tc.getTimeCost());
        
        for(int i = 1; i < 10; i++){
            for(int j = i; j < 10; j++){
                sv.numberOfPatterns_n(i, j);
            }
        }      
        System.out.println("\nThe numberOfPatterns_n  timeCost:" + tc.getTimeCost());

        for(int i = 1; i < 10; i++){
            for(int j = i; j < 10; j++){
                sv.numberOfPatterns_x(i, j);
            }
        }      
        System.out.println("\nThe numberOfPatterns_x  timeCost:" + tc.getTimeCost());
                
    }
}
