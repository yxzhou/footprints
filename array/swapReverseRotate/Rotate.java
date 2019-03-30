package fgafa.array.swapReverseRotate;

import fgafa.util.Misc;
import org.junit.Test;

/**
 * Rotate a one-dimensional array of n elements to the right by k steps.
 *
 * For instance, with n=7 and k=3, 
 * the array {a, b, c, d, e, f, g} is rotated to {e, f, g, a, b, c, d}
 *
 * Thoughts:
 * S1: copy to a new array
 *
 * S2: Inplace, swap n-r
 *    [1, 2, 3, 4, 5, 6]
 * -> [3, 4, 1, 2, 5, 6]   swap [1, 2] and ][3, 4]
 * -> [3, 4, 5, 6, 1, 2]   swap [1, 2] and ][5, 6]
 * S3:  Inplace, swap n
 *    [1, 2, 3, 4, 5, 6]
 * -> [2, 1, 3, 4, 5, 6]   swap [1, 2]
 * -> [2, 1, 6, 5, 4, 3]   swap [3, 4, 5, 6]
 * -> [3, 4, 6, 5, 1, 2]   swap [2, 1] and ][4, 3]
 * -> [3, 4, 5, 6, 1, 2]   swap [6, 5]
 * S4:  Inplace, swap n
 *    [1, 2, 3, 4, 5, 6]
 * -> [6, 5, 4, 3, 2, 1]   swap [1, 2, 3, 4, 5, 6]
 * -> [3, 4, 6, 5, 2, 1]   swap [6, 5, 4, 3]
 * -> [3, 4, 5, 6, 1, 2]   swap [2, 1]
 *
 */
public class Rotate
{

    /**
     *   Solution #4
     */
    public void rotateString_n(char[] str, int offset) {
        //check
        if(null == str || str.length < 2 || offset < 0){
            return;
        }
        
        offset %= str.length;
        
        reverse(str, 0, str.length - 1);
        reverse(str, 0, offset - 1);
        reverse(str, offset, str.length - 1);
    }
    
    private void reverse(char[] str, int i, int j){
        for( ;i < j; i++, j--){
            char c = str[i];
            str[i] = str[j];
            str[j] = c;
        }
    }


	/**
	 * 
	 * Rotate an array of n elements to the right by k steps.
	 * 
	 * For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
	 *
     * Try solving this without creating a copy of the list. How many swap or move operations do you need?
     *
     * Tags: fb
     *
	 */
	public void rotate(int[] nums, int k) {
		if (null == nums || k < 1)
			return;

		k = k % nums.length;

		reverse(nums, 0, nums.length - 1);
		reverse(nums, 0, k - 1);
		reverse(nums, k, nums.length - 1);
	}

	private void reverse(int[] nums, int start, int end) {
		for (; start < end; start++, end--) {
			int tmp = nums[start];
			nums[start] = nums[end];
			nums[end] = tmp;
		}
	}


    @Test
    public void test(){

        for(int k = 0; k < 3; k++){
            int[] array1 = new int[]{1, 2};
            rotate(array1, k);
            Misc.printArray_Int(array1);
        }

        for(int k = 0; k < 4; k++){
            int[] array1 = new int[]{1, 2, 3};
            rotate(array1, k);
            Misc.printArray_Int(array1);
        }


        for(int k = 0; k < 7; k++){
            int[] array1 = new int[]{1, 2, 3, 4, 5, 6};
            rotate(array1, k);
            Misc.printArray_Int(array1);
        }

        for(int k = 0; k < 8; k++){
            int[] array1 = new int[]{1, 2, 3, 4, 5, 6, 7};
            rotate(array1, k);
            Misc.printArray_Int(array1);
        }
    }

}
