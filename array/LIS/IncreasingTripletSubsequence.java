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

        int n = nums.length;
        int x1 = nums[0];
        int x2 = Integer.MAX_VALUE;

        for(int i = 1; i < n; i++){
            if( x2 < nums[i] ){
                return true;
            }else if(x1 < nums[i] ){
                x2 = nums[i];
            }else{
                x1 = nums[i];
            }
        }

        return false;
    }


}
