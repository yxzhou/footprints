/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.segmentTree.inversions;

import java.util.Arrays;
import junit.framework.Assert;

/**
 *
 * m3) Define a tree to store {value, leftChildrenCount}, 
 * m31) example {5, 2, 6, 3}
 *  step 1, count the smaller than 3, and add 3
 *    init a node (3, 0)
 *  step 2, count the smaller than 6, and add 6
 *           (3, 0)
 *               \
 *             (6, 0)
 *  
 *  step 3, count the smaller than 2, and add 2
 *           (3, 1)
 *           /    \
 *       (2, 0) (6, 0)
 * 
 *  step 4, count the smaller than 5, and add 5
 *           (3, 1)
 *           /    \
 *       (2, 0)  (6, 1)
 *                /
 *             (5, 0)
 * 
 *  the space is O(n), the time complexity is O(n * n), for case {1,2,3} or {3,2,1} or {5, 2, 6, 1}
 * 
 */
public class CountSmallerAfterSelf31 {
    
    class Node{
        int value;
        int smaller = 0;
        int equal = 1; //for every node, 1 means itself
        
        Node left;
        Node right;
        
        Node(int value){
            this.value = value;
        }
    }
    
    public int countSmaller(int[] nums){
        if(null == nums || nums.length < 2){
            return 0;
        }
        
        int result = 0;
        
        int n = nums.length;
        Node root = new Node(nums[n - 1]);
        
        for(int i = n - 2; i >= 0; i--){
            result += addAndCount(root, nums[i]);
        }
        
        return result;
    }
    
    private int addAndCount(Node node, int currValue){
        if(node.value == currValue){
            node.equal++;
        }else if(node.value > currValue){
            node.smaller++;
            
            if(node.left == null){
                node.left = new Node(currValue);
            }else{
                return addAndCount(node.left, currValue);
            }
        }else { // node.value < currValue
            if(node.right == null){
                node.right = new Node(currValue);
                return node.smaller + node.equal;
            }else{
                return node.smaller + node.equal + addAndCount(node.right, currValue);
            }
        }
        
        return 0;
    }
    
    public static void main(String[] args){
        int[][][] inputs = {
            //{nums, {expect}}
            {{5, 2, 6, 1}, {4}},
            {{3, 2, 2, 6, 1}, {6}},
            {{1, 2, 3, 4}, {0}},
            
            {{3, -2, -2, -6, -1}, {6}},
            {{3, -2, -3, -6, -1}, {7}},
            
            {{3, 2, 2, 6, Integer.MAX_VALUE - 1111, 1}, {7}},    //Memory Limit Exceeded Error
            {{3, -2, -3, -6, Integer.MAX_VALUE - 1111, -1}, {8}},
        };
        
        CountSmallerAfterSelf31 sv = new CountSmallerAfterSelf31();

        for(int[][] input : inputs){
            System.out.println(Arrays.toString(input[0]));

            Assert.assertEquals(input[1][0], sv.countSmaller(input[0]));
        }
    }
    
}
