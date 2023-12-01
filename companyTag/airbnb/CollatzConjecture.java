/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;

/**
 *
 * If a number is odd, the next transform is 3*n+1 
 * If a number is even, the next transform is n/2 
 * The number is finally transformed into 1. 
 * The step is how many transforms needed for a number turned into 1. 
 * 
 * Given an integer n, output the max steps of transform number in [1, n] into 1.
 *
 * Thoughts:  
 *  y = 2x ->  y = x
 *  y = 2x + 1 ->  y = 6x + 4 = 2* (3x + 2) = 2 * (n + n / 2 + 1)
 * 
 */
public class CollatzConjecture {
    

    public int findLongestSteps(int num) {
        if(num < 1){
            return 0;
        }
        
        Map<Integer, Integer> cache = new HashMap<>();
        
        int max = 0;
        
        for(int y = 1; y <= num; y++){
            max = Math.max(max, findSteps(y, cache));
        }
        
        return max;
    }
    
    private int findSteps(int num, Map<Integer, Integer> cache){
        if(num == 1){
            return 1;
        }
        
        if(cache.containsKey(num)){
            return cache.get(num);
        }

        int count;
        
        if( (num & 1) == 0 ){
            count = 1 + findSteps( num >> 1, cache );
        }else{
            count = 2 + findSteps( num + (num >> 1) + 1, cache );
        }
        
        cache.put(num, count);
        return count;
    }
    
    
    public static void main(String[] args){
        
        int[][] inputs = {
            {
                0,
                0
            },
            {
                1,
                1
            },
            {
                2,
                2
            },
            {
                3,
                8
            },
            {
                4,
                8
            },
            {
                10,
                20
            },
            {
                37,
                112
            },
            {
                101,
                119
            }
        };
        
        CollatzConjecture sv = new CollatzConjecture();
        
//        for(int y = 1; y <= 10; y++){
//            System.out.println(String.format("num = %d, maxStep = %d", y, sv.findLongestSteps(y)));
//        }
        
        for(int[] input : inputs){
            Assert.assertEquals(String.format("num = %d ", input[0]), input[1], sv.findLongestSteps(input[0]));
        }
        
    }
    
}
