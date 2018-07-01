package fgafa.datastructure.segmenttree.rangeSumQuery;

/**
 * 
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

    Example:
    Given nums = [-2, 0, 3, -5, 2, -1]
    
    sumRange(0, 2) -> 1
    sumRange(2, 5) -> -1
    sumRange(0, 5) -> -3
    Note:
    You may assume that the array does not change. There are many calls to sumRange function.
 *
 *  Solution:
 *     define int[] sums, sums[i] is the sum of nums[0] to nums[i]
 *     so, sumRange(i, j) = sums[j] - sum[i]
 *
 */

public class RangeSum_immutable {

    int[] sum ; 
    public RangeSum_immutable(int[] nums) {
        if(null == nums || 0 == nums.length){
            return;
        }
        
        this.sum = new int[nums.length + 1];
        
        for(int i = 1; i < sum.length; i++){
            sum[i] = sum[i - 1] + nums[i - 1];
        }
    }

    public int sumRange(int i, int j) {
        //check
        if(i < 0 || j < i || j > sum.length - 2){
            throw new IllegalArgumentException(String.format("The input i and j should be in [%d, %d]", 0, sum.length - 2));
        }
        
        return sum[j + 1] - sum[i];
    }


    public RangeSum_immutable(int[] nums, int n) {
        if(null == nums || 0 == nums.length){
            return;
        }
        
        this.sum = new int[nums.length];
        sum[0] = nums[0];
        for(int i = 1; i < sum.length; i++){
            sum[i] = sum[i - 1] + nums[i];
        }
    }

    public int sumRange_2(int i, int j) {
        //check
        if(i < 0 || j < i || j > sum.length - 1){
            throw new IllegalArgumentException(String.format("The input i and j should be in [%d, %d]", 0, sum.length - 2));
        }

        if(i == 0){
            return sum[j];
        }else{
            return sum[j] - sum[i - 1];
        }

    }

    
    public static void main(String[] args) {

        
        int[][] input = {
                    {-2, 0, 3, -5, 2, -1}
                    
        };
                
        int[][] ranges = {
                    {0, 2}, // 1
                    {2, 5}, // -1, 
                    {0, 5}  //-3
        };
        
        for(int[] nums : input){
            RangeSum_immutable sv = new RangeSum_immutable(nums);
            RangeSum_immutable sv2 = new RangeSum_immutable(nums, 1);
            
            for(int[] range : ranges){
                System.out.println(String.format("[%d, %d], %d - %d ", range[0], range[1], sv.sumRange(range[0], range[1]), sv2.sumRange_2(range[0], range[1])));    
            }
        }
          
    }
}


// Your NumArray object will be instantiated and called as such:
// NumArray numArray = new NumArray(nums);
// numArray.sumRange(0, 1);
// numArray.sumRange(1, 2);
