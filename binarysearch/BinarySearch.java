package fgafa.binarysearch;

import fgafa.util.Misc;

public class BinarySearch {


    public int locate(int[] nums, int target){
        //check
        if(null == nums || 0 == nums.length){
            return -1; // not found
        }
        
        int low = 0;
        int high = nums.length - 1;
        int mid;
        
        while(low <= high){
            mid = low + ((high - low) >> 1);
            
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] > target){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        
        return -1; // not found
    }
    
   
    /**
     * Given an sorted array A and a target value, return the index of the 
     * first element in A equal to or greater than the target value.
     */
    public int lowerBound(int[] nums, int target){
        if(null == nums || 0 == nums.length ){
            return -1; //complain
        }
        if(nums[nums.length - 1] < target){
            return -1; //complain
        }
        
        int low = 0;
        int high = nums.length - 1;
        int mid;
        
        while(low < high){
            mid = low + ((high - low) >> 1);
            
            if(nums[mid] >= target){
                high = mid;
            }else{
                low = mid + 1;
            }
        }
        
        return low;
    }
    
    public int lowerBound_2(int[] nums, int target){
        if(null == nums || 0 == nums.length ){
            return -1; //complain
        }
        if(nums[nums.length - 1] < target){
            return -1; //complain
        }
        
        int low = 0;
        int high = nums.length - 1;
        int mid;
        
        while(low <= high){
            mid = low + ((high - low) >> 1);
            
            if(nums[mid] >= target){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        
        return low;
    }
    
    /**
     * Given an sorted array A and a target value, return the index of the 
     * last element in A equal to or smaller than the target value.
     */
    public int upperBound(int[] nums, int target){
        if(null == nums || 0 == nums.length){
            return - 1; //complain
        }
        if(nums[0] > target){
            return -1; //complain
        }
        
        int low = 0;
        int high = nums.length - 1;
        int mid;
        while(low < high){
            mid = low + ((high - low + 1) >> 1); // **
            
            if(nums[mid] > target){
                high = mid - 1;
            }else{
                low = mid;
            }
        }
        
        return low;
    }
    
    public int upperBound_2(int[] nums, int target){
        if(null == nums || 0 == nums.length){
            return - 1; //complain
        }
     
        if(nums[0] > target){
            return -1; //complain
        }
        
        int low = 0;
        int high = nums.length - 1;
        int mid;
        while(low < high){   //**
            mid = low + ((high - low) >> 1);
            
            if(nums[mid] > target){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        
        //return low;
        return low >= 0 && low < nums.length && nums[low] > target ? low - 1 : low;
    }
    

    
    public static void main(String[] args){
        int[][] input = {
                    {1},
                    {1, 1},
                    {1, 1, 1},
                    {1, 2},
                    {1, 2, 3},
                    {1, 1, 2},
                    {1, 2, 2},
                    {1, 2, 2, 3},
                    {1, 2, 2, 4}
                    
        };
        
        int[] target = {
                    0,
                    1,
                    2,
                    3,
                    4
  
        };
        
        BinarySearch sv = new BinarySearch();
        
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < target.length; j++){
                System.out.println(String.format("\nInput: %s %d", Misc.array2String(input[i]), target[j]));
                
                //System.out.println(String.format("findTheInteger: %d ", sv.locate(input[i], target[j])));
                System.out.println(String.format("lowerBound: %d ", sv.lowerBound(input[i], target[j])));
                System.out.println(String.format("lowerBound: %d ", sv.lowerBound_2(input[i], target[j])));
                //System.out.println(String.format("upperBound: %d ", sv.upperBound(input[i], target[j])));
                //System.out.println(String.format("upperBound: %d ", sv.upperBound_2(input[i], target[j])));
            }
        }
    }
    
}
