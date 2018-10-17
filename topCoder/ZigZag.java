package fgafa.topCoder;

import fgafa.util.Misc;

/**
 * Problem
 *
 * A sequence of numbers is called a zig-zag sequence if the differences between successive numbers
 * strictly alternate between positive and negative. The first difference (if one exists) may be either
 * positive or negative. A sequence with fewer than two elements is trivially a zig-zag sequence.
 *
 * For example, 1,7,4,9,2,5 is a zig-zag sequence because the differences (6,-3,5,-7,3) are alternately
 * positive and negative. In contrast, 1,4,7,2,5 and 1,7,4,5,5 are not zig-zag sequences, the first because
 * its first two differences are positive and the second because its last difference is zero.
 *
 * Given a sequence of integers, sequence, return the length of the longest subsequence of sequence that
 * is a zig-zag sequence. A subsequence is obtained by deleting some number of elements (possibly zero)
 * from the original sequence, leaving the remaining elements in their original order.
 *
 * Constraints
 *  - sequence contains between 1 and 50 elements, inclusive.
 *  - Each element of sequence is between 1 and 1000, inclusive.
 */

public class ZigZag {

    public int zigZag(int[] nums){
        if(null == nums){
            return 0;
        }

        if(nums.length < 2){
            return nums.length;
        }

        int i = 1;
        while(nums[i] == nums[i - 1] && i < nums.length){
            i++;
        }

        if(i == nums.length){
            return 1;
        }

        int count = 2;
        boolean direction = (nums[i] > nums[i - 1]);

        for( i++; i < nums.length; i++){
            if( nums[i] != nums[i - 1] && (nums[i] > nums[i - 1]) != direction){
                count++;

                direction = !direction;
            }
        }

        return count;
    }

    public static void main(String[] args){
        int[][][] inputs = {
                { { 1, 7, 4, 9, 2, 5 }, { 6 } },
                { { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 }, { 7 } },
                { { 44 }, { 1 } },
                { { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 2 } },
                { { 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32 }, { 8 } },
                { { 374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 600, 947, 978, 46, 100, 953, 670, 862, 568, 188,
                        67, 669, 810, 704, 52, 861, 49, 640, 370, 908, 477, 245, 413, 109, 659, 401, 483, 308, 609, 120,
                        249, 22, 176, 279, 23, 22, 617, 462, 459, 244 }, { 36 } }
        };

        ZigZag sv = new ZigZag();

        for(int[][] input : inputs){

            System.out.println(Misc.array2String(input[0]));
            System.out.println(sv.zigZag(input[0]) + " = " + input[1][0]);
        }

    }
}
