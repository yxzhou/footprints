package fgafa.dailyCoding;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Given an array of numbers, find the length of the longest increasing subsequence in the array. The subsequence does not necessarily have to be contiguous.

 For example, given the array [0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15], the longest increasing subsequence has length 6: it is 0, 2, 6, 9, 11, 15.
 *
 * Tags: ms
 *
 */

public class LongestSubSequence {

    public int longestSubSequence(int[] nums){

        ArrayList<Integer> result = new ArrayList<>();
        result.add(nums[0]);

        for(int num : nums){
            if(num > result.get(result.size() - 1)){
                result.add(num);
            }else{
                binarySearch(result, num);
            }
        }

        return result.size();
    }

    private void binarySearch(ArrayList<Integer> list, int target){
        int left = 0;
        int right = list.size() - 1;

        while(left <= right){
            int middle = left + (right - left) / 2;

            if(list.get(middle) == target){
                return;
            }else if(list.get(middle) < target){
                left = middle + 1;
            }else{
                right = middle - 1;
            }
        }

        list.set(left, target);
    }

    public int longestSubSequence_n(int[] nums){

        int[] sequence = new int[nums.length];
        sequence[0] = nums[0];

        int i = 0;
        for(int num : nums){
            i = binarySearch(sequence, i, num);
        }

        return i + 1;
    }

    private int binarySearch(int[] nums, int length, int target){
        int left = 0;
        int right = length;

        while(left <= right){
            int middle = left + (right - left) / 2;

            if(nums[middle] == target){
                return middle;
            }else if(nums[middle] < target){
                left = middle + 1;
            }else{
                right = middle - 1;
            }
        }

        nums[left] = target;
        return left > length ? left : length;
    }
}
