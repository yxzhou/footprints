package fgafa.datastructure.segmenttree.Fenwick;

import java.util.LinkedList;
import java.util.List;

import fgafa.util.Misc;

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
 *  
 */

public class CountOfSmallerNumberAfterItself {

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> result = new LinkedList<>();
        if(null == nums || 0 == nums.length ){
            return result;
        }
        
        BSTNode root = new BSTNode(nums[nums.length - 1], 1);
        result.add(0, 0);
        
        for(int i = nums.length - 2; i >= 0; i--){
            result.add(0, add(root, nums[i]));
        }
        
        return result;
    }
    
    private int add(BSTNode node, int target){
        int result = 0;
        
        if(target == node.val){
            node.duplicate++;
            return node.numOfLeftNodes;
        }
        
        if(target < node.val){
            node.numOfLeftNodes++;
            
            if(null == node.left){
                node.left =  new BSTNode(target, 1);
                
            }else{
                result += add(node.left, target);
            }
        }else{
            result += node.numOfLeftNodes + node.duplicate;
            
            if(null == node.right){
                node.right =  new BSTNode(target, 1);
                
            }else{
                result += add(node.right, target) ;
            }
        }
        
        return result;
    }
    
    //Fenwick tree
    class BSTNode{
        int val = 0;
        int duplicate = 0;
        int numOfLeftNodes = 0;
        
        BSTNode left = null, right = null;
        
        BSTNode(int val, int duplicate) {
            this.val = val;
            this.duplicate = duplicate;
        }
    }
    
    public static void main(String[] args){
        CountOfSmallerNumberAfterItself sv = new CountOfSmallerNumberAfterItself();
        
        int[][] input = {
                    {5, 2, 6, 1},
                    {3, 2, 2, 6, 1}
        };
        
        for(int[] nums : input){
            System.out.println(String.format("%s, %s", Misc.array2String(nums), Misc.array2String(sv.countSmaller(nums).toArray())));
        }
    }
}
