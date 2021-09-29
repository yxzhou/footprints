package array.subArray;

/**
 * 
 * Find the contiguous subarray within an array (containing at least one number) which has the largest product.
 * 
 * For example, given the array [2,3,-2,4], the contiguous subarray [2,3] has the largest product = 6.
 *
 */
public class MaxProductSubarray {

    /**
     * Time O(n)
     */
    public int maxProduct(int[] nums) {
        int result = Integer.MIN_VALUE;
        
        int max = 1;
        int min = 1;
        int tmp;
        for (int x : nums) {
            if (x > 0) {
                max = Math.max(x, max * x);
                min = Math.min(x, min * x);
            } else {
                tmp = max * x;
                max = Math.max(x, min * x);
                min = Math.min(x, tmp);
            }

            result = Math.max(result, max);
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] inputs = {{-4, -3, -2}};

        MaxProductSubarray sv = new MaxProductSubarray();

        for (int i = 0; i < inputs.length; i++) {
            sv.maxProduct(inputs[i]);
        }

    }

}
