/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.segmentTree.inversions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;

/**
 *
 * m4) Binary Indexed Tree
 *   example {5, 2, 6, 1, 7}, 
 *   step 1, sorted {1, 2, 5, 6, 7}
 *     nums: {5, 2, 6, 1, 7}, 
 *    index: {3, 2, 4, 1, 5}, 
 *   Step 2, count the smaller than 7, and add 7 (update BIT[index[7]] to 1)       
 *    BIT {0, 0, 0, 0, 0, 1}
 *           count the smaller than 1, and add 1 (update BIT[index[1]] to 1)  
 *    BIT {0, 1, 1, 0, 2, 1}
 *           count the smaller than 6, and add 6 (update BIT[index[6]] to 1)  
 * 
 */
public class CountSmallerAfterSelf4 {
    public int countSmaller(int[] nums){
        if(null == nums || nums.length < 2){
            return 0;
        }
        
        int n = nums.length;
        
        int[] sorted = Arrays.copyOf(nums, n);
        Arrays.sort(sorted);
        
        Map<Integer, Integer> indexes = new HashMap<>();
        for(int i = 0, j = 1; i < n; i++){
            if(!indexes.containsKey(sorted[i])){
                indexes.put(sorted[i], j);
                j++;
            }
        }
        
        int[] sums = new int[indexes.size() + 1]; //BinaryIndexedTree, 1-based indexing
        
        int result = 0;
        
        for(int i = n - 1; i >= 0; i--){
            //get the smaller 
            for( int p = indexes.get(nums[i]) - 1; p > 0; p -= lowbit(p)){ // if count smaller and equals, p = indexes.get(nums[i])
                result += sums[p];
            }
            
            //update in the BI tree
            for( int p = indexes.get(nums[i]); p < sums.length; p += lowbit(p)){
                sums[p] += 1;
            }
            
        }
        
        return result;
    }
    
    private int lowbit(int x) {
        return x & -x;
    }
    
    public static void main(String[] args){
        
        int[][][] inputs = {
            //{nums, {expect}}
            {{5, 2, 6, 1}, {4}},
            {{3, 2, 2, 6, 1}, {6}},
            {{1, 2, 3, 4}, {0}},
            
            {{3, -2, -2, -6, -1}, {6}},
            {{3, -2, -3, -6, -1}, {7}},
            
            {{3, 2, 2, 6, Integer.MAX_VALUE - 1111, 1}, {7}},    //Memory Limit Exceeded Error
            {{3, -2, -3, -6, Integer.MAX_VALUE - 1111, -1}, {8}},
        };
        
        CountSmallerAfterSelf4 sv = new CountSmallerAfterSelf4();

        for(int[][] input : inputs){
            System.out.println(Arrays.toString(input[0]));

            Assert.assertEquals(input[1][0], sv.countSmaller(input[0]));
        }
    }
}
