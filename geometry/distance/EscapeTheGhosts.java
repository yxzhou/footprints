/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package geometry.distance;

/**
 * _https://www.lintcode.com/problem/1027
 * 
 * You are playing a simplified game. You start at the point (0, 0), and your destination is (target[0], target[1]). 
 * There are several ghosts on the map, the i-th ghost starts at (ghosts[i][0], ghosts[i][1]).
 *
 * Each turn, you and all ghosts simultaneously may move in one of 4 cardinal directions: north, east, west, or south,
 * going from the previous point to a new point 1 unit of distance away.
 *
 * You escape if and only if you can reach the target before any ghost reaches you (for any given moves the ghosts may
 * take). If you reach any square (including the target) at the same time as a ghost, it doesn't count as an escape.
 *
 * Return true if and only if it is possible to escape, otherwise return false.
 *
 * Constraints:
 * All points have coordinates with absolute value <= 10000. 
 * The number of ghosts will not exceed 100. 
 * 
 * Example 1:
 * Input: ghosts = [[1, 0], [0, 3]], target = [0, 1] 
 * Output: true 
 * Explanation: You can directly reach the destination (0, 1) at time 1, while the ghosts located at (1, 0) or (0, 3) 
 * have no way to catch up with you. 
 * 
 * Example 2:
 * Input: ghosts = [[1, 0]], target = [2, 0] 
 * Output: false 
 * Explanation: You need to reach the destination (2, 0), but the ghost at (1, 0) lies between you and the destination. 
 * 
 * Example 3:
 * Input: ghosts = [[2, 0]], target = [1, 0] 
 * Output: false 
 * Explanation: The ghost can reach the target at the same time as you.
 * 
 * Thoughts:
 *   1) Because the move is in one of 4 cardinal directions: north, east, west, or south. So the distance from 
 *   start(s0, s1) to target(t0, t1) is Math.abs(s0 - t0) + Math.abs(s1 - t1)
 *   2) escape only when you move to the target earlier than the ghosts. 
 * 
 */
public class EscapeTheGhosts {

    /**
     * 
     * @param ghosts: a 2D integer array
     * @param target: a integer array
     * @return true if and only if it is possible to escape, otherwise return false
     */
    public boolean escapeGhosts(int[][] ghosts, int[] target) {
        
        int t = Math.abs(target[0]) + Math.abs(target[1]);

        for(int[] ghost : ghosts){
            if(Math.abs(ghost[0] - target[0]) + Math.abs(ghost[1] - target[1]) <= t ){
                return false;
            }
        }

        return true;
    }
    
}
