/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sweepLine;

import java.util.Arrays;
import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/1291
 *
 * The sports meeting is coming. As the cheerleader, Xiaohong wants to cheer for the students in each event. Xiaohong
 * needs to observe the following rules:
 *   It is necessary to cheer for each sports event more than half of the duration of the event, and each event can only
 * be refueled once 
 *   The start and end time of sports events are all integers, and the time Xiaohong chooses to enter and leave must 
 * also be integers 
 *   Regardless of the time spent traveling to and from various sports venues 
 * 
 * Given the start and end time of each sport event, Xiaohong wondered if she could cheer for her classmates in every 
 * sport events? 
 * If she can do it, return 1, otherwise return -1.
 *
 * Notes:
 *   It is guaranteed that the number of sports events does not exceed 10. 
 *   If the duration of a project is 2, more than half of the duration is 2; 
 *   if the duration of a project is 3, more than half of the duration is 2; 
 *   if the duration of a project is 4, more than half of the duration is 3.
 *
 * Example 1 
 * input：[[3,10],[1,5],[4,6]] 
 * output：1 
 * explain：
 *   time 1-3 cheer in the 2th event，time 3-4 in free，time 4-6 cheer in the 3th event，time 6-10 cheer in the 1th event
 * 
 * Thoughts
 *   see the following cases:
 *   [[5,10],[6,20]] -- the more-than-half is [8, 14], so check sequence is [[5,10],[6,20]]
 *   [[5,10],[8,9]]  -- the more-than-half is [8, 9], so check sequence is [[5,10],[8,9]]
 *   [[5,30],[6,12],[7,9]] -- the more-than-half is [18, 10, 9], so check sequence is [[7,9],[6,12],[5,30]]
 * 
 * 
 * 
 */

public class SportsMeeting {
    /**
     * greedy
     * 
     * @param events: the start and end time
     * @return: if there has a solution return 1, otherwise return -1.
     */
    public int CheerAll(int[][] events) {
        if(events == null || events.length < 2){
            return 1;
        }

        Arrays.sort(events, (a, b) -> Integer.compare((a[1] + a[0] + 2) / 2 , (b[1] + b[0] + 2) / 2) );

        int x = 0;
        for(int[] event : events){
            if(x <= event[0]){
                x = 1 + event[0] + (event[1] - event[0]) / 2;
            }else{ // event[0] < x
                x = x + 1 + (event[1] - event[0]) / 2; 
            }
            
            if(x > event[1]){
                return -1;
            }
        }

        return 1;
    }
    
    /**
     * greedy
     * 
     * @param events: the start and end time
     * @return if there has a solution return 1, otherwise return -1.
     */
    public int CheerAll_2(int[][] events) {
        if(events == null || events.length < 2){
            return 1;
        }

        int n = events.length;

        int[][] sports = new int[n][2];

        for (int i = 0; i < n; i++) {
            sports[i][0] = i;
            sports[i][1] = 1 + events[i][0] + (events[i][1] - events[i][0]) / 2;
        }

        Arrays.sort(sports, (a, b) -> a[1] - b[1]);

        int base = 0;
        int[] event;
        for (int[] sport : sports) {
            event = events[sport[0]];
            base = Math.max(base - event[0], 0) + sport[1];

            if (base > event[1]) {
                return -1;
            }
        }

        return 1;
    }
    
    public static void main(String[] args){
        
        int[][][][] inputs = {
            {
                {{3,10},{1,5},{4,6}},
                {{1}}
            },
            {
                {{1,5},{1,5}},
                {{-1}}
            },
            {
                {{5,10},{6,20}},
                {{1}}
            },
            {
                {{5,10},{8,9}},
                {{1}}
            },
            {
                {{5,30},{6,12},{7,9}},
                {{-1}}
            },
            {
                {{5,30},{6,13},{7,9}},
                {{1}}
            }
            
        };
        
        SportsMeeting sv = new SportsMeeting();
        
        for(int[][][] input : inputs){
            System.out.println( Misc.array2String(input[0], true) );
            
            Assert.assertEquals(input[1][0][0], sv.CheerAll(input[0]));
            Assert.assertEquals(input[1][0][0], sv.CheerAll_2(input[0]));
        }
        
    }
}
