package dp.backpack;

import junit.framework.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/668
 * 
 * In the computer world, use restricted resource you have to generate maximum benefit is what we always want to pursue.
 *
 * For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, there is an array with strings
 * consisting of only 0s and 1s.
 *
 * Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s. Each 0 and 1 can
 * be used at most once.
 *
 * Note: 
 *   The given numbers of 0s and 1s will both not exceed 100 
 *   The size of given string array won't exceed 600.
 *
 *
 * Example 1: 
 * Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3 
 * Output: 4 
 * Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”
 *
 * Example 2: 
 * Input: Array = {"10", "0", "1"}, m = 1, n = 1 
 * Output: 2 
 * Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
 *
 */

public class OnesAndZeros {
    
    /**
     * @param strs: an array with strings include only 0 and 1
     * @param m: An integer
     * @param n: An integer
     * @return find the maximum number of strings
     */
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] counts = new int[m + 1][n + 1];
        counts[0][0] = 1;

        int c0; //count of 0
        int c1; //count of 1
        int len;
        int pre;
        for(String str : strs){
            len = str.length();
            c0 = 0;
            for(int i = 0; i < len; i++){
                if(str.charAt(i) == '0'){
                    c0++;
                }
            }
            c1 = len - c0;

            if(c0 > m || c1 > n){
                continue;
            }

            for(int z = m; z >= c0; z--){
                for(int o = n; o >= c1; o--){
                    if( (pre = counts[z - c0][o - c1]) > 0 ){
                        counts[z][o] = Math.max(counts[z][o], pre + 1);
                    }
                }
            }
        }

        int max = 1;
        for(int z = m; z >= 0; z--){
            for(int o = n; o >= 0; o--){
                max = Math.max(max, counts[z][o]);
            }
        }

        return max - 1;
    }
    
    
    public static void main(String[] args) {
        String[][][] inputs = {
            //{ strs, {m, n}, {expect}}
            {
                {"10", "0", "1"},
                {"1", "1"},
                {"2"}
            },
            {
                {"10", "0001", "111001", "1", "0"},
                {"5", "3"},
                {"4"}
            },
            {
                {"10", "0001", "111001", "1", "0"},
                {"7", "7"},
                {"5"}
            },
            {
                {"0","11","1000","01","0","101","1","1","1","0","0","0","0","1","0","0110101","0","11","01","00","01111","0011","1","1000","0","11101","1","0","10","0111"},
                {"9", "80"},
                {"17"}
            },
        };
        
        OnesAndZeros sv = new OnesAndZeros();
        
        for(String[][] input : inputs){
            System.out.println(String.format("\n{%s}, m = %d, n = %d", Misc.array2String(input[0]), Integer.parseInt(input[1][0]), Integer.parseInt(input[1][1]) ));
            
            Assert.assertEquals(Integer.parseInt(input[2][0]), sv.findMaxForm(input[0], Integer.parseInt(input[1][0]), Integer.parseInt(input[1][1]) ));
        }

    }

}

