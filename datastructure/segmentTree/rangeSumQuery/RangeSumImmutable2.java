package datastructure.segmentTree.rangeSumQuery;

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
 *  Solution: prefixSum
 *     define int[] sums, sums[i] is the sum of nums[0] to nums[i]
 *     so, sumRange(i, j) = sums[j] - sum[i]
 *
 */

public class RangeSumImmutable2 {

    int[] sum ; 

    public RangeSumImmutable2(int[] nums) {        
        this.sum = new int[nums.length];
        
        sum[0] = nums[0];
        for(int i = 1; i < sum.length; i++){
            sum[i] = sum[i - 1] + nums[i];
        }
    }

    public int sumRange(int i, int j) {
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
            RangeSumImmutable2 sv = new RangeSumImmutable2(nums);
            
            for(int[] range : ranges){
                System.out.println(String.format("[%d, %d], %d ", range[0], range[1], sv.sumRange(range[0], range[1]) ));    
            }
        }
          
    }
}


// Your NumArray object will be instantiated and called as such:
// NumArray numArray = new NumArray(nums);
// numArray.sumRange(0, 1);
// numArray.sumRange(1, 2);
