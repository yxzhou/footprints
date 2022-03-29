/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package array;

import java.util.Arrays;

/**
 * The m3 (interval tree)  
 * 
 * Time complextiy is O(k*logn) Space O(n)
 * 
 */
public class MaxNumbers2 {
    
    
    public int[] maxSubArray_IntervalTree(int[] nums, int k) {
        
        int n = nums.length;
        MaxTree tree = new MaxTree(nums);
        
        int[] result = new int[k];
        
        for(int i = 0, j = 0; i < k; i++){
            
            j = tree.getMaxIndex(0, 0, n - 1, j, n - k + i);
            
            result[i] = nums[j];
            j++;
            
        }
        
        return result;
    }
    
    class MaxTree{
        
        int[] nums;
        int[] tree; // interval tree, 
        
        int n;
        
        MaxTree(int[] nums){
            if(nums == null){
                return;
            }
            
            this.nums = nums;
            n = nums.length;
            
            tree = new int[ 1 << ((int)Math.ceil(Math.log(n + 1) / Math.log(2)) + 1 )];
            Arrays.fill(tree, -1);
            
            initTree();
        }
        
        private void initTree(){
            
            int index;
            int left;
            int right;

            int mid;
            
            for(int i = 0; i < n; i++){
                index = 0;
                left = 0;
                right = n - 1;

                while(left <= right){
                    if(tree[index] == -1 || nums[i] > nums[tree[index]] ){
                        tree[index] = i;
                    }

                    if(left == right){
                        break;
                    }
                    
                    mid = left + (right - left) / 2;

                    if(i <= mid){
                        right = mid;
                        index = index * 2 + 1;
                    }else{
                        left = mid + 1;
                        index = index * 2 + 2;
                    }
                }
            }
        }
        
        public int getMaxIndex(int index, int left, int right, int intervalStart, int intervalEnd){
//System.out.println(String.format("left = %d, right = %d, intervalStart = %d, intervalEnd = %d, tree[index] = %d", left, right, intervalStart, intervalEnd, tree[index]));
            if(right < left || intervalEnd < intervalStart){
                return -1;
            }

            if(intervalStart <= left && right <= intervalEnd){
                return tree[index];
            }else if(right < intervalStart || intervalEnd < left){
                return -1;
            }
            
            int max = -1;
            int maxIndex = -1;
            
            int mid = left + (right - left) / 2;
            
            int i;
            if(intervalStart <= mid){
                i = getMaxIndex(index * 2 + 1, left, mid, intervalStart, intervalEnd);
                
                if(i != -1 && nums[i] > max){
                    max = nums[i];
                    maxIndex = i;
                }
            }
            
            if(mid + 1 <= intervalEnd){
                i = getMaxIndex(index * 2 + 2, mid + 1, right, intervalStart, intervalEnd);
                
                if(i != -1 && nums[i] > max){
                    maxIndex = i;
                }
            }
            
            return maxIndex;
        }
        
    }
}
