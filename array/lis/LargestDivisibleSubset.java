/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.lis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * Given a set of distinct positive integers, find the largest subset which has the most elements, and every pair of two
 * elements (Si, Sj) in this subset satisfies: Si % Sj = 0 or Sj % Si = 0.
 *
 * If there are multiple solutions, return any subset is fine. 
 * 1≤len(nums)≤50000
 *
 * Example 1: Input: nums = [1,2,3], Output: [1,2] or [1,3]
 *
 * Example 2: Input: nums = [1,2,4,8], Output: [1,2,4,8]
 * 
 * 
 *
 */
public class LargestDivisibleSubset {

    /**
     * @param nums: a set of distinct positive integers
     * @return: the largest subset
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.EMPTY_LIST;
        }

        Arrays.sort(nums);

        int n = nums.length;
        int[] paths = new int[n]; //paths[i] is the previous element
        int[] depths = new int[n]; 
        depths[0] = 0;
        paths[0] = -1;

        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && depths[i] < depths[j] + 1) {
                    depths[i] = depths[j] + 1;
                    paths[i] = j;
                }
            }

            if (depths[max] < depths[i]) {
                max = i;
            }
        }

        Integer[] result = new Integer[depths[max] + 1];
        for (int k = max; k >= 0;) {
            result[depths[k]] = nums[k];
            k = paths[k];
        }

        return new ArrayList<>(Arrays.asList(result));
    }
}
