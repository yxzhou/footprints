/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.sequence;

import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/1177/
 * 
 * Given a positive integer n, return the number of all possible attendance records with length n, which will be
 * regarded as rewardable.
 *
 * A student attendance record is a string that only contains the following three characters:
 * 'A' : Absent. 
 * 'L' : Late. 
 * 'P' : Present. 
 * 
 * A record is regarded as rewardable if it doesn't contain more than one 'A' (absent) and more than two continuous 'L' (late).
 *
 *
 * Notes:
 *   The value of n won't exceed 100,000.
 *   The answer may be very large, return it after mod 1000000007.
 *
 * Example 1:
 * Input：1 
 * Output：3 
 * Explanation：'A','P','L'will be rewarded. 
 * 
 * Example 2:
 * Input：2 
 * Output：8 
 * Explanation： There are eight rewarding records : "PP", "AP", "PA", "LP", "PL", "AL", "LA", "LL".
 * Only "AA" will not be rewarded because it has been absent more than once.
 *
 * Thoughts:
 * m1) Totally it's 2^n - 1 possible attendance records, check one by one, 
 *   
 * Time complexity O(2^n * n)
 *
 * m2) DP, Time complexity O(n * 3)
 * The record can be L or A or P. 
 * The state includes { the last 2 records, is there "A" before the last 2 records } 
 * 
 * which can be stored with 3-bits 
 *   the first bit is 1 when there is "A" before
 *   the second and third bit store the last 2 records. 
 *     if there are "A" in the last 2 records, the A can be stored in the first bit, and it can be replaced with "P", 
 *   so the states change sequence is:
 *   init: 000
 *      -> { 000, 001, 100 }  (n = 1)
 *      -> { {000, 001, 100}, {010, 011, 110}, {100, 101, *}} (n = 2)  * mean no rewards, no state for this. 
 *   ...    
 * 
 * There are 8 states, {000, 001, 010, 011, 100, 101, 110, 111}
 * Define state[2][8], state[0] store the current, state[1] store the next, 
 * when the new record is P
 *   state[1][000] = state[0][000] + state[0][010] 
 *   state[1][010] = state[0][001] + state[0][011] 
 *   state[1][100] = state[0][100] + state[0][110] 
 *   state[1][110] = state[0][101] + state[0][111] 
 * 
 * when the new record is A, 
 *   state[1][100] = state[0][000] + state[0][010] 
 *   state[1][110] = state[0][001] + state[0][011] 
 * 
 * when the new record is L, 
 *   state[1][001] = state[0][000] + state[0][010] 
 *   state[1][011] = state[0][001] 
 *   state[1][101] = state[0][100] + state[0][110] 
 *   state[1][111] = state[0][101]
 * 
 * so:
 *   state[1][000] = state[0][000] + state[0][010] 
 *   state[1][001] = state[0][000] + state[0][010] 
 *   state[1][010] = state[0][001] + state[0][011]
 *   state[1][011] = state[0][001] 
 * 
 *   state[1][100] = state[0][000] + state[0][010] + state[0][100] + state[0][110]
 *   state[1][110] = state[0][001] + state[0][011] + state[0][101] + state[0][111] 
 *   state[1][101] = state[0][100] + state[0][110] 
 *   state[1][011] = state[0][001] 
 * 
 * m3) continue on m2. there are 8 states, {000, 001, 010, 011, 100, 101, 110, 111}, 
 *   tuning 010 and 110.  merge 010 to 000, merge 110 to 100, 
 *   {000, 001, 011, 100, 101, 111}
 * 
 *  so:
 *  when the new record is P
 *   state[1][000] = state[0][000] + state[0][001] + state[0][011] 
 *   state[1][100] = state[0][100] + state[0][101] + state[0][111]
 * 
 *  when the new record is L, 
 *   state[1][001] = state[0][000]
 *   state[1][011] = state[0][001]
 *   state[1][101] = state[0][100]
 *   state[1][111] = state[0][101]
 * 
 * when the new record is A,
 *   state[1][100] = state[0][000] + state[0][001] + state[0][011]
 *   
 */
public class StudentRecordII {
    
    /**
     * 
     * @param n: an integer
     * @return the number of all possible attendance records with length n, which will be regarded as rewardable
     */
    public int checkRecord_m2(int n) {
        if (n < 1) {
            return 0;
        }

        int[][] states = new int[2][8];
        states[0][0] = 1;

        final int BASE = 1000000007;

        int curr = 0;
        int next;
        for (int i = 1; i <= n; i++) {
            next = (curr ^ 1);

            states[next][0] = (states[curr][0] + states[curr][2]) % BASE;
            states[next][1] = states[next][0];
            states[next][2] = (states[curr][1] + states[curr][3]) % BASE;
            states[next][3] = states[curr][1];

            states[next][4] = ((states[curr][0] + states[curr][2]) % BASE + (states[curr][4] + states[curr][6]) % BASE) % BASE;
            states[next][6] = ((states[curr][1] + states[curr][3]) % BASE + (states[curr][5] + states[curr][7]) % BASE) % BASE;

            states[next][5] = (states[curr][4] + states[curr][6]) % BASE;
            states[next][7] = states[curr][5];

            curr = next;
        }

        int result = 0;
        for (int j = 0; j < 8; j++) {
            result = (result + states[curr][j]) % BASE;
        }

        return result;
    }
        
    public int checkRecord_m3(int n) {
        if (n < 1) {
            return 0;
        }

        int[][] states = new int[2][8];
        states[0][0] = 1;

        final int BASE = 1000000007;

        int curr = 0;
        int next;
        for (int i = 1; i <= n; i++) {
            next = (curr ^ 1);

            states[next][0] = ((states[curr][0] + states[curr][1]) % BASE + states[curr][3]) % BASE;
            states[next][4] = (((states[next][0] + states[curr][4]) % BASE + states[curr][5]) % BASE + states[curr][7]) % BASE;
                    
            states[next][1] = states[curr][0];
            states[next][3] = states[curr][1];
            states[next][5] = states[curr][4];
            states[next][7] = states[curr][5];

            curr = next;
        }

        int result = 0;
        for (int j = 0; j < 8; j++) {
            result = (result + states[curr][j]) % BASE;
        }

        return result;
    }
    

    
    
    public static void main(String[] args){
        
        int[][] inputs = {
            {0, 0},
            {1, 3},
            {2, 8}, 
            {3, 19},
            {4, 43},
            {10, 3536},
            {80, 629839153},
            {100, 985598218}
        };
        
        StudentRecordII sv = new StudentRecordII();
        
        for(int[] input : inputs){
            System.out.println(input[0]);
            
            Assert.assertEquals(input[1], sv.checkRecord_m2(input[0]));
            Assert.assertEquals(input[1], sv.checkRecord_m3(input[0]));
        }
    }
}
