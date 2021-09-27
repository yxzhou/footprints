package datastructure.segmentTree.inversions;

import util.Misc;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * You are given an integer array nums and you have to return a new counts array. 
 * The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].

    Example:
    Given nums = [5, 2, 6, 1]
    
    To the right of 5 there are 2 smaller elements (2 and 1).
    To the right of 2 there is only 1 smaller element (1).
    To the right of 6 there is 1 smaller element (1).
    To the right of 1 there is 0 smaller element.
    Return the array [2, 1, 1, 0].
 *
 *  Note: same as CountInversion, please know about CountInversion1 and CountInversion2 at first. The difference is here it need care about the duplicated number.
 *  The following implement is from CountInversion2,
 */

public class CountOfSmallerNumberAfterItself {

    public List<Integer> countSmaller(int[] nums) {
        if(null == nums || 0 == nums.length ){
            return Collections.emptyList();
        }

        List<Integer> result = new LinkedList<>(); //counting from right to left

        int length = nums.length;
        int[] orderedValue = new int[length];
        System.arraycopy(nums, 0, orderedValue, 0, length);
        Arrays.sort(orderedValue);

        TreeNode[] tree = new TreeNode[length];
        initTree(tree, orderedValue, length);
        
        for(int i = length - 1; i >= 0; i--){
            result.add(0, addTreeNodeAndCountSmaller(tree, 0, nums[i]));
        }
        
        return result;
    }

    private void initTree(TreeNode[] tree, int[] orderedInput, int length){
        int x = 1;
        while( x <= length ){
            x <<= 1;
        }

        if(length + 1 < x){
            x >>= 1;
        }
        int count = length - x + 1;

        int[] indexes = new int[length];
        int i = 0;
        for(int y = 0, index = 0; index < length; index++ ){
            if(y < count && index == y * 2){
                y++;
            }else{
                indexes[i++] = index;
            }
        }
        initTree(tree, 0, orderedInput, indexes, 0, x - 2);

        for(int y = 0; y < count; y++){
            tree[i++] = new TreeNode(orderedInput[y * 2]);
        }
    }

    private void initTree(TreeNode[] tree, int nodePosition, int[] orderedInput, int[] indexes, int start, int end){
        if(start <= end){
            int middle = start + (end - start) / 2;

            tree[nodePosition] = new TreeNode(orderedInput[indexes[middle]]);

            int leftSon = nodePosition * 2 + 1;
            initTree(tree, leftSon, orderedInput, indexes, start, middle - 1);
            initTree(tree, leftSon + 1, orderedInput, indexes, middle + 1, end);
        }
    }

    private int addTreeNodeAndCountSmaller(TreeNode[] tree, int nodePosition, int target){
        if(target == tree[nodePosition].value){
            tree[nodePosition].duplicatedCount++;
            return tree[nodePosition].leftChildTreeCount;
        }else if(target < tree[nodePosition].value){
            tree[nodePosition].leftChildTreeCount++;
            return addTreeNodeAndCountSmaller(tree, nodePosition * 2 + 1, target);
        }else{
            int result = 0;
            result += tree[nodePosition].leftChildTreeCount + tree[nodePosition].duplicatedCount;
            result += addTreeNodeAndCountSmaller(tree, nodePosition * 2 + 2, target);
            return result;
        }
    }

    class TreeNode {
        int value;
        int duplicatedCount = 0;
        int leftChildTreeCount = 0; //number of smaller elements in left sub tree
        
        TreeNode(int value) {
            this.value = value;
        }
    }
    
    public static void main(String[] args){
        CountOfSmallerNumberAfterItself sv = new CountOfSmallerNumberAfterItself();

        /*
        int[][] input = {
                {0},
                {0, 1},
                {0, 1, 2},
                {0, 1, 2, 3},
                {0, 1, 2, 3, 4},
                {0, 1, 2, 3, 4, 5},
                {0, 1, 2, 3, 4, 5, 6},
                {0, 1, 2, 3, 4, 5, 6, 7},
                {0, 1, 2, 3, 4, 5, 6, 7, 8},
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
        };
        
        for(int[] nums : input){
            System.out.println(Misc.array2String(nums));

            int length = nums.length;
            TreeNode[] tree = new TreeNode[length];
            sv.initTree(tree, nums, length);

            System.out.println();
            for (int i = 0; i < tree.length; i++) {
                System.out.print("\t" + tree[i].value + "\t");
            }
            System.out.println();
        }
        */

        int[][] input = {
                {5, 2, 6, 1},
                {3, 2, 2, 6, 1}
        };

        for(int[] nums : input){
            System.out.println(Misc.array2String(nums));

            System.out.println(String.format("%s, %s", Misc.array2String(nums), Misc.array2String(sv.countSmaller(nums).toArray())));
        }
    }
}
