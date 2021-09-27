package sorting;

import java.util.ArrayList;
import java.util.List;

import util.Misc;

/*
 * Given an array,  all elements are in [1, 9]. Re-arrange the elements. 
 * E.g. 12334455 => {{1,2,3,4, 5},  {3, 4, 5}}. Do it in linear time.
 * 
 * 
 * Approach #1,  Counting Sorting
 *   Time O(n), Space O(max)
 */

public class GroupSortII {

    public List<List<Integer>> groupSort(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        
        if(null == nums || 0 == nums.length){
            return result;
        }
        
        final int N = 14;
        int[] counts = new int[N]; 
        
        for(int i : nums){
            counts[i]++;
        }
        
        for(int i = 1; i < N; i++){
            if(counts[i] > 0){
                List<Integer> group = new ArrayList<>();
                for(int j = i; j < N; j++){
                    if(counts[j] > 0){
                        counts[j]--;
                        group.add(j);
                    }
                }
                
                result.add(group);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
       int[] input = {1,2,3,3,4,4,5,5};
       
       GroupSortII sv = new GroupSortII();
       
       System.out.println(String.format("Input: %s \n Output: \n", Misc.array2String(input).toString()));
       Misc.printListList(sv.groupSort(input));
    }

}
