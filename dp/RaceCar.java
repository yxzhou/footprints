/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Your car starts at position 0 and speed +1 on an infinite number line. (Your car can go into negative positions.)
 * Your car drives automatically according to a sequence of instructions A (accelerate) and R (reverse).
 *   When you get an instruction "A", your car does the following: position += speed, speed *= 2.
 *   When you get an instruction "R", your car does the following: if your speed is positive then speed = -1 , otherwise speed = 1. (Your position stays the same.)
 * For example, after commands "AAR", your car goes to positions 0->1->3->3, and your speed goes to 1->2->4->-1.
 * 
 * Now for some target position, say the length of the shortest sequence of instructions to get there.
 * 
 * Notes: 1 <= target <= 10000
 * 
 * Example 1:
 * Input:  target = 3
 * Output: 2
 * Explanation:  The shortest instruction sequence is "AA". Your position goes from 0->1->3.
 * 
 * Example 2:
 * Input:  target = 6
 * Output: 5
 * Explanation:  The shortest instruction sequence is "AAARA". Your position goes from 0->1->3->7->7->6.
 * 
 *  
 * 
 */
public class RaceCar {
    /**
     * @param target: 
     * @return the shortest sequence of instructions to get target
     */
    public int racecar_DFS(int target) {
        int ceil = (1 << ((int)Math.log(target) + 1));
        
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        String str = 0 + " " + 1;
        queue.add(str);
        visited.add(str);

        int count = 0;
        int p; //position
        int s; //speed
        String[] pair;
        while (!queue.isEmpty()) {
            count++;

            for (int k = queue.size(); k > 0; k--) {
                pair = queue.poll().split(" ");
                p = Integer.valueOf(pair[0]);
                s = Integer.valueOf(pair[1]);

                //A
                if (p + s == target) {
                    return count;
                }
                str = (p + s) + " " + (s * 2);
                if (!visited.contains(str) && (p + s) > 0 && (p + s) < ceil) {
                    queue.add(str);
                    visited.add(str);
                }

                //R
                str = (p) + " " + (s > 0 ? -1 : 1);
                if (!visited.contains(str)) {
                    queue.add(str);
                    visited.add(str);
                }
            }
        }

        return -1;
    }
    
    public int racecar_DP_2(int target) {
        int[] dp = new int[target + 3];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 4;
        
        int k;
        int left;
        int right;
        for(int t = 3; t <= target; t++){
            k = 32 - Integer.numberOfLeadingZeros(t);
            right = (1 << k);
            left = ( 1 << (k - 1));
            
            if(t == right - 1){
                dp[t] = k;
                continue;
            }
         
            for(int j = 0; j < k - 1; j++){

                dp[t] = Math.min(dp[t], dp[t - left + (1<<j)] + k - 1 + j + 2);
            }
            
            if(right - t < t){
                dp[t] = Math.min(dp[t], dp[right - t] + k + 1);
            }
            
    System.out.println(" t=" + t + " " + dp[t]);
        }
        
        return dp[target];
    }
    
    public int racecar_DP(int target) {
        if(target < 2){
            return target;
        }

        int t = (int)Math.log(target);
        int left = (1 << t);
        int right = (left << 1);
        int[] f = new int[right];
        Arrays.fill(f, Integer.MAX_VALUE);
        
        f[left - 1] = t;
        f[right - 1] = t + 1;

        f[left] = 2;
        for(int p = left, s = 1; p < right; s <<= 1, p += s){
            f[p] = f[p - s] + 1;
        }

        f[right - 1] = Math.min(f[right - 1], f[right] + 2) ;
        for(int p = right - 3, s = -2; p > left; s <<= 1, p += s){
            f[p] = Math.min(f[p], f[p - s] + 1);
        }

//        for(int p = ){
//            
//        }

        //return f[target - n + 1];
        return -1;
    }
    
    public static void main(String[] args){
        /**
         * test 
         */
        int n = 0xff;
        int k = Integer.numberOfLeadingZeros(n);
        System.out.println(" numberOfLeadingZeros(0xff): " +  k + " \t" +  (n == (1 << (32 - k)) - 1));
        
        System.out.println(" Math.log(0xff): " +  (int)Math.log(n) );
        System.out.println(" (32 - Integer.numberOfLeadingZeros((0xff)): " +  (32 - Integer.numberOfLeadingZeros(n)) );
        
        
        RaceCar sv = new RaceCar();
        
        sv.racecar_DP_2(15);
        
    }
}
