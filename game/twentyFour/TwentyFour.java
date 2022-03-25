/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.twentyFour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/739
 *
 * You have 4 cards each containing a number from 1 to 9. You need to compute whether they could operated through *, /,
 * +, -, (, ) to get the value of 24.
 * 
 * Notes:
 * 1 The division operator / represents real division, not integer division. so 4 / (1 - 2/3) = 12. 
 * 2 Every operation done is between two numbers. In particular, we cannot use - as a unary operator. For example, 
 *   with [1, 1, 1, 1] as input, the expression -1 - 1 - 1 - 1 is not allowed. 
 * 3 You cannot concatenate numbers together. For example, if the input is [1, 2, 1, 2], we cannot write this as 12 + 12.
 *
 * Example 1:
 * Input：[1, 4, 8, 7] 
 * Output：true 
 * Explanation：8 * （7 - 4） * 1 = 24 Example 2:
 *
 * Input：[1, 1, 1, 2] 
 * Output：false 
 * Explanation：no way to get 24 Example 3:
 *
 * Input：[3, 3, 8, 8] 
 * Output：true 
 * Explanation：8 / ( 3 - 8 / 3) = 24
 *
 * Thoughts:
 *   Define f(a, b)  as the expression permutations of 2 numbers 
 *   Define f(a, b, c) as the expression permutations of 3 numbers, 
 *   Define f(a, b, c, d) as the expression permutations of 4 numbers, 
 *   
 *   To f(a, b), the expression permutations are:
 *     a+b, a-b, b-a, a*b, a/b, b/a.
 * 
 *   f(a, b, c) = f(a, f(b, c)) + f(b, f(a, c)) + f(c, f(a, b))
 *   
 *   f(a, b, c, d) = f(f(a, b), f(c, d)) + f(f(a, c), f(b, d)) + f(f(a, d), f(b, c)) +
 *                   f(a, f(b, c, d)) + f(b, f(a, c, d)) + f(c, f(a, b, d)) + f(d, f(a, b, c))
 * 
 *   Need pay attention to:
 *   1 The input are 4 number from 1 to 9, with 3 times operator with "+,-,*,/,(,)", the value is double, so no overflow
 *   2 Before a/b or b/a, it make sure a and b are not zero 
 *   3 To a/b, ib is double, how to check if b is zero?  floating point comparisons.
 *     Same, Double cannot be used as key in HashMap. It means here it can't get less time complexity with more space.
 * 
 * 
 */
public class TwentyFour {
    /**
     * @param nums: 4 cards
     * @return: whether they could get the value of 24
     */

    Map<String, List<Double>> cache = new HashMap<>(); 
    final static double EPSILON = 0.000001d;

    public boolean compute24(double[] nums) {

        // case 1, f(a, b, c, d) = f(f(a, p), f(x, y)),  (a, p) is (a, b) or (a, c) or (a, d) 
        List<Double> second;
        List<Double> result;
        
        for(int b = 1; b < 4; b++){
            swap(nums, 1, b);
            
            second = calculate(nums[2], nums[3]);
            for(double ap : calculate(nums[0], nums[1]) ){
                for(double xy : second){
                    result = calculate(ap, xy);
                    
                    for(double r : result){ 
                        if(Math.abs(r - 24) < EPSILON){ 
                            return true;
                        }
                    }
                }
            }

            swap(nums, 1, b);
        } 

        // case 2, f(a, b, c, d) = f(p, f(x, y, z)),  p is one of [a, b, c, d]
        for(int a = 0; a < 4; a++){
            swap(nums, 0, a);
      
            second = new ArrayList<>();
            for(int b = 1; b < 4; b++){
                swap(nums, 1, b);
                
                for(double yz : calculate(nums[2], nums[3]) ){
                    second.addAll(calculate(nums[1], yz));
                }
                
                swap(nums, 1, b);
            }
            
            for(double xyz : second){
                result = calculate(nums[0], xyz);

                for(double r : result){
                    if(Math.abs(r - 24) < EPSILON){
                        return true;
                    }
                }
            }

            swap(nums, 0, a);
        } 

        return false;
    }

    private List<Double> calculate(double x, double y){
        if(x < y){
            return calculate(y, x);
        }

        String key = x + " " + y;  
        if(cache.containsKey(key)){
            return cache.get(key);
        }

        List<Double> result = new ArrayList<>(6);
        cache.put(key, result);

        //+ - * /
        result.add(x + y);
        result.add(x * y);

        result.add(x - y);
        result.add(y - x);
        
        if(Math.abs(y) > EPSILON){
            result.add(x / y);
        }

        if(Math.abs(x) > EPSILON){
            result.add(y / x);
        }

        return result;
    }
    
    private void swap(double[] nums, int i, int j){
        if(i != j){
            double tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }
    
    public static void main(String[] args){
        double[][][] inputs = {
            //{nums, 4 numbers}, {expect, 1-true, 0-false}
            {{1, 4, 8, 7}, {1}},  // 8 * (7 - 4) * 1 = 24 
            {{1, 1, 1, 2}, {0}},
            {{3, 3, 8, 8}, {1}},  // 8 / (3 - 8 / 3) = 24
            {{3, 2, 5, 4}, {1}},  // (3 + 5 - 2) * 4 = 24
            {{5, 2, 7, 8}, {1}},  // (5 * 2 - 7) * 8 = 24
            {{1, 4, 8, 7}, {1}},  // (7 - 4) * 8 * 1 = 24
            {{8, 9, 8, 9}, {0}},
            {{1, 1, 1, 1}, {0}},
        };
        
        TwentyFour sv = new TwentyFour();
        
        for(double[][] input : inputs){
            System.out.println(Arrays.toString(input[0]));
            
            Assert.assertEquals(input[1][0], sv.compute24(input[0])? 1d : 0d);
        }
        
    }
    
}
