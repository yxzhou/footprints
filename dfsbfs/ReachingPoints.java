/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs;

import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;

/**
 *
 * 
 * Given four integers sx, sy, tx, and ty, return true if it is possible to convert the point (sx, sy) to the point (tx,
 * ty) through some operations, or false otherwise.
 *
 * The allowed operation on some point (x, y) is to convert it to either (x, x + y) or (x + y, y).
 *
 * Example 1: 
 * Input: sx = 1, sy = 1, tx = 3, ty = 5 
 * Output: true 
 * Explanation: One series of moves that transforms the starting point to the target is: 
 * (1, 1) -> (1, 2) (1, 2) -> (3, 2) (3, 2) -> (3, 5)
 *
 * Example 2: 
 * Input: sx = 1, sy = 1, tx = 2, ty = 2 
 * Output: false
 *
 * Example 3: 
 * Input: sx = 1, sy = 1, tx = 1, ty = 1 
 * Output: true
 *
 * Constraints: 
 *   1 <= sx, sy, tx, ty <= 109
 * 
 * Thoughts:
 *             (3, 5)
 *           /       \
 *       (3, 8)      (8, 5)  
 *        /  \        /   \
 *   (3,11) (11,8) (8,13) (13, 5)
 * 
 *   m1) from Start(a, b) to End(x, y), from smaller to bigger. dfs + memorization
 *   m2) from End(x, y) to Start(a, b), from bigger to smaller. It's narrow down. 
 *   It's smilar with GCD  
 *   
 *   (a+bx, b) -> (a, b)
 *   (a, b+ax) -> (a, b)  
 *    
 * 
 */
public class ReachingPoints {
    
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        
        return dfs(sx, sy, tx, ty, new HashSet<>());
        
    }
    
    private boolean dfs(int sx, int sy, int tx, int ty, Set<String> unreach ){
        
        String state = String.format("%d,%d", sx, sy);
        
        if(unreach.contains(state)){
            return false;
        }
        
        if( sx == tx && sy == ty ){
            return true;
        }
        
        long tmp;
        if(( (tmp = sx + sy) <= ty && dfs(sx, (int)tmp, tx, ty, unreach))  || ( (tmp = sx + sy) <= tx && dfs((int)tmp, sy, tx, ty, unreach)) ){
            return true;
        }
        
        unreach.add(state);
        return false;
    }
    
    public boolean reachingPoints_n(int sx, int sy, int tx, int ty) {
        
        if(sx > tx || sy > ty){
            return false;
        }
            
        while ( sx <= tx && sy <= ty ){
            if(tx == ty){
                break;
            }
            
            if(tx > ty){
                if(ty > sy){
                    tx %= ty;
                }else { //ty == sy
                    return (tx - sx) % ty == 0;
                }
            }else{
                if(tx > sx ){
                    ty %= tx;
                }else { //tx == sx
                    return (ty - sy) % tx == 0;
                }
            }
            
            System.out.print(String.format("\ntx=%d, ty=%d", tx, ty));
        }
        
        return sx == tx && sy == ty;
        
    }
    
    
    public static void main(String[] args){
        int[][][] inputs = {
            {{1, 1, 3, 5}, {1}},
            {{1, 1, 1, 1}, {1}},
            {{1, 1, 2, 2}, {0}},
            {{2, 7, 9, 16}, {1}},
            {{9, 10, 9, 19}, {1}},
            {{35, 13, 455955547, 420098884}, {0}},

        };
        
        ReachingPoints sv = new ReachingPoints();
        
        for(int[][] input : inputs){
            System.out.print(String.format("\nsx = %d, sy = %d, tx = %d, ty = %d", input[0][0], input[0][1], input[0][2], input[0][3]));
            
            //Assert.assertEquals(input[1][0], sv.reachingPoints(input[0][0], input[0][1], input[0][2], input[0][3])? 1 : 0 );
            
            Assert.assertEquals(input[1][0], sv.reachingPoints_n(input[0][0], input[0][1], input[0][2], input[0][3])? 1 : 0 );
           
        }
    }
    
    
}
