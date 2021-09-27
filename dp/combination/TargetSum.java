/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp.combination;

/**
 * _https://www.lintcode.com/problem/1208/?_from=collection&fromId=29
 *
 * Description You are given a list of non-negative integers, a1, a2, ..., an,
 * and a target, S. Now you have 2 symbols + and -. For each integer, you should
 * choose one from + and - as its new symbol.
 *
 * Find out how many ways to assign symbols to make sum of integers equal to
 * target S.
 *
 * The length of the given array is positive and will not exceed 20. The sum of
 * elements in the given array will not exceed 1000. Your output answer is
 * guaranteed to be fitted in a 32-bit integer.
 *
 * Example 1: Input: nums is [1, 1, 1, 1, 1], S is 3. Output: 5 Explanation:
 * -1+1+1+1+1 = 3 +1-1+1+1+1 = 3 +1+1-1+1+1 = 3 +1+1+1-1+1 = 3 +1+1+1+1-1 = 3
 *
 * There are 5 ways to assign symbols to make the sum of nums be target 3.
 *
 * Example 2: Input: nums is [], S is 3. Output: 0 Explanation: There are 0 way
 * to assign symbols to make the sum of nums be target 3.
 *
 */
public class TargetSum {

    public int findTargetSumWays(int[] nums, int s) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int total = 0;
        for (int x : nums) {
            total += x;
        }

        if (total < s || ((total + s) & 1) == 1) {
            return 0;
        }

        s = (total + s) / 2;

        int[] counts = new int[s + 1];
        counts[0] = 1;
        for (int x : nums) {
            for (int i = s; i >= 0; i--) {
                if (counts[i] > 0 && i + x <= s) {
                    counts[i + x] += counts[i];
                }
            }
        }

        return counts[s];
    }

}
