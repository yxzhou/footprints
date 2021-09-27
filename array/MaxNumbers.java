package array;

public class MaxNumbers {
    
    /**
     * input: [9, 1, 2, 5, 8, 3] 
     *   when k = 1,  return [9]
     *   when k = 2,  return [9, 8]
     *   when k = 3,  return [9, 8, 3]
     *   when k = 4,  return [9, 5, 8, 3]
     */
    public int[] maxSubArray(int[] nums, int k) {
        int[] res = new int[k];
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            while (len > 0 && len + nums.length - i > k && res[len - 1] < nums[i]) {
                len--;
            }
            if (len < k)
                res[len++] = nums[i];
        }
        return res;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
