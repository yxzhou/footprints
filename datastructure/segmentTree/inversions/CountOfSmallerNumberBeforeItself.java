package fgafa.datastructure.segmentTree.inversions;

import fgafa.util.Misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Give you an integer array (index from 0 to n-1, where n is the size of this array, value from 0 to 10000) . 
 * For each element Ai in the array, leftChildrenCount the number of element before this element Ai is smaller than it and return leftChildrenCount number array.

    Example
    For array [1,2,7,8,5], return [0,1,2,3,2]
 *
 *  Challenge
    Could you use different ways to do it.
    
    Just loop
    Sort and binary search
    Build Segment Tree and Search.
 *
 *  Note: same as CountInversion, please know about CountInversion1 and CountInversion2 at first. The difference is here it need care about the duplicated number.
 *  The following implement is from CountInversion1,
 */

public class CountOfSmallerNumberBeforeItself {
	 /**
     * @param nums: An integer array
     * @return: Count the number of element before this element 'ai' is 
     *          smaller than it and return leftChildrenCount number array
     */ 
    public List<Integer> countOfSmallerNumberII(int[] nums) {
        if(null == nums || 0 == nums.length){
            return Collections.emptyList();
        }

        int max = nums[0];
        int min = nums[0];
        for(int num : nums){
            if(num > max){
                max = num;
            }else if(num < min){
                min = num;
            }
        }

        int length = nums.length;
        int[] tree = new int[(max - min + 1) * 2 - 1];
        //initTree(tree, 0, min, max);

        List<Integer> result = new ArrayList<>(length);
        for(int target : nums){
        	result.add(addNodeAndGetSmallers(tree, 0, min, max, target));
        }
        
        return result;
    }

    
    private int addNodeAndGetSmallers(int[] tree, int nodeIndex, int start, int end, int target){
        if(start == end){
            return 0;
        }

        int middle = start + (end - start) / 2;

        int leftSon = nodeIndex * 2 + 1;
        if(middle >=  target){
            tree[nodeIndex] ++;
            return addNodeAndGetSmallers(tree, leftSon, start, middle, target);
        }else{ //middle > target
            int result = 0;
            result += tree[nodeIndex];
            result += addNodeAndGetSmallers(tree, leftSon + 1, middle + 1, end, target);
            return result;
        }
    }

    public static void main(String[] args){
        CountOfSmallerNumberBeforeItself sv = new CountOfSmallerNumberBeforeItself();

        int[][] input = {
                {1, 6, 2, 5},
                {1, 6, 2, 2, 3}
        };

        for(int[] nums : input){
            System.out.println(String.format("%s, %s", Misc.array2String(nums), Misc.array2String(sv.countOfSmallerNumberII(nums).toArray())));
        }
    }
}
