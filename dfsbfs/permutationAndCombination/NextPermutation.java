package dfsbfs.permutationAndCombination;

import org.junit.Assert;
import util.Misc;

/**
 * 
 * Given a list of integers, which denote a permutation.Find the next permutation in ascending order.
 * 
 * The list may contains duplicate integers.
 * 
 * Example 1:
 * Input: array = [1]
 * Output: [1]
 * Explanation: There is only one integer, and the next permutation is itself.
 * 
 * Example 2:
 * Input: array = [1,3,2,3]
 * Output: [1,3,3,2]
 * Explanation: The next permutation of [1,3,2,3] is [1,3,3,2].
 * 
 * Example 3:
 * Input: array = [4,3,2,1]
 * Output: [1,2,3,4]
 * Explanation:
 *   When there is no arrangement with a greater lexicographic order, the arrangement with the smallest lexicographic order is output.
 *
 */
public class NextPermutation {
    /**
     *
     * 全排列（非递归求顺序）算法 1、建立位置数组，即对位置进行排列，排列成功后转换为元素的排列； 2、按如下算法求全排列：
     * 设P是1～n(位置编号)的一个全排列：p = p1,p2...pn =
     * p1,p2...pj-1,pj,pj+1...pk-1,pk,pk+1...pn
     * (1)从排列的尾部开始，找出第一个比右边位置编号小的索引j（j从首部开始计算），即j = max{i | pi < pi+1}
     * (2)在pj的右边的位置编号中，找出所有比pj大的位置编号中最小的位置编号的索引k，即 k = max{i | pi > pj}
     *    pj右边的位置编号是从右至左递增的，因此k是所有大于pj的位置编号中索引最大的 
     * (3)交换pj与pk
     * (4)再将pj+1...pk-1,pk,pk+1...pn翻转得到排列p'= p1,p2...pj-1,pj,pn...pk+1,pk,pk-1...pj+1 
     * (5)p'便是排列p的下一个排列 
     * 
     * 例如：  to 24310:
     * (1)从右至左找出排列中第一个比右边数字小的数字2；
     * (2)在该数字后的数字中找出比2大的数中最小的一个3； 
     * (3)将2与3交换得到34210；
     * (4)将原来2（当前3）后面的所有数字翻转，即翻转4210，得30124； 
     * 求得24310的下一个排列为30124。
     * 
     * @param nums: an array of integers
     * @return: return nothing (void), do not return anything, modify nums in-place instead
     */
    public int[] nextPermutation(int[] nums) {
        if (null == nums || nums.length < 2) {
            return nums;
        }

        int i = nums.length - 2;
        for (; i >= 0 && nums[i] >= nums[i + 1]; i--);

        if (i == -1) {
            reverse(nums, 0, nums.length - 1);
        } else {
            int j = nums.length - 1;
            for (; nums[i] >= nums[j]; j--);

            swap(nums, i, j);
            reverse(nums, i + 1, nums.length - 1);
        }

        return nums;
    }

    private void reverse(int[] nums, int i, int j) {
        for (; i < j; i++, j--) {
            swap(nums, i, j);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        NextPermutation sv = new NextPermutation();

        int[][] input = {{1}, {1, 3, 2, 3}, {4, 3, 2, 1}, {1, 2}, {1, 2, 3}, {1, 2, 2},
        {1, 2, 2, 2}, {1, 2, 2, 2, 3}, {0, 1, 0, 0, 9},
        {3, 3, 0, 0, 2, 3, 2}};

        for (int[] num : input) {
            System.out.println();
            System.out.println(Misc.array2String(num));
            for (int i = 0; i < 3; i++) {
                System.out.println(Misc.array2String(sv.nextPermutation(num)));
            }
        }
        
        Assert.assertArrayEquals(new int[]{1}, sv.nextPermutation(new int[]{1}));
        Assert.assertArrayEquals(new int[]{1, 3, 3, 2}, sv.nextPermutation(new int[]{1, 3, 2, 3}));
        Assert.assertArrayEquals(new int[]{1, 2, 3, 4}, sv.nextPermutation(new int[]{4, 3, 2, 1}));
        
        Assert.assertArrayEquals(new int[]{2, 1}, sv.nextPermutation(new int[]{1, 2}));
        Assert.assertArrayEquals(new int[]{1, 3, 2}, sv.nextPermutation(new int[]{1, 2, 3}));
        Assert.assertArrayEquals(new int[]{2, 1, 2}, sv.nextPermutation(new int[]{1, 2, 2}));
        Assert.assertArrayEquals(new int[]{2, 1, 2, 2}, sv.nextPermutation(new int[]{1, 2, 2, 2}));
        Assert.assertArrayEquals(new int[]{1, 2, 2, 3, 2}, sv.nextPermutation(new int[]{1, 2, 2, 2, 3}));
        Assert.assertArrayEquals(new int[]{1}, sv.nextPermutation(new int[]{1}));
        Assert.assertArrayEquals(new int[]{1}, sv.nextPermutation(new int[]{1}));
        Assert.assertArrayEquals(new int[]{1}, sv.nextPermutation(new int[]{1}));
    }
}
