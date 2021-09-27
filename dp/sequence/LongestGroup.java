package dp.sequence;

import java.util.Arrays;

import util.Misc;

/**
 * 
 * Given a Integer array, that includes positive integers, please find out the largest sub group, 
 * that every two number in the sub group (si, sj) meet,  si % sj == 0 or sj % si == 0. 
 *  
 * 
 */

public class LongestGroup {
    
    public int[] findLargestGroup(int[] nums){
        if(null == nums || nums.length < 2){
            return nums;
        }
        
        Arrays.sort(nums);
        
        int[] counts = new int[nums.length]; //default all are 0
        int[] parents = new int[nums.length]; //default all are 0
        int tail = 0;
        for(int i = 1; i < nums.length; i++){
            for(int j = i - 1; j >= 0; j--){
                if(nums[i] % nums[j] == 0 && counts[i] <= counts[j]){
                    counts[i] = counts[j] + 1;
                    parents[i] = j;
                }
            }
            
            if(counts[tail] < counts[i]){
                tail = i;
            }
        }
        
        int length = counts[tail] + 1;
        int[] largestGroup = new int[length];
        for(int i = length - 1; i>=0; i--){
            largestGroup[i] = nums[tail];
            tail = parents[tail];
        }
        
        return largestGroup;
    }
    

    public static void main(String[] args) {
        int[][] input = {{1,2}, {1,2,3}, {1,2,3,4,5,6,7,8}};
        
        LongestGroup sv = new LongestGroup();
        
        for(int[] nums : input){
            System.out.println(String.format("\nInput: %s \nOutput:%s", Misc.array2String(nums), Misc.array2String(sv.findLargestGroup(nums))));
        }

    }

}
