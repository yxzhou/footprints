package array.swapReverseRotate;

import util.Misc;
import org.junit.Test;

/**
 * _https://www.lintcode.com/problem/1334/?_from=ladder&fromId=190
 *
 * Rotate an array of n elements to the right by k steps.
 *
 * For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 *
 * Try solving this without creating a copy of the list. How many swap or move operations do you need?
 *
 * Thoughts:
 * S1: copy to a new array
 *
 * S2:  do it in-place with O(1) extra space, swap
 *    [1, 2, 3, 4, 5, 6, 7]
 * -> [7, 6, 5, 4, 3, 2, 1]   swap [1, 2, 3, 4, 5, 6]
 * -> [5, 6, 7, 1, 2, 3, 4]   swap [7, 6, 5] and [64, 3, 2, 1]
 *
 */
public class Rotate {

    /**
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] rotate_copyarray(int[] nums, int k) {
        if (null == nums || nums.length < 2 || k < 1) {
            return nums;
        }

        int n = nums.length;
        k %= n;

        int[] result = new int[n];

        System.arraycopy(nums, n - k, result, 0, k);
        System.arraycopy(nums, 0, result, k, n - k);

        return result;
    }

    /**
     * do it in-place with O(1) extra space
     *
     * @param nums
     * @param k
     */
    public void rotate_swap(int[] nums, int k) {
        if (null == nums || nums.length < 2 || k < 1) {
            return;
        }

        int n = nums.length;
        k %= n;

        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    private void reverse(int[] nums, int l, int r) {
        int tmp;
        for (; l < r; l++, r--) {
            tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        Rotate sv = new Rotate();

        for (int k = 0; k < 3; k++) {
            int[] array1 = new int[]{1, 2};
            sv.rotate_swap(array1, k);
            Misc.printArray_Int(array1);
        }

        for (int k = 0; k < 4; k++) {
            int[] array1 = new int[]{1, 2, 3};
            sv.rotate_swap(array1, k);
            Misc.printArray_Int(array1);
        }

        for (int k = 0; k < 7; k++) {
            int[] array1 = new int[]{1, 2, 3, 4, 5, 6};
            sv.rotate_swap(array1, k);
            Misc.printArray_Int(array1);
        }

        for (int k = 0; k < 8; k++) {
            int[] array1 = new int[]{1, 2, 3, 4, 5, 6, 7};
            sv.rotate_swap(array1, k);
            Misc.printArray_Int(array1);
        }
    }

}
