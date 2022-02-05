/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.backpack;

/**
 * _https://www.lintcode.com/problem/724
 *
 * Given a set of positive integers, write a function to divide it into two sets S1 and S2 such that the absolute
 * difference between their sums is minimum.
 *
 * If there is a set S with n elements, then if we assume Subset1 has m elements, Subset2 must have n-m elements and the
 * value of abs(sum(Subset1) – sum(Subset2)) should be minimum.
 *
 *
 * Example1
 * Input: nums = [1, 6, 11, 5] 
 * Output: 1 
 * Explanation: Subset1 = [1, 5, 6], sum of Subset1 = 12 Subset2 = [11], sum of Subset2 = 11 abs(11 - 12) = 1 
 * 
 * Example2
 * Input: nums = [1, 2, 3, 4] 
 * Output: 0 
 * Explanation: Subset1 = [1, 4], sum of Subset1 = 5 Subset2 = [2, 3], sum of Subset2 = 5 abs(5 - 5) = 0
 *
 */
public class MinimumPartition {
    
    /**
     * @param nums: the given array
     * @return the minimum difference between their sums 
     */
    public int findMin(int[] nums) {
        if(nums == null){
            return 0;
        }

        int sum = 0; //assume int is enough
        for(int x : nums){
            sum += x;
        }

        int half = sum / 2;
        boolean[] f = new boolean[half + 1];
        f[0] = true;
        for(int x: nums){
            for(int y = half - x; y >= 0; y--){
                if(f[y]){
                    f[y + x] = true;
                }
            }
        }

        for(int y = half; y >= 0; y--){
            if(f[y]){             
                return sum - y - y; 
            }
        }

        return sum;
    }
    
}
