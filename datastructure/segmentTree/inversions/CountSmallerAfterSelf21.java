/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.segmentTree.inversions;

import java.util.Arrays;
import junit.framework.Assert;

/**
 * 
 * m21) If it's BinaryTree, every node mappings to a segment
 * 
 *    (min, max, the index in BinaryTree)
 *         (0, 5, leftChildrenCount)
 *           /                  \
 *      (0, 2, lcc)           (3, 5, lcc)
 *        /      \                /     \
 *   (0,1,lcc)  (2,2,lcc)   (3,4,lcc)  (5,5,lcc)
 *    /      \               /    \
 * (0,0,lcc)(1,1,lcc)   (3,3,lcc)(4,4,lcc) 
 * 
 * It takes space is O( (max - min + 1) * 2 ), the time complexity is O(n * log(max - min + 1) )
 * 
 */
public class CountSmallerAfterSelf21 {
    
    class Node {
        int middleValue;  
        int smaller = 0;
        //int equal = 0; //only set value on leaf
        
        boolean isLeaf = false;
        
        Node left;
        Node right;
    }
    

    public int countSmaller(int[] nums){
        if(null == nums || nums.length < 2){
            return 0;
        }
        
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int num : nums){
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        
        int result = 0;
        Node root = new Node();
        initTree(root, min, max);
        
        for(int i = nums.length - 1; i >= 0; i--){
            result += addAndCount(root, nums[i]);
        }
        
        return result;
    }
    
    private void initTree(Node node, int min, int max){        
        node.middleValue = min + (max - min) / 2;
        
        if(min == max){
            node.isLeaf = true;
        }else{
            if(node.left == null){
                node.left = new Node();
            }
            initTree(node.left, min, node.middleValue);
            
            if(node.right == null){
                node.right = new Node();
            }
            initTree(node.right, node.middleValue + 1, max);
        }
    }
    
    
    private int addAndCount(Node node, int currValue){
        if(node.isLeaf) {
            return 0; //if it need count smaller and equal, return node.equal
        }
        
        if(node.middleValue >= currValue){
            node.smaller++;
            
            return addAndCount(node.left, currValue);
        }else {
            return node.smaller + addAndCount(node.right, currValue);
        }
    }
    
    
    public static void main(String[] args){
        int[][][] inputs = {
            //{nums, {expect}}
            {{5, 2, 6, 1}, {4}},
            {{3, 2, 2, 6, 1}, {6}},
            {{1, 2, 3, 4}, {0}},
            
            {{3, -2, -2, -6, -1}, {6}},
            {{3, -2, -3, -6, -1}, {7}},
            
            //{{3, 2, 2, 6, Integer.MAX_VALUE - 1111, 1}, {7}},    //Memory Limit Exceeded Error
            //{{3, -2, -3, -6, Integer.MAX_VALUE - 1111, -1}, {8}},
        };
        
        CountSmallerAfterSelf21 sv = new CountSmallerAfterSelf21();

        for(int[][] input : inputs){
            System.out.println(Arrays.toString(input[0]));

            Assert.assertEquals(input[1][0], sv.countSmaller(input[0]));
        }
    }
}
