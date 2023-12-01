/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.backpack;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.junit.Assert;

/**
 * __https://www.lintcode.com/problem/300/
 *
 * Given an array of meeting time intervals consisting of start and end times [[s1, e1], [s2, e2], ...] (si < ei) and 
 * the value of each meeting. You can only attend a meeting at the same time. Please calculate the most value you can get.
 *
 *  
 * Example #1:
 * Input meeting = [[10, 40], [20, 50], [30, 45], [40, 60]]
 *         value = [3, 6, 2, 4]
 * Output: 7
 * Explanation:  take the 0th and 3th meeting, get 3 + 4 = 7
 * 
 * Example #2:
 * Input meeting = [[10, 20], [20, 30]]
 *         value = [2, 4]
 * Output: 6
 * Explanation:  take the 0th and 1th meeting, get 2 + 4 = 6
 * 
 * 
 * Notes: 
 *   (0,8),(8,10) is not conflict at 8
 *   0 < len(meeting) = len(value) < 10_000
 *   0 < meeting[i][0] < meeting[i][1] < 50_000
 *   0 < value[i] < 10_000
 * 
 * 
 * Thoughts:
 *   Define n as the number of meetings. 
 * 
 *   S1: combination
 *   You can only attend a meeting at the same time. the value you can get is from meetings' combination.
 *   
 *   S2: backpack problem / knapsack problem, DP
 *   All meetings are in a timeline.  
 *   Define int[] max, max[i] is the most value you can get before time i.
 * 
 *  
 * 
 */
public class MeetingRoomIV {
    
    /**
     * 
     * @param meetings
     * @param values
     * @return the most value you can get
     */
    public int maxValue(int[][] meetings, int[] values){
        if(meetings == null || meetings.length == 0){
            return 0;
        }
        
        int n = meetings.length;
        TreeMap<Integer, List<Integer>> endTimes = new TreeMap<>();//map, endTime to index of meetings
        for(int i = 0; i < n; i++){
            endTimes.computeIfAbsent(meetings[i][1], o -> new ArrayList<>()).add(i);
        }
        
        //
        final int maxEndTime = endTimes.lastKey();
        int[] f = new int[maxEndTime + 1]; //default all are 0
        
        for(int endTime = 1; endTime <= maxEndTime; endTime++){
            
            f[endTime] = f[endTime - 1];
            
            if(endTimes.containsKey(endTime)){
                for(int i : endTimes.get(endTime)){
                    f[endTime] = Math.max(f[endTime], f[meetings[i][0]] + values[i] );
                } 
            }

        }
        
        return f[maxEndTime];
    }
    
    
    public static void main(String[] args){
        MeetingRoomIV sv = new MeetingRoomIV();
        
        int[][][][] inputs = {
            {
                {{10, 40}, {20, 50}, {30, 45}, {40, 60}},
                {{3, 6, 2, 4}},
                {{7}}
            },
            {
                {{10, 20}, {20, 30}},
                {{2, 4}},
                {{6}}
            }
        };
        
        for(int[][][] input : inputs){
            Assert.assertEquals(0, input[2][0][0], sv.maxValue(input[0], input[1][0]));
        }
        
    }
}
