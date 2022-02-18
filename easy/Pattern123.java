/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easy;

/**
 * _https://www.lintcode.com/problem/636
 *
 * Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak such that i < j < k and ai
 * < ak < aj. Design an algorithm that takes a list of n numbers as input and checks whether there is a 132 pattern in
 * the list.
 *
 * Notes:
 *   n will be less than 20,000.
 *
 * Example 1:
 * Input: nums = [1, 2, 3, 4] 
 * Output: False 
 * Explanation: There is no 132 pattern in the sequence. Example 2:
 *
 * Input: nums = [3, 1, 4, 2] 
 * Output: True 
 * Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
 * 
 */
public class Pattern123 {

    /**
     * Time O(n), Space O(1)
     * 
     * @param nums: a list of n integers
     * @return true if there is a 132 pattern or false
     */
    public boolean find132pattern(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int localMin = Integer.MAX_VALUE;
        
        for(int num : nums){
            if(min < num && num < max){
                return true;
            }

            if(localMin < num){
                min = Math.min(min, localMin);
                max = Math.max(max, num);
            }else{
                localMin = num;
            }
        }

        return false;
    }
    
}
