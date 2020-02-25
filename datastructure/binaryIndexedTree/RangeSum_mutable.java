package fgafa.datastructure.binaryIndexedTree;

/**
 *
 * Given an integer array in the construct method, implement two methods checkIn(start, end) and modify(index, value):

     For checkIn(start, end), return the sum from index start to index end in the given array.
     For modify(index, value), modify the number in the given index to value

     Example
     Given array A = [1,2,7,8,5].

     checkIn(0, 2), return 10.
     modify(0, 4), change A[0] from 1 to 4.
     checkIn(0, 1), return 6.
     modify(2, 1), change A[2] from 7 to 1.
     checkIn(2, 4), return 14.
     Note
     We suggest you finish problem Segment Tree Build, Segment Tree Query and Segment Tree Modify first.

     Challenge
     O(logN) time for checkIn and modify.
 *
 *   Solution: with BinaryIndexedTree
 */

public class RangeSum_mutable {
    final int n;  //length
    int[] nums; // the original array, start from 1
    int[] sums; // the BinaryIndexedTree array, start from 1

    public RangeSum_mutable(int[] origin){
        if(null == origin || 0 == origin.length){
            throw new IllegalArgumentException("The input Integer array should not be null or empty. ");
        }

        n = origin.length;

        nums = new int[n + 1];  //default all are 0
        //System.arraycopy(origin, 0, nums, 1, length);

        /*init the sums array*/
        sums = new int[n + 1]; //default all are 0
        for(int i = 0; i < n; i++){
            update(i, origin[i]);
        }
    }


    /** update original[pos] to the new value, it's equal to update nums[pos+1]  */
    public void update(int pos, int val){
        pos++;
        int diff = val - this.nums[pos];
        this.nums[pos] = val;  //update in the origin

        //update in the BI tree
        for(int i = pos; i <= n; i += lowbit(i)){
            sums[i] += diff;
        }
    }

    /**
     *  Getting sum from original array with range [start, end]
     *  It's equal to getting sum from nums with range [start + 1, end + 1]
     *
     */
    public int rangeSum(int start, int end){
        //assert start >= 0 && end < n

        return rangeSum(end + 1) - rangeSum(start);
    }


    private int rangeSum(int pos){
        int result = 0;

        for(int i = pos; i > 0; i -= lowbit(i)){
            //System.out.println(Integer.toBinaryString(i));
            result += sums[i];
        }

        return result;
    }


    private int lowbit(int x) {
        return x & -x;
    }


    public static void main(String[] args){
        RangeSum_mutable sv = new RangeSum_mutable(new int[]{0, 0, 0, 0, 0, 0, 0, 0});
        //        for(int i = 0; i <= 16; i++){
        //            System.out.println(Integer.toBinaryString(i) + "  " + Integer.toBinaryString(sv.lowbit(i)));
        //        }

        sv.update(1, 1);


/*
        //随机放满数据
        Random random=new Random();
        int length=7;
        int[] origin = new int[length];
        for (int i = 0; i < origin.length; i++) {
            origin[i] = random.nextInt(100);
        }

        //取出每一位
        System.out.print("Origin \t\t");
        for (int i = 0; i < origin.length; i++) {
            int value=origin[i];
            System.out.printf("%3d \t",value);
        }
        System.out.println();

        RangeSum_mutable biTree = new RangeSum_mutable(origin);
        //取出每一位
        System.out.print("BIT.nums \t");
        for (int i = 1; i <= origin.length; i++) {
            int value=biTree.nums[i];
            System.out.printf("%3d \t",value);
        }
        System.out.println();

        System.out.print("BIT.sums \t");
        for (int i = 1; i <= origin.length; i++) {
            int value=biTree.sums[i];
            System.out.printf("%3d \t",value);
        }
        System.out.println();
        //计算0~i的和
        System.out.print("rangeSum \t");
        for (int i = 0; i < origin.length; i++) {
            System.out.printf("%3d \t",biTree.rangeSum(i));
        }
        System.out.println();

        //计算start~end的和
        System.out.printf("%3d",biTree.rangeSum(2,4));
 */
    }
}
