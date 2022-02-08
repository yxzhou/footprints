/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dfsbfs.permutationAndCombination;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/862
 * 
 * Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits. There is no
 * limit on how many times a digit can be reused.
 *
 * You may assume the given input string is always valid. For example, "01:34", "12:09" are valid. "1:34", "12:9" are
 * invalid.
 *
 * Example 1:
 * Input: "19:34" 
 * Output: "19:39" 
 * Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, not 19:33. 
 * 
 * Example 2:
 * Input: "23:59" 
 * Output: "22:22" 
 * 
 * 
 * Thoughts:
 *   m1) find all permutation, exclude the invalid on Hours and Minutes. find the next closest.  
 *   m2) calculate the next bigger. 
 *       from left to right, try to find the bigger digit, and replace the digits on the left with the smallest digit. 
 */
public class NextClosestTime {
    
    /**
     * @param time: the given time
     * @return the next closest time
     */
    public String nextClosestTime(String time) {
        int[] result = new int[4];
        result[0] = time.charAt(0) - '0';
        result[1] = time.charAt(1) - '0';
        result[2] = time.charAt(3) - '0';
        result[3] = time.charAt(4) - '0';
        
        int[] sorted = Arrays.copyOf(result, 4);
        Arrays.sort(sorted);

        int i = 3;
        outer: for( ; i >= 0; i--){
            
            for(int d : sorted){
                if(d <= result[i] || (i == 2 && d > 5) || (i == 1 && result[0] == 2 && d > 4) || (i == 0 && d > 2) ){
                    continue;  
                }

                result[i] = d;
                break outer; 
            }
        }

        for( i++; i < 4; i++){
            result[i] = sorted[0];
        }

        return String.format("%d%d:%d%d", result[0], result[1], result[2], result[3]);
    }
    
    public static void main(String[] args){
        
        String[][] inputs = {
            {"19:34" , "19:39"},
            {"19:39", "11:11"},
            {"11:11", "11:11"},
            {"23:59", "22:22"},
            {"09:59", "00:00"},
            {"22:33", "23:22"},
            {"21:19", "21:21"},
        };
        
        NextClosestTime sv = new NextClosestTime();
        
        for(String[] input : inputs){
            System.out.println(String.format("\n%s -> %s", input[0], input[1]));
            
            Assert.assertEquals(input[1], sv.nextClosestTime(input[0]));
        }
        
    }
    
}
