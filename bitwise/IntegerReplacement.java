/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bitwise;

import java.util.Arrays;
import junit.framework.Assert;
import util.Misc;
import util.TimeCost;

/**
 * _https://www.lintcode.com/problem/1259
 *
 * Given a positive integer n and you can do operations as follow:
 *   1.If n is even, replace n with n/2. 
 *   2.If n is odd, you can replace n with either n + 1 or n - 1.
 *
 * What is the minimum number of replacements needed for n to become 1?
 *
 *
 * Example 1:
 * Input: 8 Output: 3
 * Explanation: 8 -> 4 -> 2 -> 1 Example 2:
 *
 * Input: 7 Output: 4
 * Explanation: 7 -> 8 -> 4 -> 2 -> 1 or 7 -> 6 -> 3 -> 2 -> 1
 * 
 * Thoughts:
 *    if n is even, it mean n likes 0bxx0,  n/2 mean n >> 1; 
 *    if n is odd, it mean n likes 0bxx1, 
 *       when to n + 1 or n - 1,  if it's not decided by the value of n, it need Math.min(f(n + 1), f(n - 1)), f(n+1) 
 *       need pay attention to integer overflow.
 *         
 *       0b11->ob10->0b1,  it's better than 0b11->0b100->0b10->0b1,  here -1 is better than +1
 *       0b111->0b1000->0b1, it's better than 0b111->0b110->0b11->0b1,  here -1 and +1 is same, 
 *         if +1, it need 1 + _the bit digit number of 0b111) = 1+3 steps
 *         if -1, it need  [(the bit digit number of 0b111) - 1] * 2 = 2 * 2.
 *       to 0b1011,  compare 0b1100 with 0b1010,  0b1100 need fewer steps.  
 * 
 *       so if there is two more bit 1 in the right-side, +1 is better. 
 *       
 *       
 * 
 *
 */
public class IntegerReplacement {
    /**
     * 
     * @param n: a positive integer 
     * @return the minimum number of replacements
     */
    public int integerReplacement(int n) {
        assert n < 1;

        int count = 0;

        while( n > 3){
            while( (n & 1) == 0 ){ //
                count++;
                n >>= 1;
            }

            if(n < 4){
                break;
            }
            
            if( ((n >> 1) & 1) == 0 ){ // 01, the best approach is -1
                count += 2;
                n >>= 1;
            }else{        // (11 or 111) ... or (011 or 0111...), the best approach is +1
                count++;

                while( (n & 1) == 1 ){
                    count++;
                    n >>= 1;
                }
                n |= 1;
            }
        }
        
        //when n <= 3 
        count += n - 1;

        return count;
    }
    
    
    /**
     * 
     * @param n: a positive integer 
     * @return the minimum number of replacements
     */
    public int integerReplacement_n(int n) {
        assert n < 1;

        int count = 0;

        while( n > 3){
            if( (n & 1) == 0 ){ // 00 or 10
                count++;
                n >>= 1;
            }else if( (n & 0b11) == 1 ){ // 01
                count += 2;
                n >>= 1;
            }else{       //11
                count++;
                while( (n & 1) == 1 ){
                    count++;
                    n >>= 1;
                }
                n |= 1;
            }
        }
        
        //when n <= 3 
        count += n - 1;

        return count;
    }
    
    public static void main(String[] args){
        IntegerReplacement sv = new IntegerReplacement();
        
       int[][] inputs = {
           //{k, magicalString(k)}
           {1, 0},
           {2, 1}, 
           {3, 2},
           {4, 2},
           {5, 3},
           {6, 3},
           {7, 4},
           {8, 3},
           {9, 4},
           {10, 4},
           {100, 8},
           {1000, 12},
           {10_000, 16},
           {100_000, 21},
           {1000_000, 24},
           {10_000_000, 30},
           {100_000_000, 31},
           {1_000_000_000, 38},
           {999_999, 25},
           {9_999_999, 31},
           {99_999_999, 32},
           {999_999_999, 39},
           {999_999_789, 42},
           {999_999_678, 40},
           {999_999_567, 40},
           {Integer.MAX_VALUE - 1, 32},
           {Integer.MAX_VALUE, 32}
       };
       
       for(int[] p : inputs){
           System.out.println(String.format("k = %d,  magicString(k): %d", p[0], p[1] ));
           
           Assert.assertEquals(p[1], sv.integerReplacement(p[0]));
           Assert.assertEquals(p[1], sv.integerReplacement_n(p[0]));
       }
       
        /** performance test 
       //int[] cases = { 1_000, 10_000, 100_000, 1000_000, 1_000_000, 10_000_000, 100_000_000, 999_999_999, 999_999_789, 999_999_678, 999_999_567};
        
       TimeCost tc = TimeCost.getInstance();
       tc.init();
        
       for(int k = 1_999_999_567; k <= Integer.MAX_VALUE; k++ ){     
           sv.integerReplacement(k);
       }
       System.out.println("\nThe integerReplacement, timeCost:" + tc.getTimeCost());
       
       
       for(int k = 999_999_567; k <= Integer.MAX_VALUE; k++ ){     
           sv.integerReplacement_n(k);
       }
       System.out.println("\nThe integerReplacement, timeCost:" + tc.getTimeCost());
       */
       
       Arrays.sort(inputs, (a, b) -> a[1] == b[1] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]) );
       
       System.out.println(Misc.array2String(inputs));
       int[][] r = new int[0][0];
    }
}
