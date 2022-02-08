package datastructure.binaryIndexedTree;

import java.util.Arrays;
import java.util.Random;

/**
 * _https://www.lintcode.com/problem/840
 *
 * Given an integer array in the construct method, implement two methods:
 *      update(i, val) Modify the element whose index is i to val.
 *      sumRange(l, r) Return the sum of elements whose indexes are in range of [l,r]. both inclusive
 * 
 * Example 1
 *   Input: 
 *     nums = [1, 3, 5]
 *     sumRange(0, 2)
 *     update(1, 2)
 *     sumRange(0, 2)
 *   Output:
 *     9
 *     8
 * 
 * Example 2:
 *   Input:
 *     nums = [0, 9, 5, 7, 3]
 *     sumRange(4, 4)
 *     sumRange(2, 4)
 *     update(4, 5)
 *     update(1, 7)
 *     update(0, 8)
 *     sumRange(1, 2)
 *   Output:
 *     3
 *     15
 *     12

 *   Challenge  O(logN) time for update and sumRange.
 *
 *   Solution: with BinaryIndexedTree
 */

public class RangeSumMutable {
    int[] nums; // the copy of the original matrix, zero-based indexing
    int[] sums; // the BinaryIndexedTree array, 1-based indexing

    public RangeSumMutable(int[] origin){
        if(null == origin || 0 == origin.length){
            throw new IllegalArgumentException("The input Integer array should not be null or empty. ");
        }

        nums = origin;  //default all are 0

        sums = new int[origin.length + 1]; //default all are 0
        for(int i = 0; i < nums.length; i++){
            update(i, origin[i]);
        }
    }


    /** update datas[pos] to the new value, it's equal to update nums[pos+1]  */
    public void update(int p, int val){
        
        int delta = val - this.nums[p];
        this.nums[p] = val;  //update in the datas

        //update in the BI tree
        for( p++; p < sums.length; p += lowbit(p)){
            sums[p] += delta;
        }
    }

    /**
     *  Getting sum from original array with range [start, end]
     *  It's equal to getting sum from nums with range [start + 1, end + 1]
     *
     */
    public int sumRange(int start, int end){
        //assert start >= 0 && end < n
        //return i == 0? sumRange(j) : sumRange(j) - sumRange(i - 1);
        return getSum(end) - getSum(start - 1);
    }

    private int getSum(int p){
        int result = 0;

        for( p++; p > 0; p -= lowbit(p)){
            //System.out.println(Integer.toBinaryString(i));
            result += sums[p];
        }

        return result;
    }


    private int lowbit(int x) {
        return x & -x;
    }


    public static void main(String[] args){
        //check lowbit and x * (x - 1)
        for (int x = 0; x < 19; x++) {
            System.out.println( String.format( "\n\tx=\t%s \n\t-x=\t%s \n\tx&-x=\t%s ", 
                    Integer.toBinaryString(x), 
                    Integer.toBinaryString(-x), 
                    Integer.toBinaryString(x & -x) ));
        }
        
        //x - lowbit vs x * (x - 1)
        for (int x = 0; x < 19; x++) {
            System.out.println( String.format( "\n\tx - x&-x=\t%s \n\tx&(x - 1)=\t%s ", 
                    Integer.toBinaryString( x - (x & -x) ), 
                    Integer.toBinaryString( x & (x - 1)) ));
        }
        
        RangeSumMutable sv = new RangeSumMutable(new int[]{0, 0, 0, 0, 0, 0, 0, 0});
        //        for(int i = 0; i <= 16; i++){
        //            System.out.println(Integer.toBinaryString(i) + "  " + Integer.toBinaryString(sv.lowbit(i)));
        //        }

        sv.update(1, 1);
        
        //init 
        Random random=new Random();
        int length=7;
        int[] origin = new int[length];
        for (int i = 0; i < origin.length; i++) {
            origin[i] = random.nextInt(100);
        }
        RangeSumMutable biTree = new RangeSumMutable(origin);

        //print the input
        System.out.print(String.format("Origin \t\t%s\n", Arrays.toString(origin)));
        System.out.print(String.format("BIT.nums \t%s\n", Arrays.toString(biTree.nums)));

        //print the BIT.sums
        //System.out.print(String.format("BIT.sums \t\t%s\n", Arrays.toString(biTree.sums)));
        
        //get the prefix sum
        System.out.print("rangeSum \t");
        for (int i = 0; i < origin.length; i++) {
            System.out.printf("%3d \t",biTree.getSum(i));
        }

        //cal the range sum
        System.out.printf("\n%3d",biTree.sumRange(2,4));

    }
}
