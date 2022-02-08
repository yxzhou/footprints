package bitwise;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * _https://www.lintcode.com/problem/824
 *
 * Give an array, all the numbers appear twice except one number which appears once and all the numbers which appear
 * twice are next to each other. Find the number which appears once.
 *
 *
 * 1 <= nums.length < 10^4 
 * In order to limit the time complexity of the program, your program will run 10^5 times.
 *
 * Example 1: 
 * Input: [3,3,2,2,4,5,5] 
 * Output: 4 
 *
 * Example 2: 
 * Input: [2,1,1,3,3] 
 * Output: 2 
 * 
 * 
 * Test cases:
 * --bad input--
 * null
 * {1,1}
 * {1,2,1}
 * --right input--
 * {1}
 * {1,2,2}
 * {1,1,2}
 * {1,2,2,3,3}
 * {1,1,2,3,3}
 * {2,2,1,1,3}
 * {3,3,1,1,2}
 *
 * Thoughts:
 *   because all the numbers appear twice except one number
 *   m1) xor, as same as SingleNumber 
 * 
 *   because all the numbers which appear twice are next to each other
 *   m2) traversal, from rigth to left, check pair to pair
 *       if they are not same, return the first one
 * 
 *   m3) binary search, 
 * 
 *   define n as the length of input array
 *   m1, m2,  Time O(n)
 *   m3,      Time O( logn )
 * 
 */

public class SingleNumberIV {

    public int singleNumber_binarySearch(int[] nums){
        if(null == nums || 0 == (nums.length & 1)){
            throw new IllegalArgumentException("Can't find out the single number.");
        }

        int left = 0;
        int right = nums.length - 1;
        
        int mid;
        int diff;
        while(left < right){
            mid = left + (right - left) / 2;
            diff = mid - left;
            
            if(nums[mid] == nums[mid + 1]){
                if((diff & 1) == 0 ){
                    left = mid + 2;
                }else{
                    right = mid - 1;
                }
                
            }else if(mid > 0 && nums[mid] == nums[mid - 1]){
                if((diff & 1) == 1 ){
                    left = mid + 1;
                }else{
                    right = mid - 2;
                }
            }else{
                return nums[mid];
            }
        }

        return nums[left];
    }
    
    public int singleNumber_xor(int[] nums){
        int x = 0;
        
        for(int n : nums){
            x ^= n;
        }
        
        return x;
    }
    
    public int singleNumber_2(int[] nums){
        int i = 0;
        int j = 1;
        while(j < nums.length ){
            if(nums[j] == nums[i]){
                i = j + 1;
                j += 2;
            }else{
                break;
            }
        }
        
        return nums[i];
    }
    
    
    public static void main(String[] args){
        int[][][] inputs = {
            {
                {3,3,2,2,4,5,5},
                {4}
            },
            {
                {2,1,1,3,3},
                {2}
            },
            {
                {3,3,1,1,2},
                {2}
            }
        };
        
        SingleNumberIV sv = new SingleNumberIV();
        
        for(int[][] input : inputs){
            System.out.println(String.format("\n%s\n[%d]", Arrays.toString(input[0]), input[1][0] ));
                    
            Assert.assertEquals(input[1][0], sv.singleNumber_xor(input[0]));
            Assert.assertEquals(input[1][0], sv.singleNumber_2(input[0]));
            Assert.assertEquals(input[1][0], sv.singleNumber_binarySearch(input[0]));
        }
        
    }
}
