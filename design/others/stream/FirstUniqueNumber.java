/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design.others.stream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * _https://www.lintcode.com/problem/685
 * 
 * Given a continuous stream of data, write a function that returns the first unique number (including the last number)
 * when the terminating number arrives. If the terminating number is not found, return -1.
 *
 * Example1
 * Input: [1, 2, 2, 1, 3, 4, 4, 5, 6] 5 
 * Output: 3 
 * Explanation: the 3 is first unique number by 5  (by means before and include 5)
 * 
 * Example2
 * Input: [1, 2, 2, 1, 3, 4, 4, 5, 6] 7 
 * Output: -1 
 * Explanation: not found the terminating number 7 in the input stream 
 * 
 * Example3
 * Input: [1, 2, 2, 1, 3, 4] 3 
 * Output: 3
 * Explanation: not found any unique number by 3
 * 
 * Solutions:
 *   Two pass
 *   m1) HashMap<value, count>
 * 
 *   One pass,  
 *   Because the input is a data stream, so it is better to One Pass. see FirstUniqueNumberII
 *   m2) LinkedList + HashMap 
 *   m3) LinkedHashSet
 *   And because it need check if it's unique, it has to cache the inputted numbers, the best way is BloomFilter
 * 
 *   
 */
public class FirstUniqueNumber {
    
    /**
     * @param nums: a continuous stream of numbers
     * @param number: a number
     * @return the first unique number
     */
    public int firstUniqueNumber_onepass(int[] nums, int number) {

        LinkedHashSet<Integer> uniques = new LinkedHashSet<>();  //<value>
        HashSet<Integer> visited = new HashSet<>();

        for(int num : nums){
            if(!visited.contains(num) ){
                visited.add(num);
                uniques.add(num);
            } else if(uniques.contains(num) ) {
                uniques.remove(num);
            }

            if(num == number){ // terminating number
                return uniques.isEmpty()? -1 : uniques.iterator().next();
            }
        }

        return -1;
    }
    
    
    /**
     * @param nums: a continuous stream of numbers
     * @param number: a number
     * @return the first unique number
     */
    public int firstUniqueNumber_twopass(int[] nums, int number) {

        int n = nums.length;

        Map<Integer, Integer> counts = new HashMap<>();  //<value, count>

        for(int i = 0; i < n; i++){
            counts.put(nums[i], counts.getOrDefault(nums[i], 0) + 1);

            if(nums[i] == number){ // terminating number
                for(int j = 0; j <= i; j++){
                    if(counts.get(nums[j]) == 1 ){
                        return nums[j];
                    }
                }
            }
        }

        return -1;
    }
}
