package array.LIS;

/**
 *  Leetcode #334
 *
 Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.

 Formally the function should:
 Return true if there exists i, j, k
 such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
 Your algorithm should run in O(n) time complexity and O(1) space complexity.

 Examples:
 Given [1, 2, 3, 4, 5],
 return true.

 Given [5, 4, 3, 2, 1],
 return false.
 */

public class IncreasingTripletSubsequence {


    public boolean increasingTriplet(int[] nums) {
        if(nums == null || nums.length < 3){
            return false;
        }

        int x1 = Integer.MAX_VALUE;
        int x2 = Integer.MAX_VALUE;

        for(int x : nums){
            if( x2 < x ){
                return true;
            }else if(x1 < x ){ // x <= x2
                x2 = x;
            }else{ // x <= x1
                x1 = x;
            }
        }

        return false;
    }

    public boolean increasingTriplet_n(int[] nums) {
        if(nums == null || nums.length < 3){
            return false;
        }

        int x1 = Integer.MAX_VALUE;
        int x2 = Integer.MAX_VALUE;

        for(int x : nums){
            if( x <= x1 ){
                x1 = x;
            }else if(x <= x2 ){
                x2 = x;
            }else{
                return true;
            }
        }

        return false;
    }
}
