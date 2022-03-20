/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure.segmentTree.inversions;

import java.util.Arrays;
import junit.framework.Assert;

/**
 *
 * _https://www.lintcode.com/problem/1297
 *
 * You are given an integer array nums and you have to return a new counts array. The counts array has the property 
 * where counts[i] is the number of smaller elements to the right of nums[i].
 *
 * Example 1:
 * Input: nums {8, 4, 2, 1}
 * Output: 6
 *    They are (8, 4) (8, 2) (8, 1) (4, 2) (4, 1) (2, 1)
 *
 * Example 1:
 * Input: nums {5, 2, 6, 1}
 * Output: 4
 *    They are (5, 2) (5, 1)(2, 1) (6, 1)
 *
 * Solution: Define n as the length of nums
 *   To {5, 2, 6, 1}, 
 *  
 * m1) Naive method, check every one from right to left.
 *   to 1, the inversion is 0,
 *   to 6, the inversion is 1,
 *   to 2, the inversion is 1,
 *   to 5, the inversion is 2.
 *   Totally it's 0+1+1+2 = 4
 *  It takes space O(1), the time complexity is O(n * n) 
 * 
 * m2) Define a tree to store {start, end, leftChildrenCount}, 
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
 * m22) store segments with int[] instead of Nodes, 
 *
 *     (min, max, the index in binaryIndexedTree)
 *            (0, 5, 0)
 *           /         \
 *      (0, 2, 1)     (3, 5, 2)
 *        /   \         /   \
 *   (0,1,3) (2,2,4) (3,4,5) (5,5,6)
 *    /   \           /   \
 * (0,0,7)(1,1,8) (3,3,11)(4,4,12) 
 *  
 *  Define w as the max - min + 1, 
 *  the height of the binaryIndexedTree, h is Math.ceil(log(2, w)),  
 *  total space is 2^0 + 2^1 + ... + 2^h = 2^h * 2 - 1 = 2^Math.ceil(log(2, w))*2 - 1 < w * 4 
 *  the space is O( (max - min) * 4), the time complexity is O(n * log(max - min))
 * 
 * m3) Define a tree to store {value, leftChildrenCount}, 
 * m31) example {5, 2, 6, 1}
 *  step 1, count the smaller than 1, and add 1
 *    init a node (1, 0)
 *  step 2, count the smaller than 6, and add 6
 *           (1, 0)
 *              \
 *             (6, 0)
 *  
 *  step 3, count the smaller than 2, and add 2
 *           (1, 0)
 *              \
 *             (6, 1)
 *              /
 *          (2, 0)
 * 
 *  step 4, count the smaller than 5, and add 5
 *           (1, 0)
 *              \
 *             (6, 2)
 *              /
 *          (2, 0)
 *              \
 *             (5, 0)
 *   the space is O(n), the time complexity is O(n * n), for case {1,2,3} or {3,2,1}
 * 
 * m32) example {5, 2, 6, 1, 7}
 *  step 1, sorted,  {1, 2, 5, 6, 7}
 *  step 2, Define a int array to store the perfect AVL tree. Node is {value, left sub tree node number}
 *  
 *   (the index in sorted array, the index in arrayTree)
 *              3
 *            /   \
 *           1     4 
 *          / \
 *         0   2
 *   so the arrayTree {6, 2, 7, 1, 5}       
 *   the space is O(n), the time complexity is O(n * log(n))
 *  
 * m33) example {5, 2, 6, 1, 7}, 
 *  step 1, sorted,  {1, 2, 5, 6, 7}
 *  step 2, Define a int array to store a AVL tree.  
 *  
 *   (the index in sorted array, the index in arrayTree)
 *               (2, 0)
 *               /    \
 *            (1, 1) (4, 2)  
 *             /      /
 *         (0, 3)  (3, 5) 
 *   so the arrayTree {5, 2, 7, 1, *, 6}, here root_index = min + (max - min + 1) / 2
 * 
 * m4) Binary Indexed Tree
 *   example {5, 2, 6, 1, 7}, 
 *   step 1, sorted {1, 2, 5, 6, 7}
 *     nums: {5, 2, 6, 1, 7}, 
 *    index: {3, 2, 4, 1, 5}, 
 *   Step 2, count the smaller than 7, and add 7 (update BIT[index[7]] to 1)       
 *    BIT {0, 0, 0, 0, 0, 1}
 *           count the smaller than 1, and add 1 (update BIT[index[1]] to 1)  
 *    BIT {0, 1, 1, 0, 2, 1}
 *           count the smaller than 6, and add 6 (update BIT[index[6]] to 1)  
 *    ...
 * 
 * Furthermore:
 * 1) How to do count smaller and equal after itself?  
 *      To m2 and m3, the leaf need store the equalCount, the non-leaf leftSon node need store the smallerCount
 *      To m4, every node need store the smallerCount and equalCount; 
 * 
 * 2) Similar with Question,  Input an array, Count the inversions 
 * Inversion Count for an array indicates:  how far (or close) the array is from being sorted.
 * If array is already sorted then inversion leftChildrenCount is 0. 
 * If array is sorted in reverse order that inversion leftChildrenCount is the maximum.
 * 
 */
public class CountSmallerAfterSelf {
    /**
     * m1
     * Time Complexity O(n*n)
     */
    public int countSmaller(int[] nums){
        if(null == nums || nums.length < 2){
            return 0;
        }
        
        int count = 0;
        
        int n = nums.length;
        for(int i = n - 2; i >= 0; i--){
            for(int j = i + 1; j < n; j++){
                if(nums[i] > nums[j]){
                    count++;
                }
            }
        }
        
        return count;
    }
    
    public static void main(String[] args){
        int[][][] inputs = {
            //{nums, {expect}}
            {{5, 2, 6, 1}, {4}},
            {{3, 2, 2, 6, 1}, {6}},
            {{1, 2, 3, 4}, {0}},
            
            {{3, -2, -3, -6, -1}, {7}},
            {{3, -2, -3, -6, Integer.MAX_VALUE - 1111, -1}, {8}},
        };
        
        CountSmallerAfterSelf sv = new CountSmallerAfterSelf();

        for(int[][] input : inputs){
            System.out.println(Arrays.toString(input[0]));

            Assert.assertEquals(input[1][0], sv.countSmaller(input[0]));
        }
    }
}
