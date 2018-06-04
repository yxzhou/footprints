package fgafa.dfsbfs.permutationAndCombination;

import fgafa.util.Misc;

/**
 * 
 * Given a list of integers, which denote a permutation.
 * 
 * Find the next permutation in ascending order.
 * 
 * Example
 * For [1,3,2,3], the next permutation is [1,3,3,2]
 * 
 * For [4,3,2,1], the next permutation is [1,2,3,4]
 * 
 * Note
 * The list may contains duplicate integers.
 *
 */
public class NextPermutation {
    /**
     * @param nums: an array of integers
     * @return: return nothing (void), do not return anything, modify nums in-place instead
     */
    public int[] nextPermutation(int[] nums) {
        //check
        if(null == nums || 2 > nums.length){
            return nums;
        }
        
        int i = nums.length - 2;
        for( ; i >= 0 && nums[i] >= nums[i+1]; i--);
        
        if(-1 == i){
            reverse(nums, 0, nums.length - 1);
        }else{
            int j = nums.length - 1;
            for( ; j > i && nums[i] >= nums[j]; j--);
            
            swap(nums, i, j);
            reverse(nums, i + 1, nums.length - 1);
        }
        
        return nums;
    }
    
    private void reverse(int[] nums, int i, int j){
        for( ; i < j; i++, j--){
            swap(nums, i, j);
        }
    }
    
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
	public static void main(String[] args) {
		NextPermutation sv = new NextPermutation();

		int[][] input = { { 1 }, { 1, 2 }, { 1, 2, 3 }, { 1, 2, 2 },
				{ 1, 2, 2, 2 }, { 1, 2, 2, 2, 3 }, { 0, 1, 0, 0, 9 },
				{ 3, 3, 0, 0, 2, 3, 2 } };

		for (int[] num : input) {
			for(int i=0; i<10; i++){
				System.out.println(Misc.array2String(num));
				sv.nextPermutation(num);
			}
		}
	}
}
