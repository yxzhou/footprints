/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

import java.util.HashMap;
import java.util.Map;

/**
 * _https://www.lintcode.com/problem/1103
 * 
 * Given an integer array nums. You need to split nums into several (at least 1) subsequences, where each subsequence
 * contains at least 3 consecutive integers.
 *
 * Return whether you can make such a split.
 *
 * Constraints:
 *   nums.length will be in the range of [1, 10000]. 
 *   nums has been sorted in ascending order and may contain duplicates.
 *   If you can make such a split, each element of nums must and can only exist in one of subsequences. 
 *   A legitimate subsequence can only consist of consecutive elements and cannot contain duplicate elements. 
 * 
 * Example 1:
 * Input: [1,2,3,3,4,5] 
 * Output: True 
 * Explanation: You can split them into two subsequences: [1, 2, 3] [3, 4, 5] 
 * 
 * Example 2:
 * Input: [1,2,3,3,4,4,5,5] 
 * Output: True 
 * Explanation: You can split them into two subsequences: [1, 2, 3, 4, 5] [3, 4, 5] 
 * 
 * Example 3:
 * Input: [1,2,3,4,4,5] 
 * Output: False 
 * Explanation: We can't split them into several legal subsequences.
 * 
 * Thoughts:
 *   If it's one subsequence, it's like [2, 3, 4]
 *   If it's two subsequences, it's like [1,2,3,3,4,5] 
 *   If it's three subsequences, it's like [3,3,3,4,4,4,5,5,5]
 * 
 *  Define n as the length of input array. 
 *  m1) Sorted and check,  
 *      Time O(n * logn ), Space O(1)
 *  m2) Map<value, count>, check from minimum,   
 *      Time O(n), Space O(n)
 * 
 */
public class SplitIntoConsecutiveSequences {
    /**
     *
     * @param nums: a list of integers
     * @return whether it can make such a split
     */
    public boolean isPossible(int[] nums) {
        if (nums == null || nums.length < 6) {
            return false;
        }

        Map<Integer, Integer> counts = new HashMap<>();
        int min = Integer.MAX_VALUE;
        for (int x : nums) {
            counts.put(x, counts.getOrDefault(x, 0) + 1);

            min = Math.min(min, x);
        }

        int low;
        int high = Integer.MAX_VALUE;
        Integer pre; //count
        Integer curr; //count 

        while (min < high) {
            for (low = min, high = min, pre = 0; (curr = counts.get(high)) != null && curr >= pre; high++) {
                pre = curr;

                if (curr == 1) {
                    min++;
                } else {
                    counts.put(high, curr - 1);
                }
            }

            if (high - low < 3) {
                return false;
            }
        }

        return true;
    }
}
