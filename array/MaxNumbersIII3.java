/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * Continue on MaxNumbersIII
 * 
 * it doesn't work on some case 
 * 
 */
public class MaxNumbersIII3 {
    
    int[] nums1;
    int[] nums2;
    
    int k;
    
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        if((nums1 == null && nums2 == null) || k < 1){
            return new int[0];
        }

        MaxNumbers2.MaxTree tree1 = new MaxNumbers2().new MaxTree(nums1);
        MaxNumbers2.MaxTree tree2 = new MaxNumbers2().new MaxTree(nums2);
        
        if(nums1 == null){
            return maxNumber(nums2, k, tree2);
        }else if(nums2 == null){
            return maxNumber(nums1, k, tree1);
        }
        
        int[] result = new int[k];
                
        this.nums1 = nums1;
        this.nums2 = nums2;
        this.k = k;
        dfs(result, 0, tree1, 0, tree2, 0, new HashMap<>());
        
        return result;
    }
    
    private void dfs(int[] result, int index, MaxNumbers2.MaxTree tree1, int start1, MaxNumbers2.MaxTree tree2, int start2, HashMap<String, int[]> cache){
        if(index == this.k ){
            return;
        }
        
        String key = start1 + " " + start2;
        if(cache.containsKey(key)){
            int[] cacheResult = cache.get(key);
            for(int i = index; i < k; i++ ){
                if(result[i] < cacheResult[i]){

                    System.arraycopy(cacheResult, i, result, i, k - i);
                    return;
                }
            }
            
            return;
        }
        
        int maxIndex1 = tree1.getMaxIndex(0, 0, nums1.length - 1, start1, nums1.length + nums2.length + index - k - start2 );
//System.out.println(String.format("--left = %d, right = %d, intervalStart = %d, intervalEnd = %d, maxIndex1 = %d", 0, nums1.length - 1, start1, nums1.length + nums2.length + index - k -start2, maxIndex1));

        int maxIndex2 = tree2.getMaxIndex(0, 0, nums2.length - 1, start2, nums1.length + nums2.length + index - k - start1 );
//System.out.println(String.format("--left = %d, right = %d, intervalStart = %d, intervalEnd = %d, maxIndex2 = %d", 0, nums2.length - 1, start2, nums1.length + nums2.length + index - k -start1, maxIndex2));


        if( maxIndex1 > -1 && ( maxIndex2 == -1 || ( nums1[maxIndex1] >= nums2[maxIndex2] && result[index] <= nums1[maxIndex1])) ){
            if(result[index] < nums1[maxIndex1]){
                result[index] = nums1[maxIndex1];
                Arrays.fill(result, index + 1, k, 0);
            }
            
            dfs(result, index + 1, tree1, maxIndex1 + 1, tree2, start2, cache);
        } 
        if( maxIndex2 > -1 && ( maxIndex1 == -1 || ( nums1[maxIndex1] <= nums2[maxIndex2] && result[index] <= nums2[maxIndex2])) ){
            if(result[index] < nums2[maxIndex2]){
                result[index] = nums2[maxIndex2];
                Arrays.fill(result, index + 1, k, 0);
            }
            
            dfs(result, index + 1, tree1, start1, tree2, maxIndex2 + 1, cache);
        }
        
        cache.put(key, Arrays.copyOf(result, k));
    }
    
    private int[] maxNumber(int[] nums, int k, MaxNumbers2.MaxTree tree) {
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
