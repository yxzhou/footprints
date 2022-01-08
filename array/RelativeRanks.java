/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * _https://www.lintcode.com/problem/1200
 * 
 * Given scores of N athletes, find their relative ranks and the people with the top three highest scores, who will be
 * awarded medals: "Gold Medal", "Silver Medal" and "Bronze Medal".
 * 
 * Notes:
 *   N is a positive integer and won't exceed 10,000.
 *   All the scores of athletes are guaranteed to be unique.
 * 
 * Example 1:
 * Input: [5, 4, 3, 2, 1]
 * Output: ["Gold Medal", "Silver Medal", "Bronze Medal", "4", "5"]
 * Explanation: The first three athletes got the top three highest scores, so they got "Gold Medal", "Silver Medal" and 
 * "Bronze Medal". For the right two athletes, you just need to output their relative ranks according to their scores.
 * 
 */
public class RelativeRanks {
    /**
     * @param nums: List[int]
     * @return: return List[str]
     */
    public String[] findRelativeRanks(int[] nums) {
        if(nums == null){
            return new String[0];
        }

        int n = nums.length;
        int[] arr = new int[n];
        System.arraycopy(nums, 0, arr, 0, n);

        Arrays.sort(arr);

        Map<Integer, Integer> ranks = new HashMap<>();
        for(int i = 0, j = n; i < n; i++, j--){
            ranks.put(arr[i], j);
        }

        String[] result = new String[n];
        int rank;
        for(int i = 0; i < n; i++){
            rank = ranks.get(nums[i]);
            switch(rank){
                case 1:
                    result[i] = "Gold Medal";
                    break;
                case 2:
                    result[i] = "Silver Medal";
                    break;
                case 3:
                    result[i] = "Bronze Medal";
                    break;
                default:
                    result[i] = String.valueOf(rank);
                    break;

            }
        }

        return result;
    }
}
