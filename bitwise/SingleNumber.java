package bitwise;

import util.Misc;
import org.junit.Test;

/**
 * _https://www.lintcode.com/problem/82 singleNumber
 * _https://www.lintcode.com/problem/83 singleNumberII
 * _https://www.lintcode.com/problem/84 singleNumberIII
 * 
 */

public class SingleNumber {

    /**
     *
     * Given an array of integers, every element appears twice except for one. Find that single one.
     *
     * Note: Your algorithm should have a linear run time complexity. Could you implement it without using extra memory?
     */
    public int singleNumber(int[] A) {
        if (null == A | 0 == A.length) {
            throw new IllegalArgumentException("Can't find the single number from a NULL or Empty array.");
        }

        int ret = 0;
        for (int a : A) {
            ret ^= a;
        }

        return ret;
    }
	
    /**
     *
     * Given an array of integers, every element appears three times except for one. Find that single one.
     *
     * Note: Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
     */
    public int singleNumberII(int[] A) {
        if (null == A | 0 == A.length) {
            throw new IllegalArgumentException("Can't find the single number from a NULL or Empty array.");
        }

        int ret = 0;
        
        int bit;
        for (int i = 0; i < 32; i++) {
            bit = 0;
            for (int num : A) {
                bit += (num >> i) & 1;
            }
            
            bit %= 3;
            ret += (bit << i);
        }

        return ret;
    }
    
    /**
     * @param nums : An integer array, every element appears three times except for one
     * @return the single number
     */
    public int singleNumberII_2(int[] nums) {
        if (null == nums || 0 == nums.length) {
            throw new IllegalArgumentException("The input array should not be null or empty.");
        }

        int ret = 0;
        for (int i = 0; i < 32; i++) {
            int bit = 0;
            int x = (1 << i);
            for (int n : nums) {
                bit += n & x;
                bit %= 3; // for case, out of range
            }

            if (1 == bit) {
                ret |= (bit << i);
            }
        }

        return ret;
    }


    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());

        //
        System.out.println("<<: " + (1 << 3) + " " + (1 << 31));
        System.out.println("&: " + (2 & 2) + " " + (1 & 2));
        System.out.println("|: " + (2 | 2) + " " + (1 | 2));
        System.out.println("^: " + (2 ^ 2) + " " + (1 ^ 2));

        int[][] input = {
            {2, 2, 1},
            {0xFFFFFFF0, 0x0000000F, 0x0000000F},};

        SingleNumber sv = new SingleNumber();

        for (int[] nums : input) {
            System.out.println("\nInput: " + Misc.array2String(nums));
            System.out.println("Output: " + sv.singleNumber(nums));
        }

        System.out.println("\n=========================");
        int[][] input2 = {
            {2, 2, 2, 1},
            {2, 2, 2, 1, 1, 1, 4}
        };
        for (int[] nums : input2) {
            System.out.println("\nInput: " + Misc.array2String(nums));
            System.out.println("Output: " + sv.singleNumberII(nums) + " " + sv.singleNumberII_2(nums));
        }


    }

}
