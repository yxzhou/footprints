package datastructure.segmentTree.inversions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Give you an integer array (index from 0 to n-1, where n is the size of this array, value from 0 to 10000) and an checkIn list.
 * For each checkIn, give you an integer, return the number of element in the array that are smaller than the given integer.

    Example
    For array [1,2,7,8,5], and queries [1,8,5], return [0,4,2]
    
    
    Challenge
    Could you use three ways to do it.
    
    Just loop
    Sort and binary search
    Build Segment Tree and Search.
 *
 *  Note: same as CountInversion, please know about CountInversion1 and CountInversion2 at first.
 *  The following implement is from CountInversion1
 *
 */

public class CountOfSmallerNumber {
	 /**
     * @param nums: An integer array
     * @return: The number of element in the array that 
     *          are smaller that the given integer
     */
    public List<Integer> countOfSmallerNumber(int[] nums, int[] queries) {
        //check
        if(null == nums || null == queries){
            return Collections.emptyList();
        }

        int[] tree = new int[10001]; // from 0 to 10000

        //initTree(tree, 0, 0, 10000);

        List<Integer> result = new ArrayList<Integer>();

        for(int target : queries){
            result.add(addNodeAndGetSmallerCount(tree, 0, 0, 10000, target));
        }
        
        return result;
    }

    private int addNodeAndGetSmallerCount(int[] tree, int nodeIndex, int start, int end, int target){
        if(start == end){
            return 0;
        }

        int middle = start + (end - start) / 2;

        if(middle >= target){
            tree[nodeIndex]++;
            return addNodeAndGetSmallerCount(tree, nodeIndex * 2 + 1, start, middle, target);
        }else{
            int result = 0;
            result += tree[nodeIndex];
            result += addNodeAndGetSmallerCount(tree, nodeIndex * 2 + 2, middle + 1, end, target);

            return result;
        }
    }

}
