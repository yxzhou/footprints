package fgafa.datastructure.segmenttree.Fenwick;

import java.util.ArrayList;
import java.util.List;

import fgafa.util.Misc;

/**
 * 
 * Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
    Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.
    
    Note:
    A naive algorithm of O(n^2) is trivial. You MUST do better than that.
    
    Example:
    Given nums = [-2, 5, -1], lower = -2, upper = 2,
    Return 3.
    The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.
 *
 */

public class CountOfRangeSum {

    public int countRangeSum(int[] nums, int lower, int upper) {
        //check
        if(null == nums || 0 == nums.length){
            return 0;
        }
        
        BSTNode root = buildSumTree(nums);
        
        return countRangeSum(nums, lower, upper, root);
    }
    
    private BSTNode buildSumTree(int[] nums){

        long sum = nums[0];
        BSTNode root = new BSTNode(sum, 1);
        for(int i = 1; i < nums.length; i++){
            sum += nums[i];
            
            add(root, sum, i + 1);
        }
            
        return root;
    }
    
    private void add(BSTNode node, long sum, int endIndex){
        while( null != node){
            if(node.value > sum){
                if(null == node.left){
                    node.left = new BSTNode(sum, endIndex);
                    break;
                }else{
                    node = node.left;
                }
            }else if(node.value < sum){
                if(null == node.right){
                    node.right = new BSTNode(sum, endIndex);
                    break;
                }else{
                    node = node.right;
                }
                
            }else{ // == 
                node.endIndexs.add(endIndex);
                break;
            }
        }
    }
    
    private int countRangeSum(int[] nums, long lower, long upper, BSTNode root){

        int count = 0;
        
        for(int i = 0; i < nums.length; i++){
            count += countRangeSum(lower, upper, root, i);
            
            lower += nums[i];
            upper += nums[i];
        }
            
        return count;
    }
    
    private int countRangeSum(long lower, long upper, BSTNode node, int endIndex){
        if(null == node){
            return 0;
        }
        
        int count = 0;
        if(node.value < lower){
            return countRangeSum(lower, upper, node.right, endIndex);
        }else if(node.value > upper){
            return countRangeSum(lower, upper, node.left, endIndex);
        }else{ 
            List<Integer> endIndexs = node.endIndexs;
            for(int i = endIndexs.size() - 1; i >= 0; i--){
                if(endIndexs.get(i) > endIndex){
                    count++;
                }else{
                    break;
                }
            }
            
            count += countRangeSum(lower, upper, node.right, endIndex);
            count += countRangeSum(lower, upper, node.left, endIndex);
        }
        
        return count;
    }
    
    class BSTNode{
        long value;
        List<Integer> endIndexs = new ArrayList<>();
        
        BSTNode right = null;
        BSTNode left = null;
        
        BSTNode(long value, int endIndex){
            this.value = value;
            this.endIndexs.add(endIndex);
        }
    }
    
    public static void main(String[] args) {
        CountOfRangeSum sv = new CountOfRangeSum();

        int[][] input = {
                    { -2, 5, -1 },  // 3
                    {2147483647,-2147483648,-1,0}  //4
        };
        
        int[][] pairs = {
                    {-2, 2},
                    {-1, 0}
        };
        
        for(int i = 0; i < input.length; i++){
            System.out.println(String.format("%s and [%d, %d] -- %d", Misc.array2String(input[i]), pairs[i][0], pairs[i][1], sv.countRangeSum(input[i], pairs[i][0], pairs[i][1])));

        }
     }

}
