/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bitwise;

import java.util.Arrays;
import org.junit.Assert;
import util.Misc;

/**
 * _https://www.lintcode.com/problem/84
 * 
 * Given 2*n + 2 numbers, every numbers occurs twice except two, find them.
 *
 * Example 1: 
 * Input: array = [1,2,2,3,4,4,5,3] 
 * Output: [1,5] 
 *
 * Example 2:
 * Input: array = [1,1,2,3,4,4] 
 * Output: [2,3] 
 *
 * Example 3
 * given the array [2, 4, 6, 8, 10, 2, 6, 10], 
 * return 4and 8. The order does not matter.
 * 
 * Challenge Can you do this in linear time and constant space?
 *   O(n) time, O(1) extra space.
 * 
 */
public class SingleNumberIII {

    public int[] twoNumber_n(int[] nums) {
        if (null == nums || 2 > nums.length) {
            return new int[0];  //exception, error code
        }

        //1 get the xor
        int x = 0; //xor
        for (int n : nums) {
            x ^= n;
        }

        //2 check the xor from right to left, get the first 1 in the xor
        int i = 0;
        for (; i < 32; i++) {
            if (((x >> i) & 1) == 1) {
                break;
            }
        }

        //3 with the 1, it can diff with the special two, and make A in 2 group
        int r = 0;  //default both are 0
        for (int n : nums) {
            if (((n >> i) & 1) == 1) {
                r ^= n;
            }
        }

        return new int[]{r, x^r};
    }

    
    public static void main(String[] args){
        int[][][] inputs = {
            {
                {1,2,2,3,4,4,5,3},
                {1,5}
            },
            {
                {1,1,2,3,4,4},
                {2,3}
            },
            {
                {2, 4, 6, 8, 10, 2, 6, 10},
                {4, 8}
            }
                    
        };
        
        SingleNumberIII sv = new SingleNumberIII();
        
        int[] result;
        for(int[][] input : inputs){
            
            System.out.println(String.format("\n%s\n%s", Arrays.toString(input[0]), Arrays.toString(input[1]) ));
                    
            result = sv.twoNumber_n(input[0]);
            Arrays.sort(result);
            Assert.assertArrayEquals(input[1], result);
        }
        

    }
}
