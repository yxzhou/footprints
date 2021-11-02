/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfsbfs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import org.junit.Assert;

/**
 * A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone. 
 * The frog can jump on a stone, but it must not jump into the water.
 * 
 * Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able to cross the 
 * river by landing on the last stone. Initially, the frog is on the first stone and assume the first jump must be 1 unit.
 * 
 * If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units. 
 * Note:
 *   The frog can only jump in the forward direction.
 *   The number of stones is ≥ 1 and is < 1100.
 *   Each stone's position will be a non-negative integer < 2^31.
 *   The first stone's position is always 0.
 * 
 * Example 1:
 * Input [0,1,3,5,6,8,12,17]
 * Output: true
 * Explanation: The frog can jump to the last stone by jumping 1 unit to the 2nd stone, then 2 units to the 3rd stone, 
 * then 2 units to the 4th stone, then 3 units to the 6th stone, 4 units to the 7th stone, and 5 units to the 8th stone.
 * 
 * Example 2:
 * Input: [0,1,2,3,4,8,9,11]
 * Output: alse
 * Explanation: There is no way to jump to the last stone as the gap between the 5th and 6th stone is too large.
 * 
 */
public class FrogJump {
    /**
     * 
     * @param stones
     * @return 
     */
    public boolean canCross_BFS(int[] stones) {
        if(stones == null || stones.length == 0){
            return false;
        }
        if(stones.length == 1 ){//The first stone's position is always 0
            return true;
        }

        int last = stones[stones.length - 1];

        Set<Integer> set = new HashSet<>();
        for(int stone : stones){
            set.add(stone);
        }

        Queue<int[]> queue = new LinkedList<>(); //<new int[]{position index, speed}
        queue.add(new int[]{0, 1}); 

        Set<String> visited = new HashSet<>();

        int[] top;
        int p;
        int s;
        while(!queue.isEmpty()){
            top = queue.poll();
            for(int d = -1; d < 2; d++){
                s = top[1] + d;
                p = top[0] + s;
                
                if(s > 0 && set.contains(p) && !visited.contains(p + " " + s)){//jump forward && jump to a stone && not duplicated
                    if(p == last){
                        return true;
                    }

                    queue.add(new int[]{p, s});
                    visited.add(p + " " + s);
                }
            }
        }

        return false;
    }
    
    public boolean canCross_DP(int[] stones) {
        if(stones == null || stones.length == 0){
            return false;
        }
        
        int n = stones.length;
        if(n == 1){//The first stone's position is always 0
            return true;
        }
        
        boolean[][] f = new boolean[n][n-1];//f[p][s] mark a state that it's in stone[p] with speed s
        f[0][1] = true;
        
        int distance;
        for(int i = 1; i < n; i++){
            for(int j = i - 1; j >= 0; j--){
                distance = stones[i] - stones[j];
                
                if(distance > j + 1){ // start from (0, 1), now it's (j, x) every jump the speed can be +1, so the max of x is j+ 1, the max jump is j + 2. so it means the distance from j to i is <= j + 1. 
                    break;
                }
                
                if(f[j][distance - 1] || f[j][distance] || f[j][distance + 1]){
                    f[i][distance] = true;
                }
            }
        }
        
        
        for(boolean b : f[n - 1]){
            if(b){
                return true;
            }
        }
        
        return false;
    }
    
    public static void main(String[] args){
        
        FrogJump sv = new FrogJump();
        
        int[][] inputs = {
            {0,1,3,5,6,8,12,17},
            {0,1,2,3,4,8,9,11}
        };
       
        boolean[] expects = {true, false}; 
        
        for(int i = 0; i < inputs.length; i++){
            if(expects[i]){
                Assert.assertTrue(sv.canCross_BFS(inputs[i]));
                Assert.assertTrue(sv.canCross_DP(inputs[i]));
            }else{
                Assert.assertFalse(sv.canCross_BFS(inputs[i]));
                Assert.assertFalse(sv.canCross_DP(inputs[i]));
            }
        }
        
    }
}
