/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * _https://www.lintcode.com/problem/1236
 * 
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 * Find all the elements of [1, n] inclusive that do not appear in this array.
 *
 * Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
 * 
 * Example
 * Input: [4,3,2,7,8,2,3,1]
 * Output: [5,6]
 * 
 */
public class FindMissingAll {
    /**
     * @param nums: a list of integers
     * @return: return a list of integers
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        if(nums == null){
            return Collections.EMPTY_LIST;
        }

        int n = nums.length;

        int tmp;
        for(int i = 0; i < n; ){
            if(nums[nums[i] - 1] != nums[i]){ //swap when --
                tmp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = tmp;
            }else{
                i++;
            }
        }

        List<Integer> result = new LinkedList<>();
        for(int i = 0, j = 1; i < n; i=j++ ){
            if(nums[i] != j){
                result.add(j);
            }
        }

        return result;
    }
}
