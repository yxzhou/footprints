package array;


/**
 * 
 * Continue on MaxNumbersIII, optimize the maxNumber(nums1, k) from O(n) to O(logn)
 *
 */

public class MaxNumbersIII22  {
 
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        if((nums1 == null && nums2 == null) || k < 1){
            return new int[0];
        }
        
        MaxNumbers2.MaxTree tree1 = new MaxNumbers2().new MaxTree(nums1);
        MaxNumbers2.MaxTree tree2 = new MaxNumbers2().new MaxTree(nums2);
        
        if(null == nums1 || 0 == nums1.length){
            return maxNumber(nums2, k, tree2);
        }else if(null == nums2 || 0 == nums2.length){
            return maxNumber(nums1, k, tree1);
        }
        
        if(nums1.length + nums2.length < k){
            return new int[0];
        }
        
        int[] result = new int[k]; //default all are 0
        for (int i = Math.max(k - nums2.length, 0); i <= Math.min(nums1.length, k); i++) {
            
            int[] res1 = maxNumber(nums1, i, tree1);
            int[] res2 = maxNumber(nums2, k - i, tree2);
            int[] res = new int[k];
            int pos1 = 0, pos2 = 0, tpos = 0;
 
            while (pos1 < res1.length || pos2 < res2.length) {
                res[tpos++] = compare(res1, pos1, res2, pos2) ? res1[pos1++] : res2[pos2++];
            }
 
            if (!compare(result, 0, res, 0)){
                result = res;
            }
                
        }
        
        return result;
    }
    
    private boolean compare(int[] nums1, int start1, int[] nums2, int start2) { 
        
        for (; start1 < nums1.length && start2 < nums2.length; start1++, start2++) {
            if (nums1[start1] > nums2[start2]) {
                return true;
            }else if (nums1[start1] < nums2[start2]){
                return false;
            }
        }
        
        return start1 != nums1.length;
    }
    
    private int[] maxNumber(int[] nums, int k, MaxNumbers2.MaxTree tree) {
        if(nums == null || nums.length < k){
            return new int[0];
        }
        
        int[] result = new int[k];

        int n = nums.length;
        
        int maxIndex = -1;

        for(int i = 0; i < k; i++){
            maxIndex = tree.getMaxIndex(0, 0, n - 1, maxIndex + 1, n - k + i);
            result[i] = nums[maxIndex];
        }
        
        return result;
    }

}
