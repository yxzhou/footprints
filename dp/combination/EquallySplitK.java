package dp.combination;

import org.junit.Test;

import java.util.Arrays;
import java.util.Stack;

/**
 * Leetcode #698
 *
 * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.
 *
 * Example 1:
 * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * Output: True
 * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 *
 *
 * Note:
 * 1 <= k <= len(nums) <= 16.
 * 0 < nums[i] < 10000.
 *
 */

public class EquallySplitK {

    public boolean canPartitionKSubsets(int[] nums, int k) {
        if(k == 1){
            return true;
        }

        int sum = 0;
        for(int x : nums){
            sum += x;
        }

        if(sum % k > 0){
            return false;
        }

        int target = sum / k;
        int start = 0;
        while( k-- > 1){
            start = exist(nums, start, target);

            if(start == -1){
                return false;
            }
        }

        return true;
    }

    private int exist(int[] nums, int start, int target){
        int[] paths = new int[target + 1];
        Arrays.fill(paths, -1);
        paths[0] = 0;

        for(int j = start; j < nums.length; j++){
            for(int i = target; i >= nums[j]; i--){
                if(paths[i] == -1 && paths[i - nums[j]] > -1){
                    paths[i] = j;

                    if(i == target){
                        break;
                    }
                }
            }
        }

        if(paths[target] == -1){
            return -1;
        }

        Stack<Integer> stack = new Stack<>();
        while(target > 0){
            stack.push(paths[target]);
            target -= nums[paths[target]];
        }

        int top;
        int tmp;
        while(!stack.isEmpty()){
            top = stack.pop();

            //swap
            tmp = nums[start];
            nums[start] = nums[top];
            nums[top] = tmp;

            start++;
        }

        return start;
    }


    @Test public void test(){

        //Assert.assertTrue(canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4));
        //Assert.assertTrue(canPartitionKSubsets(new int[]{10,10,10,7,7,7,7,7,7,6,6,6}, 3));


        //Assert.assertFalse(canPartitionKSubsets(new int[]{2, 2, 2, 2, 3, 4, 5}, 4));


        int sum = -30;
        int k = 3;

        int target = sum / k;

        System.out.println(sum % target);
        System.out.println((sum - 2) % target);
        System.out.println((sum + 2) % target);

        System.out.println();

        System.out.println(( sum - 1) % target + 1);
        System.out.println((sum - 2 - 1) % target + 1);
        System.out.println((sum + 2 - 1) % target + 1);
    }
}
