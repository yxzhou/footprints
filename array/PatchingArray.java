package array;

import java.util.Arrays;

import util.Misc;

/**
 * 
 * Given a sorted positive integer array nums and an integer n, add/patch elements to the array 
 * such that any number in range [1, n] inclusive can be formed by the sum of some elements in the array. 
 * Return the minimum number of patches required.

    Example 1:
    nums = [1, 3], n = 6
    Return 1.
    
    Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
    Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
    Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
    So we only need 1 patch.
    
    Example 2:
    nums = [1, 5, 10], n = 20
    Return 2.
    The two patches can be [2, 4].
    
    Example 3:
    nums = [1, 2, 2], n = 5
    Return 0.
 *
 */

public class PatchingArray {

    /* when n is not big*/
    public int minPatches(int[] nums, int n) {
        //check 
        if(null == nums || n < 1){
            return 0;
        }
        
        boolean[] filled = new boolean[10];
        
        filling(nums, 0, 0, filled);
        
        int count = 0;
        boolean hasMore = true;
        while(hasMore){
            hasMore = false;
            for(int i = 1; i < filled.length; i++){
                if(filled[i]){
                    continue;
                }
                
                count++;
                hasMore = true;
                    
                filling(filled, i, i);
            }
        }

        return count;
    }
    
    private void filling(boolean[] filled, int start, int x){
        for(int i = start; i < filled.length; i++){
            if(filled[i]){
                continue;
            }
                
            if(filled[i - x] || i == x){
                filled[i] = true;
            }
        }
    }
    
    private void filling(int[] nums, int i, long pathSum, boolean[] filled){
        if(i == nums.length){
            if(pathSum != 0){
                filled[(int)(pathSum - 1)] = true;
            }

            return;
        }
        
        filling(nums, i + 1, pathSum, filled);
        
        long tmp = pathSum + nums[i];
        if(tmp <= Integer.MAX_VALUE + 1){
            filling(nums, i + 1, tmp, filled);
        }
        
    }

    public int minPatches_n(int[] nums, int n) {
        //check 
        if(null == nums || n < 1){
            return 0;
        }
    
        Arrays.sort(nums);
        
        int count = 0;
        int[] index = new int[1];//default both it's 0
        int sum = 0;
        for(long j = 1; j <= n;  ){ // note, j is long, for overflow hwen n is very big
            sum = locate(nums, index, j);
            if(sum > 0){
                j += sum;
            }else{
                count++;

                j <<= 1;
            }
        }
        
        return count;
    }
    
    private int locate(int[] nums, int[] interval, long target){
        int sum = 0;
        for( ; interval[0] < nums.length ; interval[0]++){
            if(nums[interval[0]] <= target){
                sum += nums[interval[0]];
            }else{
                break;  //**
            }
        }
        
        return sum;
    }
    
    public int minPatches_n2(int[] nums, int n) {
        //check 
        if(null == nums || n < 1){
            return 0;
        }
    
        Arrays.sort(nums);
        
        int count = 0;
        int index = 0;
        for(long j = 1; j <= n;  ){ // note, j is long, for overflow hwen n is very big
            
            if(index < nums.length && j >= nums[index]){
                j += nums[index++];
            }else{
                j <<= 1;
                count++;
            }

        }
        
        return count;
    }
    
    
    
    public static void main(String[] args) {
        int[][] input = {
                    {1, 3},
                    {1, 5, 10},
                    {1, 2, 2},
                    {1,2,31,33}
                                
        };

        int[] n = {
                    6, 
                    20,
                    5, 
                    2147483647
        };
        
        PatchingArray sv = new PatchingArray();
        
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("%s, %d", Misc.array2String(input[i]), n[i]));
            
            System.out.println(String.format("%d, %d", sv.minPatches_n(input[i], n[i]), sv.minPatches_n2(input[i], n[i])));
        }
        
    }

}
