/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.subArray;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
 * The length of the given binary array will not exceed 50,000.
 * 
 * Example 1:
 * Input: [0,1]
 * Output: 2
 * Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.
 * 
 * Example 2:
 * Input: [0,1,0]
 * Output: 2
 * Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
 * 
 */
public class ContiguousArray {
    /**
     * @param nums: a binary array
     * @return: the maximum length of a contiguous subarray
     */
    public int findMaxLength(int[] nums) {
        int sum = 0; //prefix sum
        Map<Integer, Integer> map = new HashMap<>(); //<sum, the first position>
        map.put(0, -1);

        int max = 0; // the maximum length of subarray
        for(int i = 0; i < nums.length; i++){
            sum += (nums[i] == 0? -1 : 1 );

            if(map.containsKey(sum)){
                max = Math.max(max, i - map.get(sum));
            }else{
                map.put(sum, i);
            }
        }

        return max;
    }
}
