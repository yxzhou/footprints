/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * Description
 * A beautiful subarray is defined as an array of any length having a specific number of odd elements. 
 * Given an array of integers and a number of odd elements that constitutes beauty, create as many distinct beautiful subarrays as possible. 
 * Distinct means the arrays do not share identical starting and ending indices, though they may share one of the two. 
 * Return the number of beautiful subarrays.
 *
 * Notes:
 *  the length of nums is within range: [1, 100000]
 *  numOdds is with range: [1, 100000]
 *  Guarantee the type of result is int.
 * 
 * Example 1:
 * Input: 
 * nums = [1, 2, 3, 4, 5]  numOdds = 2
 * Output: 4
 * Explanation: There are 4 subarrays only have two odds. such as: [1, 2, 3], [1, 2, 3, 4], [2, 3, 4, 5], [3, 4, 5].
 *
 * Example 2:
 * Input:
 * nums = [2, 4, 6, 8]  numOdds = 1
 * Output: 0
 * Explanation: No odd number in array
 * 
 * 
 */
public class BeautifulSubarrays {
    
    public int BeautifulSubarrays(int[] nums, int numOdds) {
        int sum = 0;        
        int n = nums.length;

        Deque<Integer> diffs = new LinkedList<>();
        int preIndex = -1;
        for(int i = 0; i < n; i++){
            if( (nums[i] & 1) == 1 ){
                diffs.add(i - preIndex);
                preIndex = i;
            }

            if(diffs.size() > numOdds){
                sum += diffs.pollFirst() * diffs.peekLast();
            }
        }

        if(diffs.size() == numOdds){
            sum += diffs.pollFirst() * (n - preIndex);
        }

        return sum;
    }
    
    
}
