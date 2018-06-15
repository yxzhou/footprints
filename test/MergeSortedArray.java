package fgafa.test;

public class MergeSortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if(null == nums1 || m <= 0 || null == nums2 || n <=0){
            return;
        }
        
        int j = n - 1;
        for(int i = m - 1, k = m + n -1; j >= 0 & i >= 0; k--){
            if(nums1[i] > nums2[j]){
                nums1[k] = nums1[i--];
            }else{
                nums1[k] = nums2[j--];
            }
        }
        
        while(j >= 0){
            nums1[j] = nums2[j];
            j--;
        }
    }
}
