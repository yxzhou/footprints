package dfsbfs.permutationAndCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import util.Misc;


/**
 * 
 * Given a list of integers, which denote a permutation. Find the previous permutation in ascending order.
 * 
 * The list may contains duplicate integers.
 * 
 * Example 1:
 * Input: array = [1]
 * Output: [1]
 * Explanation: There is only one integer, and the previous permutation is itself.
 * 
 * Example 2:
 * Input: array = [1,3,2,3]
 * Output: [1,2,3,3]
 * Explanation: The previous permutation of [1,3,2,3] is [1,2,3,3].
 * 
 * Example 3:
 * Input: array = [1,2,3,4]
 * Output: [4,3,2,1]
 * Explanation:
 *   When there is no arrangement with a smaller lexicographic order, the arrangement with the largest lexicographic order is output.
 *
 */
public class PreviousPermutation {
    /**
     * cases: (use <= mark the previous permutation)
     * 
     * 112 <= 211 <= 121 <= 112
     * 1323 <= 1233 <= 3321 <= 3312 <= 3231 <= 3213 <= 3132 <= 3123 <= 2331 <= 2313 <= 2133 <= 1332 <= 1323 
     * 
     * @param nums: A list of integers
     * @return: A list of integers that's previous permuation
     */
    public ArrayList<Integer> previousPermuation(ArrayList<Integer> nums) {
        if(nums == null || nums.size() < 2 ){
            return nums;
        }

        //check from right to left, find the first i, nums.get(i) > nums.get(i + 1)
        int i = nums.size() - 2;
        while(i >= 0 && nums.get(i) <= nums.get(i + 1)){
            i--;
        }

        if(i == -1){
            reverse(nums, 0, nums.size() - 1);
            return nums;
        }

        //check from right to left, find the first j, nums.get(j) < nums.get(i)
        int j = nums.size() - 1;
        while(nums.get(j) >= nums.get(i)){
            j--;
        }

        swap(nums, i, j);
        reverse(nums, i + 1, nums.size() - 1);

        return nums;
    }

    private void reverse(ArrayList<Integer> nums, int start, int end){
        for( ; start < end; start++, end--){
            swap(nums, start, end);
        }
    }

    private void swap(ArrayList<Integer> nums, int i, int j){
        int tmp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, tmp);
    }
    
    public static void main(String[] args) {
        PreviousPermutation sv = new PreviousPermutation();

        Integer[][] input = {{1}, {1, 3, 2, 3}, {1, 2, 3, 4}, {1, 2}, {1, 2, 3}, {1, 2, 2},
        {1, 2, 2, 2}, {1, 2, 2, 2, 3}, {0, 1, 0, 0, 9},
        {3, 3, 0, 0, 2, 3, 2}};

        ArrayList<Integer> list;
        for (Integer[] num : input) {
            System.out.println();
            System.out.println(Misc.array2String(num));
            list = new ArrayList<>(Arrays.asList(num));
            for (int i = 0; i < 3; i++) {
                list = sv.previousPermuation(list);
                System.out.println(Misc.array2String(list));
            }
        }
        
        Assert.assertEquals(new ArrayList<>(Arrays.asList(new Integer[]{1})).toString(), sv.previousPermuation(new ArrayList<>(Arrays.asList(new Integer[]{1}))).toString() );
        Assert.assertEquals(new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3, 3})).toString(), sv.previousPermuation(new ArrayList<>(Arrays.asList(new Integer[]{1, 3, 2, 3}))).toString() );
        Assert.assertEquals(new ArrayList<>(Arrays.asList(new Integer[]{4, 3, 2, 1})).toString(), sv.previousPermuation(new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3, 4}))).toString() );
        
//        Assert.assertArrayEquals(new int[]{2, 1}, sv.nextPermutation(new int[]{1, 2}));
//        Assert.assertArrayEquals(new int[]{1, 2, 3}, sv.nextPermutation(new int[]{1, 3, 2}));
//        Assert.assertArrayEquals(new int[]{2, 1, 2}, sv.nextPermutation(new int[]{1, 2, 2}));
//        Assert.assertArrayEquals(new int[]{2, 1, 2, 2}, sv.nextPermutation(new int[]{1, 2, 2, 2}));
//        Assert.assertArrayEquals(new int[]{1, 2, 2, 3, 2}, sv.nextPermutation(new int[]{1, 2, 2, 2, 3}));
//        Assert.assertArrayEquals(new int[]{1}, sv.nextPermutation(new int[]{1}));
//        Assert.assertArrayEquals(new int[]{1}, sv.nextPermutation(new int[]{1}));
//        Assert.assertArrayEquals(new int[]{1}, sv.nextPermutation(new int[]{1}));
    }
    
}
